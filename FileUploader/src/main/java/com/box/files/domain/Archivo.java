package com.box.files.domain;

import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDateTime;

@Entity
public class Archivo {

    public static final String HASH_TYPE_256 = "SHA-256";
    public static final String HASH_TYPE_512 = "SHA-512";

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idArchivo;

    private String fileName;

    private String hashSha256;

    private String hashSha512;

    private LocalDateTime lastUpload;

    @ManyToOne
    @JoinColumn(name = "ACCOUNT_ID")
    private Usuario usuario;

    public Archivo() { }

    public Archivo(String fileName, String hashSha256, String hashSha512, LocalDateTime lastUpload, Usuario usuario) {
        this.idArchivo = idArchivo;
        this.fileName = fileName;
        this.hashSha256 = hashSha256;
        this.hashSha512 = hashSha512;
        this.lastUpload = lastUpload;
        this.usuario = usuario;
    }

    public Long getIdArchivo() { return idArchivo; }

    public void setIdArchivo(Long idArchivo) { this.idArchivo = idArchivo; }

    public String getFileName() { return fileName; }

    public void setFileName(String fileName) { this.fileName = fileName; }

    public String getHashSha256() { return hashSha256; }

    public void setHashSha256(String hashSha256) { this.hashSha256 = hashSha256; }

    public String getHashSha512() { return hashSha512; }

    public void setHashSha512(String hashSha512) { this.hashSha512 = hashSha512; }

    public LocalDateTime getLastUpload() { return lastUpload; }

    public void setLastUpload(LocalDateTime lastUpload) { this.lastUpload = lastUpload; }

    public Usuario getUsuario() { return usuario; }

    public void setUsuario(Usuario usuario) { this.usuario = usuario; }

}
