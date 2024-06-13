/*
 * Copyright 2020 IEXEC BLOCKCHAIN TECH
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.iexec.common.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.lingala.zip4j.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.web3j.crypto.Hash;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Objects;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FileHelper {

    public static String readFile(String filePath) {
        try {
            byte[] content = Files.readAllBytes(Paths.get(filePath));
            return new String(content);
        } catch (Exception e) {
            log.error("Failed to read file [filePath:{}]", filePath);
        }
        return "";
    }

    public static byte[] readFileBytes(String filePath) {
        String content = readFile(filePath);

        if (!content.isEmpty()) {
            return content.getBytes();
        }
        return null;
    }

    /*
     * TODO
     * 1 - Rename FileHelper.readFile --> FileHelper.readStringFile
     * 2 - Remove FileHelper.readFileBytes ?
     * */
    public static byte[] readAllBytes(String filePath) {
        try {
            return Files.readAllBytes(Paths.get(filePath));
        } catch (IOException e) {
            log.error("Failed to readAllBytes [filePath:{}]", filePath);
        }

        return null;
    }

    public static boolean writeFile(String filePath, byte[] data) {
        try {
            Files.write(Paths.get(filePath), data);
            return true;
        } catch (IOException e) {
            log.error("Failed to write file [filePath:{}, dataHash:{}]", filePath, Hash.sha3(data));
        }
        return false;
    }

    public static File createFileWithContent(String filePath, String data) {
        return data != null ? createFileWithContent(filePath, data.getBytes()) : null;
    }

    public static File createFileWithContent(String filePath, byte[] data) {
        String parentDirectoryPath = new File(filePath).getParent();
        boolean isParentFolderCreated = createFolder(parentDirectoryPath);

        if (!isParentFolderCreated) {
            log.error("Failed to create base directory [parentDirectoryPath:{}]", parentDirectoryPath);
            return null;
        }

        try {
            Files.write(Paths.get(filePath), data);
            log.debug("File created [filePath:{}]", filePath);
            return new File(filePath);
        } catch (IOException e) {
            log.error("Failed to create file [filePath:{}]", filePath);
            return null;
        }
    }

    /**
     * Download file with custom name in specified directory
     * @param fileUrl URL of the file
     * @param parentFolderPath directory path where the file will be downloaded
     * @param outputFilename desired name for future downloaded file
     * @return downloaded file location path if successful download
     */
    public static String downloadFile(String fileUrl,
                                      String parentFolderPath,
                                      String outputFilename) {
        if (StringUtils.isEmpty(fileUrl)) {
            log.error("Invalid file url [fileUrl:{}]", fileUrl);
            return "";
        }
        if (StringUtils.isEmpty(parentFolderPath)) {
            log.error("Invalid parent folder path [fileUrl:{}, path:{}]",
                    fileUrl, parentFolderPath);
            return "";
        }
        if (StringUtils.isEmpty(outputFilename)) {
            log.error("Invalid output filename [fileUrl:{}, outputFilename:{}]",
                    fileUrl, outputFilename);
            return "";
        }
        byte[] fileBytes = readFileBytesFromUrl(fileUrl);
        if (fileBytes == null) {
            log.error("Failed to download file [fileUrl:{}]", fileUrl);
            return "";
        }
        boolean parentFolderAlreadyExisted = exists(parentFolderPath);
        if (!parentFolderAlreadyExisted && !createFolder(parentFolderPath)) {
            log.error("Failed to create parent folder [fileUrl:{}, parentFolderPath:{}]",
                    fileUrl, parentFolderPath);
            return "";
        }
        String filePath = parentFolderPath + File.separator + outputFilename;
        boolean isWritten = writeFile(filePath, fileBytes);
        if (!isWritten) {
            log.error("Failed to write downloaded file to disk " +
                    "[fileUrl:{}, filePath:{}]", fileUrl, filePath);
            if (!parentFolderAlreadyExisted) {
                deleteFolder(parentFolderPath);
            }
            return "";
        }
        log.info("Downloaded data [fileUrl:{}, filePath:{}]", fileUrl, filePath);
        return filePath;
    }

    /**
     * Read the content of the remote file located at the provided URL.
     * @param url of the file
     * @return the content of the file in a byte array if success,
     * null otherwise.
     */
    public static byte[] readFileBytesFromUrl(String url) {
        try {
            return new URL(url).openStream().readAllBytes();
        } catch (Exception e) {
            log.error("Failed to read file bytes from url [url:{}]", url, e);
            return null;
        }
    }

    /**
     * Download file and returns downloaded file location path if successful.
     * Downloaded file name is inferred from uri end path
     * @param fileUri URI of the file
     * @param downloadDirectoryPath directory path where the file will be downloaded
     * @return downloaded file location path if successful download
     */
    public static String downloadFile(String fileUri, String downloadDirectoryPath) {
        return downloadFile(fileUri,
                downloadDirectoryPath,
                Paths.get(fileUri).getFileName().toString());
    }

    /**
     * Download file
     * @deprecated
     * <p> Use {@link FileHelper#downloadFile(String, String, String)} instead.
     * @param fileUri URI of the file
     * @param directoryPath directory path where the file will be downloaded
     * @return true if successful download
     */
    @Deprecated(forRemoval = true)
    public static boolean downloadFileInDirectory(String fileUri, String directoryPath){
        return !downloadFile(fileUri, directoryPath).isEmpty();
    }

    public static boolean exists(String path) {
        Objects.requireNonNull(path, "Path must not be null");
        return new File(path).exists();
    }

    public static boolean createFolder(String folderPath) {
        File baseDirectory = new File(folderPath);
        return baseDirectory.exists() ? true : baseDirectory.mkdirs();
    }

    public static boolean deleteFile(String filePath) {
        try {
            Files.delete(Paths.get(filePath));
            log.info("File has been deleted [path:{}]", filePath);
            return true;
        } catch (IOException e) {
            log.error("Problem when trying to delete the file [path:{}]", filePath);
        }
        return false;
    }

    public static boolean deleteFolder(String folderPath) {
        File folder = new File(folderPath);
        if (!folder.exists()) {
            log.info("Folder doesn't exist so can't be deleted [path:{}]", folderPath);
            return false;
        }

        try {
            FileUtils.deleteDirectory(folder);
            log.info("Folder has been deleted [path:{}]", folderPath);
            return true;
        } catch (IOException e) {
            log.error("Problem when trying to delete the folder [path:{}]", folderPath);
        }
        return false;
    }

    public static File zipFolder(String folderPath) {
        String parentFolder = Paths.get(folderPath).getParent().toString();
        return zipFolder(folderPath, parentFolder);
    }

    public static File zipFolder(String folderPath, String saveIn) {
        String folderName = Paths.get(folderPath).getFileName().toString();
        String zipFilePath = Path.of(saveIn, folderName + ".zip").toAbsolutePath().toString();
        Path sourceFolderPath = Paths.get(folderPath);

        try (ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(zipFilePath))) {
            Files.walkFileTree(sourceFolderPath, new SimpleFileVisitor<>() {
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    log.debug("Adding file to zip [file:{}, zip:{}]", file.toAbsolutePath(), zipFilePath);
                    zos.putNextEntry(new ZipEntry(sourceFolderPath.relativize(file).toString()));
                    if (!Files.isSymbolicLink(file)) {
                        Files.copy(file, zos);
                    }
                    zos.closeEntry();
                    return FileVisitResult.CONTINUE;
                }
            });
            log.info("Folder zipped [path:{}]", zipFilePath);
            return new File(zipFilePath);

        } catch (Exception e) {
            log.error("Failed to zip folder [path:{}]", zipFilePath, e);
        }
        return null;
    }

    // TODO: Use same lib for zipping
    public static boolean unZipFile(String zipFilePath, String destDirPath) {
        if (zipFilePath == null || zipFilePath.isEmpty() || !new File(zipFilePath).exists()) {
            log.error("Failed to unZipFile (missing zipFile) [zipFilePath:{}, destDirPath:{}]", zipFilePath, destDirPath);
            return false;
        }

        if (destDirPath == null || destDirPath.isEmpty()) {
            log.error("Failed to unZipFile (missing destDirPath) [zipFilePath:{}, destDirPath:{}]", zipFilePath, destDirPath);
            return false;
        }

        try {
            new ZipFile(zipFilePath).extractAll(destDirPath);
            return true;
        } catch (ZipException e) {
            log.error("Failed to unZipFile (can't extract) [zipFilePath:{}, destDirPath:{}]", zipFilePath, destDirPath);
        }
        return false;
    }

    /*
    * Will extract into a directory next to zip
    *
    * before
    * └── some-content.zip
    *
    * after
    * ├── some-content.zip
    * └── some-content/
    *     ├── file1
    *     └── file2
    *
    * returns extractDirPath
    * */
    public static String unZipFile(String zipPath){
        String extractDirPath = removeZipExtension(zipPath);
        if (extractDirPath.isEmpty()){
            log.error("unzip failed (removeZipExtension) [zipPath:{}]", zipPath);
            return "";
        }

        boolean isExtracted = FileHelper.unZipFile(zipPath, extractDirPath);
        if (!isExtracted){
            log.error("unzip failed (unZipFile) [zipPath:{}]", zipPath);
            return "";
        }
        return extractDirPath;
    }

    public static String removeZipExtension(String zipPath) {
        if (zipPath == null || !FilenameUtils.getExtension(zipPath).equals("zip")){
            return "";
        }
        return FilenameUtils.removeExtension(zipPath);
    }

    public static boolean replaceFile(String toBeReplaced, String replacer) {
        try {
            Files.delete(Paths.get(toBeReplaced));
            return new File(replacer).renameTo(new File(toBeReplaced));
        } catch (IOException e) {
            log.error("Problem when trying to replace file [toBeReplaced:{}, replacer:{}]",
                    toBeReplaced, replacer, e);
            return false;
        }
    }

    public static boolean moveFile(String fromPath, String toPath) {
        boolean isMoved = new File(fromPath).renameTo(new File(toPath));
        if (!isMoved) {
            log.error("Failed to moveFile file [fromPath:{}, toPath:{}]", fromPath, toPath);
        }
        return isMoved;
    }

    public static boolean copyFolder(String source, String target) {
        try {
            FileUtils.copyDirectory(new File(source), new File(target));
            return true;
        } catch (IOException e) {
            log.error("Error copying folder [source:{}, target:{}]", source, target, e);
            return false;
        }
    }

    public static boolean copyFile(String source, String target) {
        try {
            Files.copy(Path.of(source), Path.of(target));
            return true;
        } catch (IOException e) {
            log.error("Error copying file [source:{}, target:{}]", source, target, e);
            return false;
        }
    }

    public static String getFilenameFromUri(String uri) {
        return Paths.get(uri).getFileName().toString();
    }

    public static String printDirectoryTree(File folder) {
        if (!folder.isDirectory()) {
            throw new IllegalArgumentException("folder is not a Directory");
        }
        int indent = 0;
        StringBuilder sb = new StringBuilder();
        printDirectoryTree(folder, indent, sb);
        return sb.toString();
    }

    private static void printDirectoryTree(File folder, int indent,
                                           StringBuilder sb) {
        if (!folder.isDirectory()) {
            throw new IllegalArgumentException("folder is not a Directory");
        }
        sb.append(getIndentString(indent));
        sb.append("├── ");
        sb.append(folder.getName());
        sb.append("/");
        sb.append("\n");
        for (File file : folder.listFiles()) {
            if (file.isDirectory()) {
                printDirectoryTree(file, indent + 1, sb);
            } else {
                printFile(file, indent + 1, sb);
            }
        }

    }

    private static void printFile(File file, int indent, StringBuilder sb) {
        sb.append(getIndentString(indent));
        sb.append("├── ");
        sb.append(file.getName());
        sb.append("\n");
    }

    private static String getIndentString(int indent) {
        return "│   ".repeat(indent);
    }

}
