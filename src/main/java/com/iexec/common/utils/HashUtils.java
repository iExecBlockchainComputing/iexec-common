package com.iexec.common.utils;

import org.bouncycastle.util.Arrays;
import org.web3j.crypto.Hash;
import org.web3j.utils.Numeric;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import static com.iexec.common.utils.BytesUtils.bytesToString;

public class HashUtils {

    private HashUtils() {
        throw new UnsupportedOperationException();
    }

    public static String concatenateAndHash(String... hexaString) {

        // convert
        byte[] res = new byte[0];
        for (String str : hexaString) {
            res = Arrays.concatenate(res, BytesUtils.stringToBytes(str));
        }

        // Hash the result and convert to String
        return Numeric.toHexString(Hash.sha3(res));
    }

    public static String sha256(String utf8Input) {
        byte[] input = utf8Input.getBytes(StandardCharsets.UTF_8);
        byte[] hexHash = Hash.sha256(input);
        return BytesUtils.bytesToString(hexHash);
    }

    public static String getFileTreeSha256(String fileTreePath) {
        File fileTree = new File(fileTreePath);

        if (!fileTree.exists()){
            return "";
        }

        //fileTree is a leaf, a single file
        if (!fileTree.isDirectory()){
            return getFileSha256(fileTreePath);
        }

        //fileTree is a tree, with multiple files
        if (fileTree.listFiles() != null){
            List<String> hashes = new ArrayList<>();
            for (File file : fileTree.listFiles()) {
                hashes.add(getFileSha256(file.getAbsolutePath()));
            }
            return HashUtils.concatenateAndHash(hashes.toArray(new String[0]));
        }

        return "";
    }

    public static String getFileSha256(String filePath) {
        if (!new File((filePath)).exists()){
            return "";
        }
        byte[] input = FileHelper.readAllBytes(filePath);
        if (input == null){
            return "";
        }
        return bytesToString(Hash.sha256(input));
    }
}
