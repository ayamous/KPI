package ma.itroad.ram.kpi.service.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.apache.commons.io.FilenameUtils;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.Cell;

public class FileUtils {

    public static String generateFolderId() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString();//.replace("-", "");
    }

    public static String generateUniqueFileName() {
        // generate random UUIDs
        UUID id = UUID.randomUUID();
        System.out.println("UUID One: " + id);
        return id.toString();
    }

    /**
     * Deletes the file or directory.
     *
     * @param path pathname string of file or directory
     */
    public static void delete(String path) throws IOException {
        if (!Files.deleteIfExists(Paths.get(path))) {
            throw new IOException("Failed to delete " + path);
        }
    }

    /**
     * Creates the storage directory path, including any necessary but nonexistent
     * parent directories.
     * If the directory already exists, do nothing.
     * <p>
     * Also, appropriate directory permissions (w/ StickyBit) are set.
     *
     * @param path storage directory path to create
     * @return true if the directory is created and false if the directory already
     * exists
     */
    public static boolean createStorageDirPath(String path)
            throws IOException {
        if (Files.exists(Paths.get(path))) {
            return false;
        }
        try {
            Files.createDirectories(Paths.get(path));
        } catch (UnsupportedOperationException | SecurityException | IOException e) {
            throw new IOException("Failed to create folder " + path, e);
        }
        return true;
    }

    /**
     * Creates an empty file and its intermediate directories if necessary.
     *
     * @param filePath pathname string of the file to create
     */
    public static void createFile(String filePath) throws IOException {
        Path storagePath = Paths.get(filePath);
        Path parent = storagePath.getParent();
        if (parent != null) {
            Files.createDirectories(parent);
        }
        Files.createFile(storagePath);
    }

    /**
     * Creates an empty directory and its intermediate directories if necessary.
     *
     * @param path path of the directory to create
     */
    public static void createDir(String path) throws IOException {
        Files.createDirectories(Paths.get(path));
    }

    /**
     * Checks if a path exists.
     *
     * @param path the given path
     * @return true if path exists, false otherwise
     */
    public static boolean exists(String path) {
        return Files.exists(Paths.get(path));
    }

    /**
     * Checks if a storage directory path is accessible.
     *
     * @param path the given path
     * @return true if path exists, false otherwise
     */
    public static boolean isStorageDirAccessible(String path) {
        Path filePath = Paths.get(path);
        return Files.exists(filePath)
                && Files.isReadable(filePath)
                && Files.isWritable(filePath)
                && Files.isExecutable(filePath);
    }

    public static String getExtension(String filename) {
        return FilenameUtils.getExtension(filename);
    }

    public static <T> List<T> findDifference(List<T> listWithMoreNumbers, List<T> listWithLessNumbers) {
        return listWithMoreNumbers.stream()
                .filter(i -> !listWithLessNumbers.contains(i))
                .collect(Collectors.toList());
    }


    public static String cellValue(Cell cell) {
        System.out.println("Cell = " + cell);
        switch (cell.getCellType()) {
            case 0:
                if (HSSFDateUtil.isCellDateFormatted(cell)) {
                    return cell.toString();
                }
                return Integer.valueOf((int) cell.getNumericCellValue()).toString();
            case 1:
                return cell.getStringCellValue();
            default:
                return null;
        }
    }
}
