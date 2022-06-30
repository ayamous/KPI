package ma.itroad.ram.kpi.service.multipartupload;


import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.zip.ZipOutputStream;

public interface IFileSystemStorage {
    void init();

    String upload(String uploadDir, MultipartFile file, int i);

    Resource loadFileAsResource(String folderId, String fileName);

    String createKpiDocumentsUploadDir(String dirId);

    String uodateKpiDocumentsUploadDir(String folderId);

    List<String> getDirectoryFileNames(String dirId);

    void deleteFiles(String dirId, List<String> filesNames);

    ByteArrayResource loadAllFilesAsResource(String folderId) throws IOException;
}
