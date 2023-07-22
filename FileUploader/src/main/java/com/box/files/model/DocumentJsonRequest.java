package com.box.files.model;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public class DocumentJsonRequest {

    private List<DocumentData> documents;

    public DocumentJsonRequest() { }


    public List<DocumentData> getDocuments() { return documents; }
}
