package ma.itroad.ram.kpi.service.multipartupload;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import javax.annotation.PostConstruct;

import ma.itroad.ram.kpi.service.multipartupload.exceptions.FileNotFoundException;
import ma.itroad.ram.kpi.service.multipartupload.exceptions.FileStorageException;
import ma.itroad.ram.kpi.service.utils.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

//https://github.com/Alluxio/alluxio/blob/master/core/common/src/main/java/alluxio/util/io/FileUtils.java#L247
@Service
public class FileSystemStorageImpl implements IFileSystemStorage {
    private final Path rootDirLocation;

    private ResourceLoader resourceLoader;


    @Autowired
    public FileSystemStorageImpl(FileUploadProperties fileUploadProperties, ResourceLoader resourceLoader) {
        this.rootDirLocation = Paths.get(fileUploadProperties.getLocation())
                .toAbsolutePath()
                .normalize();

        this.resourceLoader = resourceLoader;
    }

    @Override
    @PostConstruct
    public void init() {
        // TODO Auto-generated method stub
        try {
            Files.createDirectories(this.rootDirLocation);
        } catch (Exception ex) {
            throw new FileStorageException("Could not create upload dir!");
        }
    }

    @Override
    public String createKpiDocumentsUploadDir(String dirId) {
        try {
            if (!Files.exists(this.rootDirLocation)) {
                init();
            }
            String uploadDir = this.rootDirLocation.toAbsolutePath() + "/" + dirId;
            if (!Files.exists(Paths.get(uploadDir))) {
                System.out.println("creating sub dir..." + uploadDir);
                Files.createDirectory(Paths.get(uploadDir));
            }
            return uploadDir;

        } catch (IOException e) {
            throw new RuntimeException("Could not create upload folder!");
        }
    }
    //

    @Override
    public String uodateKpiDocumentsUploadDir(String dirId) {
        try {
            if (!Files.exists(this.rootDirLocation)) {
                init();
            }
            String uploadDir = this.rootDirLocation.toAbsolutePath() + "/" + dirId;
            if (!Files.exists(Paths.get(uploadDir))) {
                System.out.println("creating sub dir..." + uploadDir);
                Files.createDirectory(Paths.get(uploadDir));
            }
            return uploadDir;

        } catch (IOException e) {
            throw new RuntimeException("Could not create upload folder!");
        }
    }

    @Override
    public String upload(String uploadDir, MultipartFile file, int fileIndex) {
        try {
            String fileExtension = FileUtils.getExtension(file.getOriginalFilename());
            // String fileName = fileIndex + "." + fileExtension;

            String fileName = FileUtils.generateUniqueFileName() + "." + fileExtension;
            Path dfile = Paths.get(uploadDir).toAbsolutePath()
                    .normalize().resolve(fileName);
            System.out.println("dfile .. : " + dfile);
            Files.copy(file.getInputStream(), dfile, StandardCopyOption.REPLACE_EXISTING);
            return fileName;
        } catch (Exception e) {
            e.getStackTrace();
            throw new FileStorageException("Could not upload file");
        }
    }

    // TODO Auto-generated method stub

    @Override
    public Resource loadFileAsResource(String folderId, String fileName) {
        try {
            Path file = this.rootDirLocation
                    .resolve(folderId)
                    .resolve(fileName).normalize();
            System.out.println("loadFileAsResource call : " + file);

            Resource resource = new UrlResource(file.toUri());

            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new FileNotFoundException("Could not find file");
            }
        } catch (MalformedURLException e) {
            throw new FileNotFoundException("Could not download file");
        }
    }


    // FilenameFilter
    @Override
    public List<String> getDirectoryFileNames(String dirId) {
        Path path = this.rootDirLocation
                .resolve(dirId).normalize();
        String strDirPath = path.toString();
        File[] files = new File(strDirPath).listFiles();
        if (files != null) {
            return Stream.of(files)
                    .filter(file -> !file.isDirectory())
                    .map(File::getName)
                    .collect(Collectors.toList());
        } else return new ArrayList<>();
    }

    @Override
    public void deleteFiles(String dirId, List<String> filesNames) {
        for (String fileName : filesNames) {
            Path filePath = this.rootDirLocation
                    .resolve(dirId)
                    .resolve(fileName).normalize();
            try {
                Files.delete(filePath);
            } catch (NoSuchFileException x) {
                System.err.format("%s: no such" + " file or directory%n", filePath);
            } catch (DirectoryNotEmptyException x) {
                System.err.format("%s not empty%n", filePath);
            } catch (IOException x) {
                // File permission problems are caught here.
                System.err.println(x);
            }
        }

    }


    @Override
    public ByteArrayResource loadAllFilesAsResource(String folderId) throws IOException {
        Path sourceFile = this.rootDirLocation
                .resolve(folderId)
                .normalize();
        String compressedFolderStringPath = sourceFile.toString() + ".zip";

        System.out.println("compressedFolderStringPath..." + compressedFolderStringPath);

        FileOutputStream fos = new FileOutputStream(compressedFolderStringPath);
        ZipOutputStream zipOut = new ZipOutputStream(fos);
        File fileToZip = new File(sourceFile.toString());
        zipFile(fileToZip, fileToZip.getName(), zipOut);

        zipOut.close();
        fos.close();


        Path path = Paths.get(compressedFolderStringPath);
        byte[] data = Files.readAllBytes(path);
        System.out.println("data..." + data);
        ByteArrayResource resource = new ByteArrayResource(data, folderId + ".zip");
        System.out.println("resource..." + resource.toString());
        try {
            Files.delete(Paths.get(compressedFolderStringPath));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return resource;

    }

    private static void zipFile(File fileToZip, String fileName, ZipOutputStream zipOut) throws IOException {
        if (fileToZip.isHidden()) {
            return;
        }
        if (fileToZip.isDirectory()) {
            if (fileName.endsWith("/")) {
                zipOut.putNextEntry(new ZipEntry(fileName));
                zipOut.closeEntry();
            } else {
                zipOut.putNextEntry(new ZipEntry(fileName + "/"));
                zipOut.closeEntry();
            }
            File[] children = fileToZip.listFiles();
            for (File childFile : children) {
                zipFile(childFile, fileName + "/" + childFile.getName(), zipOut);
            }
            return;
        }
        FileInputStream fis = new FileInputStream(fileToZip);
        ZipEntry zipEntry = new ZipEntry(fileName);
        zipOut.putNextEntry(zipEntry);
        byte[] bytes = new byte[1024];
        int length;
        while ((length = fis.read(bytes)) >= 0) {
            zipOut.write(bytes, 0, length);
        }
        fis.close();
    }
}

/*
 * @Override
 * public Resource getFilesDirectory(String folderId) {
 * try {
 * Path file = this.rootDirLocation
 * .resolve(folderId)
 * .resolve(fileName).normalize();
 * System.out.println("loadFileAsResource call : " + file);
 *
 * Resource resource = new UrlResource(file.toUri());
 *
 * if (resource.exists() || resource.isReadable()) {
 * return resource;
 * } else {
 * throw new FileNotFoundException("Could not find file");
 * }
 * } catch (MalformedURLException e) {
 * throw new FileNotFoundException("Could not download file");
 * }
 * }
 */

