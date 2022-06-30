package ma.itroad.ram.kpi.service.impl;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import ma.itroad.ram.kpi.domain.Document;
import ma.itroad.ram.kpi.repository.DocumentStorageRepository;
import ma.itroad.ram.kpi.service.DocumentStorageService;
import ma.itroad.ram.kpi.service.dto.DocumentDTO;
import ma.itroad.ram.kpi.service.dto.KpiDTO;
import ma.itroad.ram.kpi.service.mapper.DocumentMapper;
import ma.itroad.ram.kpi.service.multipartupload.IFileSystemStorage;
import ma.itroad.ram.kpi.service.multipartupload.exceptions.DirectoryNotFoundExeption;
import ma.itroad.ram.kpi.service.utils.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

/**
 * Service Implementation for managing {@link Document}.
 */
@Service
@Transactional
public class DocumentStorageServiceImpl implements DocumentStorageService {

    private final Logger log = LoggerFactory.getLogger(DocumentStorageServiceImpl.class);

    private final DocumentStorageRepository documentStorageRepository;

    private final DocumentMapper documentMapper;

    @Autowired
    IFileSystemStorage fileSystemStorage;

    public DocumentStorageServiceImpl(DocumentStorageRepository documentStorageRepository,
                                      DocumentMapper documentMapper) {
        this.documentStorageRepository = documentStorageRepository;
        this.documentMapper = documentMapper;
    }

    @Override
    public DocumentDTO save(DocumentDTO documentDTO) {
        log.debug("Request to save document : {}", documentDTO);
        Document doc = documentMapper.toEntity(documentDTO);
        doc = documentStorageRepository.save(doc);
        return documentMapper.toDto(doc);
    }

    @Override
    public Optional<DocumentDTO> partialUpdate(DocumentDTO documentDTO) {
        log.debug("Request to partially update Attachment : {}", documentDTO);

        return documentStorageRepository
                .findById(documentDTO.getId())
                .map(existingDocument -> {
                    documentMapper.partialUpdate(existingDocument, documentDTO);

                    return existingDocument;
                })
                .map(documentStorageRepository::save)
                .map(documentMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<DocumentDTO> findAll() {
        log.debug("Request to get all Attachments");
        return documentStorageRepository.findAll().stream().map(documentMapper::toDto)
                .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<DocumentDTO> findOne(Long id) {
        log.debug("Request to get document : {}", id);
        return documentStorageRepository.findById(id).map(documentMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Attachment : {}", id);
        documentStorageRepository.deleteById(id);
    }

    @Override
    @Transactional
    public List<DocumentDTO> storeFiles(String dirId, MultipartFile[] files) {
        System.out.println("folderId : " + dirId);
        System.out.println("files.length " + files.length);

        String uploadDir = fileSystemStorage.createKpiDocumentsUploadDir(dirId);
        return IntStream.range(0, files.length).mapToObj(
                index -> {
                    MultipartFile file = files[index];
                    String extension = FileUtils.getExtension(file.getOriginalFilename());
                    System.out.println("index : " + index + "file  :" + file);
                    String uploadedFileName = fileSystemStorage.upload(uploadDir, file, index);
                    String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                            .path("/business/api/download/" + dirId + "/")
                            .path(uploadedFileName)
                            .toUriString();
                    DocumentDTO newDoc = new DocumentDTO();
                    newDoc.setUrl(fileDownloadUri);
                    newDoc.setContentType(file.getContentType());
                    newDoc.setSize(file.getSize());
                    newDoc.setDirId(dirId);
                    newDoc.setName(file.getOriginalFilename());
                    newDoc.setFsName(uploadedFileName);
                    newDoc.setExtension(extension);
                    return newDoc;
                }).collect(Collectors.toList());
    }

    DocumentDTO findDocument(List<DocumentDTO> kpiDocuments, String fileName) {
        System.out.println("find document id call");
        for (DocumentDTO doc : kpiDocuments) {
            System.out.println("FsName " + doc.getFsName());
            // System.out.println("FileName " + fileName);
            if (doc.getFsName().equals(fileName)) {
                return doc;
            }
        }
        return null;
    }

    @Override
    @Transactional
    public List<DocumentDTO> updateStoreFiles(KpiDTO kpiDTO) {
        String dirId = kpiDTO.getDirId();

        if (dirId == null) {
            //dirId = FileUtils.generateFolderId();
            throw new DirectoryNotFoundExeption("Invalid directory Id");
        }
        List<String> fileSystemNames = fileSystemStorage.getDirectoryFileNames(dirId);
        List<String> kpiDocumentsNames = kpiDTO.getDocuments()
                .stream()
                .map(doc -> doc.getFsName())
                .collect(Collectors.toList());

        List<String> differencefilesNames = FileUtils.findDifference(fileSystemNames, kpiDocumentsNames);
        System.out.println("differences :  " + differencefilesNames);

        fileSystemStorage.deleteFiles(dirId, differencefilesNames);
        documentStorageRepository.deleteDocumentsWithIds(differencefilesNames);

        // return list of kepeed files

        return null;

    }

    @Override
    public boolean isEmptyMultipartFile(MultipartFile[] files) {
        for (MultipartFile $file : files) {
            if ($file.isEmpty()) {
                return true;
            }
        }
        return false;
    }


}

