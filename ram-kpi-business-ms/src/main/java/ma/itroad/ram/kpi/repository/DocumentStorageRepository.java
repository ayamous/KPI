package ma.itroad.ram.kpi.repository;

import ma.itroad.ram.kpi.domain.Document;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data SQL repository for the Attachement entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DocumentStorageRepository extends JpaRepository<Document, Long> {

    /*
     * @Query("Select a from Document a where user_id = ?1 and document_type = ?2")
     * Document checkDocumentByUserId(Integer userId, String docType);
     *
     *
     * @Query("Select fileName from Document a where user_id = ?1 and document_type = ?2"
     * )
     * String getUploadDocumnetPath(Integer userId, String docType);
     */

    @Modifying
    @Query("delete from Document d where d.fsName in ?1")
    void deleteDocumentsWithIds(List<String> ids);

}
