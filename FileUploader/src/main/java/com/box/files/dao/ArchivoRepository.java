package com.box.files.dao;

import com.box.files.domain.Archivo;
import org.springframework.data.repository.CrudRepository;

public interface ArchivoRepository extends CrudRepository<Archivo, Long> {


    Archivo findByHashSha256(String hash256);

    Archivo findByHashSha512(String hash512);

    Archivo findByHashSha256AndUsuario_AccountId(String hashSha256, Long idUsuario);

    Archivo findByHashSha512AndUsuario_AccountId(String hashSha512, Long idUsuario);
}
