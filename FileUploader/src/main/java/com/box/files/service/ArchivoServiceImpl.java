package com.box.files.service;

import com.box.files.dao.ArchivoRepository;
import com.box.files.domain.Archivo;
import com.box.files.domain.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

@Component
@Transactional
public class ArchivoServiceImpl implements ArchivoService{

    @Autowired
    private ArchivoRepository archivoRepository;


    @Override
    public List<Archivo> findAll() {
        List<Archivo> archivos = (List<Archivo>) this.archivoRepository.findAll();
        return archivos;
    }

    @Override
    public Archivo findByHashSha256(String hash256) {
        return this.archivoRepository.findByHashSha256(hash256);
    }

    @Override
    public Archivo findByHashSha512(String hash512) {
        return this.archivoRepository.findByHashSha512(hash512);
    }

    @Override
    public Archivo findArchivoByHashSha256AndUsuarioId(String hashSha256, Long idUsuario) {
        return archivoRepository.findByHashSha256AndUsuario_AccountId(hashSha256, idUsuario);
    }

    @Override
    public Archivo findArchivoByHashSha512AndUsuarioId(String hash256, Long usuarioId) {
        return archivoRepository.findByHashSha512AndUsuario_AccountId(hash256, usuarioId);
    }

    @Override
    public boolean isHashTypeValid(String hashType) {
        boolean isValid = Boolean.FALSE;
        if (hashType.equalsIgnoreCase(Archivo.HASH_TYPE_256) ||
                hashType.equalsIgnoreCase(Archivo.HASH_TYPE_512)) {
            isValid = Boolean.TRUE;
        }
        return isValid;
    }


    @Override
    public String encodeToHash256(byte[] file) {
        StringBuilder sb = null;
        try {
            MessageDigest md = MessageDigest.getInstance(Archivo.HASH_TYPE_256);
            byte[] hashBytes = md.digest(file);

            sb = new StringBuilder();
            for (byte b : hashBytes) {
                sb.append(String.format("%02x", b));
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return sb.toString();

    }

    @Override
    public String encodeToHash512(byte[] file) {
        StringBuilder sb = null;
        try {
            MessageDigest md = MessageDigest.getInstance(Archivo.HASH_TYPE_512);
            byte[] hashBytes = md.digest(file);

            sb = new StringBuilder();
            for (byte b : hashBytes) {
                sb.append(String.format("%02x", b));
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    @Override
    public Archivo createAndSaveFile(String hash512, String hash256, String filename) {

        //FIXME: ACÁ DEBO METER EL USUARIO QUE LO GUARDÓ.
        Usuario usuario = new Usuario();
        Archivo archivo = new Archivo(filename, hash256, hash512, null, usuario);

        this.archivoRepository.save(archivo);

        return archivo;
    }

    @Override
    public boolean isHashType256(String hashType) {
        return hashType.equalsIgnoreCase(Archivo.HASH_TYPE_256);
    }

    @Override
    public List<Archivo> findByUsuario(Long accountId) {
        return this.archivoRepository.findByUsuario_AccountId(accountId);
    }


}
