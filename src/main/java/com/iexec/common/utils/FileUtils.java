package com.iexec.common.utils;

import lombok.extern.slf4j.Slf4j;
import org.web3j.crypto.Hash;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Slf4j
public class FileUtils {

    private FileUtils() {
        throw new UnsupportedOperationException();
    }

    public static byte[] readFile(String dirPath, String filename) {
        try {
            return Files.readAllBytes(Paths.get(dirPath + filename));
        } catch (IOException e) {
            log.error("Failed to read file [dirPath:{}, filename:{}]", dirPath, dirPath);
        }
        return null;
    }

    public static boolean writeFile(String dirPath, String filename, byte[] data){
        try {
            Files.write(Paths.get(dirPath + filename), data);
            return true;
        } catch (IOException e) {
            log.error("Failed to write file [dirPath:{}, filename:{}, dataHash:{}]", dirPath, dirPath, Hash.sha3(data));
        }
        return false;
    }
}