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

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import static com.iexec.common.utils.FileHelper.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class FileHelperTests {

    private static final String TEST_FOLDER = "/tmp/iexec-test";
    // http
    private static final String HTTP_URL = "http://icons.iconarchive.com/icons/" +
            "cjdowner/cryptocurrency-flat/512/iExec-RLC-RLC-icon.png";
    // private static final String HTTP_FILENAME = "iExec-RLC-RLC-icon.png";
    private static final String HTTP_FILE_DIGEST =
            "0x240987ee1480e8e0b1b26fa806810fea04021191a8e6d8ab6325c15fa61fa9b6";
    // https
    private static final String HTTPS_URL =
            "https://iex.ec/wp-content/uploads/2018/12/token.svg";
    // private static final String HTTPS_FILENAME = "token.svg";
    private static final String HTTPS_FILE_DIGEST =
            "0x5e824f880294851ce7cf77671231f41fcd5502579c603adc884a978b2a3ce364";
    // redirection
    // private static final String REDIRECTION_URL = "https://goo.gl/t8JxoX";
    // private static final String REDIRECTION_FILE_DIGEST = "TODO";

    private static final String ICON_PNG = "icon.png";

    // clean the test repo before and after each test
    @BeforeEach
    void init() throws IOException {
        FileUtils.deleteDirectory(new File(TEST_FOLDER));
    }

    @AfterEach
    void tearDown() throws IOException {
        FileUtils.deleteDirectory(new File(TEST_FOLDER));
    }

    @Test
    void shouldThrowExceptionWhenInvokingConstructor() throws Exception {
        Constructor<FileHelper> clazz = FileHelper.class.getDeclaredConstructor();
        clazz.setAccessible(true);
        // calling the private constructor
        assertThrows(Exception.class, clazz::newInstance);
    }


    @Test
    void shouldCreateFileWithContent() throws IOException {
        String data = "a test";
        File file = FileHelper.createFileWithContent(TEST_FOLDER + "/test.txt", data);
        assertThat(file).isNotNull();
        assertThat(file).exists();
        assertThat(file).isFile();
        String content = Files.readString(Paths.get(file.getAbsolutePath()));
        assertThat(content).isEqualTo(data);
    }

    @Test
    void shouldCreateFolder() {
        String folderPath = TEST_FOLDER + "/folder";
        boolean created = FileHelper.createFolder(folderPath);
        File newFolder = new File(folderPath);
        assertThat(created).isTrue();
        assertThat(newFolder).isNotNull();
        assertThat(newFolder).exists();
        assertThat(newFolder).isDirectory();

        // it should not change anything if the folder is already created
        boolean createdAgain = FileHelper.createFolder(folderPath);
        File existingFolder = new File(folderPath);
        assertThat(createdAgain).isTrue();
        assertThat(existingFolder).isNotNull();
        assertThat(existingFolder).exists();
        assertThat(existingFolder).isDirectory();
    }

    @Test
    void shouldCreateFolderRecursively() {
        String folderPath = TEST_FOLDER + "/folder1/folder2/folder3";
        boolean created = FileHelper.createFolder(folderPath);
        File newFolder = new File(folderPath);
        assertThat(created).isTrue();
        assertThat(newFolder).isNotNull();
        assertThat(newFolder).exists();
        assertThat(newFolder).isDirectory();

        // it should not change anything if the folder is already created
        boolean createdAgain = FileHelper.createFolder(folderPath);
        File existingFolder = new File(folderPath);
        assertThat(createdAgain).isTrue();
        assertThat(existingFolder).isNotNull();
        assertThat(existingFolder).exists();
        assertThat(existingFolder).isDirectory();
    }

    @Test
    void shouldDeleteFile() {
        String filePath = TEST_FOLDER + "/test.txt";
        File file = FileHelper.createFileWithContent(filePath, "Hello world");
        assertThat(file).isNotNull();
        assertThat(file).exists();
        assertThat(file).isFile();

        boolean isDeleted = FileHelper.deleteFile(filePath);
        File deletedFile = new File(filePath);
        assertThat(isDeleted).isTrue();
        assertThat(deletedFile).doesNotExist();
    }

    @Test
    void shouldNotDeleteNonExistingFile() {
        String filePath = TEST_FOLDER + "/test.txt";

        boolean isDeleted = FileHelper.deleteFile(filePath);
        File deletedFile = new File(filePath);
        assertThat(isDeleted).isFalse();
        assertThat(deletedFile).doesNotExist();
    }

    @Test
    void shouldDeleteFolder() {
        String folderPath = TEST_FOLDER + "/folder";
        boolean created = FileHelper.createFolder(folderPath);
        File newFolder = new File(folderPath);
        assertThat(created).isTrue();
        assertThat(newFolder).isNotNull();
        assertThat(newFolder).exists();
        assertThat(newFolder).isDirectory();

        boolean isDeleted = FileHelper.deleteFolder(folderPath);
        File deletedFolder = new File(folderPath);
        assertThat(isDeleted).isTrue();
        assertThat(deletedFolder).doesNotExist();
    }

    @Test
    void shouldDeleteFoldersRecursively() {
        String folderPath = TEST_FOLDER + "/folder1/folder2/folder3";
        boolean created = FileHelper.createFolder(folderPath);
        File newFolder = new File(folderPath);
        assertThat(created).isTrue();
        assertThat(newFolder).isNotNull();
        assertThat(newFolder).exists();
        assertThat(newFolder).isDirectory();

        boolean isDeleted = FileHelper.deleteFolder(folderPath);
        File deletedFolder = new File(folderPath);
        assertThat(isDeleted).isTrue();
        assertThat(deletedFolder).doesNotExist();
    }

    @Test
    void shouldNotDeleteNonExistingFolder() {
        String folderPath = TEST_FOLDER + "/folder";
        boolean isDeleted = FileHelper.deleteFolder(folderPath);
        File deletedFolder = new File(folderPath);
        assertThat(isDeleted).isFalse();
        assertThat(deletedFolder).doesNotExist();
    }

    @Test
    void shouldZipFolder() {
        FileHelper.createFileWithContent(TEST_FOLDER + "/taskId/test.txt", "a test");
        File zipFile = FileHelper.zipFolder(TEST_FOLDER + "/taskId");
        assertThat(zipFile).isNotNull();
        assertThat(zipFile).exists();
        assertThat(zipFile).isFile();
        assertThat(zipFile.getAbsolutePath()).isEqualTo(TEST_FOLDER + "/taskId.zip");
    }

    // downloadFile(url, dir, name)

    @Test
    void shouldDownloadFileWithName() {
        String downloadedFilePath = FileHelper.downloadFile(
                HTTP_URL, TEST_FOLDER, ICON_PNG);
        assertThat(downloadedFilePath).isEqualTo(TEST_FOLDER + "/icon.png");
        assertThat(new File(downloadedFilePath)).exists();
        // check that the correct file is downloaded
        assertThat(HashUtils.sha256(new File(downloadedFilePath)))
                .isEqualTo(HTTP_FILE_DIGEST);
    }

    @Test
    void shouldDownloadFileWithNameFromHttpsUrl() {
        String downloadedFilePath = FileHelper.downloadFile(
                HTTPS_URL, TEST_FOLDER, "token.svg");
        assertThat(downloadedFilePath).isEqualTo(TEST_FOLDER + "/token.svg");
        assertThat(new File(TEST_FOLDER + "/token.svg")).exists();
        // check that the correct file is downloaded
        assertThat(HashUtils.sha256(new File(downloadedFilePath)))
                .isEqualTo(HTTPS_FILE_DIGEST);
    }

    // TODO
    // @Test
    // void shouldDownloadFileWithNameWithHttpRedirection() {
    //     String downloadedFilePath = FileHelper.downloadFile(
    //             REDIRECTION_URL, TEST_FOLDER, ICON_PNG);
    //     assertThat(downloadedFilePath).isEqualTo(TEST_FOLDER + "/icon.png");
    //     assertThat(new File(TEST_FOLDER + "/icon.png")).exists();
    //     // check that the correct file is downloaded
    //     assertThat(HashUtils.sha256(new File(downloadedFilePath)))
    //             .isEqualTo(REDIRECTION_FILE_DIGEST);
    // }

    @Test
    void shouldNotDownloadFileWithEmptyUrl() {
        assertThat(FileHelper.downloadFile("", TEST_FOLDER, "file.txt")).isEmpty();
        assertThat(new File(TEST_FOLDER).exists()).isFalse();
    }

    @Test
    void shouldNotDownloadFileWithEmptyParentFolderPath() {
        assertThat(FileHelper.downloadFile(HTTP_URL, "", ICON_PNG)).isEmpty();
        assertThat(new File(TEST_FOLDER).exists()).isFalse();
    }

    @Test
    void shouldNotDownloadFileWithEmptyOutputFileName() {
        assertThat(FileHelper.downloadFile(HTTP_URL, TEST_FOLDER, "")).isEmpty();
        assertThat(new File(TEST_FOLDER).exists()).isFalse();
    }

    @Test
    void shouldNotDownloadFileWithBadUrl() {
        assertThat(FileHelper.downloadFile("http://bad-url", TEST_FOLDER, "file.txt"))
                .isEmpty();
        assertThat(new File(TEST_FOLDER).exists()).isFalse();
    }

    @Test
    void shouldNotDownloadFileSinceCannotCreateFolder() {
        String downloadedFilePath = FileHelper.downloadFile(
                HTTP_URL, "/unauthorized", ICON_PNG);
        assertThat(downloadedFilePath).isEmpty();
    }

    // TODO
    // @Test
    // void shouldCleanCreatedParentFolderWhenFailingToWriteDownloadedFile() {}

    // readFileBytesFromUri(url)

    @Test
    void shouldReadFileBytesFromUrl() {
        byte[] bytes = FileHelper.readFileBytesFromUrl(HTTP_URL);
        assertThat(bytes).isNotEmpty();
    }

    @Test
    void shouldNotReadFileBytesFromBadUrl() {
        byte[] bytes = FileHelper.readFileBytesFromUrl("http://bad-url");
        assertThat(bytes).isNull();
    }

    // downloadFile(url, dir)

    @Test
    void shouldDownloadFileWithHttpUrl() {
        String downloadedFilePath = downloadFile(HTTP_URL, TEST_FOLDER);
        String filename = Paths.get(HTTP_URL).getFileName().toString();
        assertThat(downloadedFilePath).isEqualTo(TEST_FOLDER + "/" + filename);
        assertThat(new File(TEST_FOLDER + "/" + filename)).exists();
        // check that the correct file is downloaded
        assertThat(HashUtils.sha256(new File(downloadedFilePath)))
                .isEqualTo(HTTP_FILE_DIGEST);

    }

    @Test
    void shouldDownloadFileWithHttpsUrl() {
        String downloadedFilePath = downloadFile(HTTPS_URL, TEST_FOLDER);
        String filename = Paths.get(HTTPS_URL).getFileName().toString();
        assertThat(downloadedFilePath).isEqualTo(TEST_FOLDER + "/" + filename);
        assertThat(new File(TEST_FOLDER + "/" + filename)).exists();
        // check that the correct file is downloaded
        assertThat(HashUtils.sha256(new File(downloadedFilePath)))
                .isEqualTo(HTTPS_FILE_DIGEST);
    }

    // TODO
    // @Test
    // void shouldDownloadFileWithRedirectionUrl() {
    //     String downloadedFilePath = downloadFile(REDIRECTION_URL, TEST_FOLDER);
    //     String filename = Paths.get(REDIRECTION_URL).getFileName().toString();
    //     assertThat(downloadedFilePath).isEqualTo(TEST_FOLDER + "/" + filename);
    //     assertThat(new File(TEST_FOLDER + "/" + filename)).exists();
    //     // check that the correct file is downloaded
    //     assertThat(HashUtils.sha256(new File(downloadedFilePath)))
    //             .isEqualTo(REDIRECTION_FILE_DIGEST);
    // }

    // exists()

    @Test
    void shouldFindFolder() {
        assertThat(FileHelper.exists("/tmp")).isTrue();
    }

    @Test
    void shouldFindFile() {
        File file = FileHelper.createFileWithContent(TEST_FOLDER + "/test.txt", "whatever");
        assertThat(FileHelper.exists(file.getAbsolutePath())).isTrue();
    }

    @Test
    void shouldNotFindFolder() {
        assertThat(FileHelper.exists("/not-found")).isFalse();
    }

    // removeZipExtension(path)

    @Test
    void shouldRemoveZipExtension() {
        String fileName = FileHelper.removeZipExtension("/some/where/file.zip");
        assertEquals("/some/where/file", fileName);
    }

    @Test
    void shouldNotRemoveZipExtensionSinceNoExtension() {
        String fileName = FileHelper.removeZipExtension("/some/where/file");
        assertEquals(fileName, "");
    }

    @Test
    void shouldNotRemoveZipExtensionSinceWrongExtension() {
        String fileName = FileHelper.removeZipExtension("/some/where/file.bla");
        assertEquals(fileName, "");
    }

}
