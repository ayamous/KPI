package ma.itroad.ram.kpi.service;

import java.util.List;
import java.util.Optional;

import ma.itroad.ram.kpi.domain.Document;
import ma.itroad.ram.kpi.service.dto.DocumentDTO;
import ma.itroad.ram.kpi.service.dto.KpiDTO;
import org.springframework.web.multipart.MultipartFile;

/**
 * Service Interface for managing {@link Document}.
 */
public interface DocumentStorageService {
    /**
     * Save a attachment.
     *
     * @param documentDTO the entity to save.
     * @return the persisted entity.
     */
    DocumentDTO save(DocumentDTO documentDTO);

    /**
     * Partially updates a attachment.
     *
     * @param documentDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<DocumentDTO> partialUpdate(DocumentDTO documentDTO);

    /**
     * Get all the attachments.
     *
     * @return the list of entities.
     */
    List<DocumentDTO> findAll();

    /**
     * Get the "id" attachment.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<DocumentDTO> findOne(Long id);

    /**
     * Delete the "id" attachment.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Save MultipartFile files.
     *
     * @param id the id of the entity.
     * @return
     */

    List<DocumentDTO> storeFiles(String dirId, MultipartFile[] files);


    /**
     * Save MultipartFile files.
     *
     * @param kpiDTO
     * @return
     */
    List<DocumentDTO> updateStoreFiles(KpiDTO kpiDTO);


    /**
     * isEmpty MultipartFile files.
     *
     * @param files .
     * @return
     */
    boolean isEmptyMultipartFile(MultipartFile[] files);

}
