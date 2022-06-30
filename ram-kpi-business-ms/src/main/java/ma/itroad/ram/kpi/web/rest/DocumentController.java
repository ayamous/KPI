package ma.itroad.ram.kpi.web.rest;

import ma.itroad.ram.kpi.service.multipartupload.IFileSystemStorage;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.zip.ZipOutputStream;


@RestController
@RequestMapping("/business/api")
public class DocumentController {

    private IFileSystemStorage fileSytemStorage;

    public DocumentController(IFileSystemStorage fileSytemStorage) {
        this.fileSytemStorage = fileSytemStorage;
    }


    @GetMapping("/download/zip/{folderId}")
    @ResponseBody
    public ResponseEntity<Resource> downloadFile(@PathVariable final String folderId) throws IOException {
        ByteArrayResource resource = fileSytemStorage.loadAllFilesAsResource(folderId);
        System.out.println("fileName : " + resource.getFilename());
        String fileName = resource.getFilename() != null ? resource.getFilename() : folderId + ".zip";
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + fileName + "\"")
                .contentLength(resource.contentLength())
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }

    @GetMapping("/download/{folderId}/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> downloadFile(@PathVariable final String folderId, @PathVariable String filename) {

        Resource resource = fileSytemStorage.loadFileAsResource(folderId, filename);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }


}