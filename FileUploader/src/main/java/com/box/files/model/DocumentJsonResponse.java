package com.box.files.model;

import java.time.LocalDateTime;

public class DocumentJsonResponse {

        private String fileName;
        private String hashSha256;
        private String hashSha512;
        private LocalDateTime lastUpload;

    public DocumentJsonResponse(String fileName, String hashSha256, String hashSha512, LocalDateTime lastUpload) {
        this.fileName = fileName;
        this.hashSha256 = hashSha256;
        this.hashSha512 = hashSha512;
        this.lastUpload = lastUpload;
    }

    public DocumentJsonResponse(String fileName, String hashSha256, String hashSha512) {
        this.fileName = fileName;
        this.hashSha256 = hashSha256;
        this.hashSha512 = hashSha512;
    }

    public String getFileName() { return fileName; }

    public void setFileName(String fileName) { this.fileName = fileName; }

    public String getHashSha256() { return hashSha256; }

    public void setHashSha256(String hashSha256) { this.hashSha256 = hashSha256; }

    public String getHashSha512() { return hashSha512; }

    public void setHashSha512(String hashSha512) { this.hashSha512 = hashSha512; }

    public LocalDateTime getLastUpload() { return lastUpload; }

    public void setLastUpload(LocalDateTime lastUpload) { this.lastUpload = lastUpload; }

}
