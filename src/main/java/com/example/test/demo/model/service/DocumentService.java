package com.example.test.demo.model.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

public interface DocumentService {

    boolean moveDir(Path src, Path dest);
    void search(File file) throws IOException;
    void calculateSha256(File file) throws IOException;
    byte[] checksum(String filePath, String algorithm);
    String bytesToHex(byte[] bytes);
}
