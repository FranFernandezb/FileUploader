package com.box.files.model;

import jakarta.annotation.Nullable;

public class DocumentData {

    @Nullable
    private byte[] file;

    private String filename;

    public DocumentData() { }

    public byte[] getFile() { return file; }

    public String getFilename() { return filename; }
}
