package com.box.files.service;

import com.box.files.domain.Archivo;

import java.util.List;

public interface ArchivoService {

    List<Archivo> findAll();

    Archivo findByHashSha256(String hash256);

    Archivo findByHashSha512(String hash512);

    Archivo findArchivoByHashSha256AndUsuarioId(String hash256, Long usuarioId);

    Archivo findArchivoByHashSha512AndUsuarioId(String hash256, Long usuarioId);

    boolean isHashTypeValid(String hashType);
}
