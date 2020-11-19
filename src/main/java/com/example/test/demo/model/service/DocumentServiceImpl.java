package com.example.test.demo.model.service;

import org.springframework.context.annotation.Configuration;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
@Configuration
public class DocumentServiceImpl implements DocumentService {

    @Override
     public boolean moveDir(Path src, Path dest) {
        if (src.toFile().isDirectory()) {
            for (File file : src.toFile().listFiles()) {
               moveDir(file.toPath(), dest.resolve(src.relativize(file.toPath())));
            }
        }

        try {
            Files.move(src, dest, StandardCopyOption.REPLACE_EXISTING);
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    @Override
    public void search(File file) throws IOException {
        String builder = "";
        if (file.isDirectory()) {
            System.out.println("Searching directory ... " + file.getAbsoluteFile());

            //do you have permission to read this directory?
            if (file.canRead()) {
                for (File temp : file.listFiles()) {
                    if (temp.isDirectory()) {
                        search(temp);
                    } else {
                        calculateSha256(temp);
                    }
                }

            } else {
                System.out.println(file.getAbsoluteFile() + "Permission Denied");
            }
        }

    }
    @Override
    public void calculateSha256(File file) throws IOException {
        String algorithm = "SHA-256";
        String sha256ForAllFiles = "";
        // get file path from resources
        String fileName = file.getName();
        String filePath = file.getPath();

        File my_file = new File(filePath);

        byte[] hashInBytes = checksum(filePath, algorithm);
        String[] arr = filePath.split("\\.");
        if (arr.length < 3) {
            String newValue = fileName + "." + bytesToHex(hashInBytes);
            System.out.println(newValue);
            FileWriter writer = new FileWriter(filePath);
            writer.write(newValue);

            writer.close();

        }

    }
     @Override
     public byte[] checksum(String filePath, String algorithm) {

        MessageDigest md;
        try {
            md = MessageDigest.getInstance(algorithm);
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalArgumentException(e);
        }

        try (InputStream is = new FileInputStream(filePath);
             DigestInputStream dis = new DigestInputStream(is, md)) {
            while (dis.read() != -1) ; //empty loop to clear the data
            md = dis.getMessageDigest();
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
        return md.digest();

    }
    @Override
    public String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }
}
