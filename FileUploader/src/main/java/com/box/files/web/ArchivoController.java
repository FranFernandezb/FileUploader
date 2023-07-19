package com.box.files.web;

import com.box.files.model.DocumentJsonRequest;
import com.box.files.service.ArchivoService;
import com.box.files.utils.ResponseGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/files")
public class ArchivoController {

    @Autowired
    private ArchivoService archivoService;

    @Autowired
    private ResponseGenerator responseGenerator;

    @PostMapping(value = "/hash", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Object> uploadFiles(@RequestParam("hashType") String hashType,
                                              @RequestBody DocumentJsonRequest documents,
                                              @RequestHeader("Authorization") String token) {
        try {
            if (!this.archivoService.isHashTypeValid(hashType)) {
                return this.responseGenerator.buildErrorResponse(HttpStatus.BAD_REQUEST, "El parámetro 'hashType' solo puede ser 'SHA-256' o 'SHA-512'.");
            }

            if (documents.getDocuments().isEmpty()) {
                return this.responseGenerator.buildErrorResponse(HttpStatus.BAD_REQUEST, "No se subieron archivos.");
            }

            //TODO: ADD ENCODER.

            //TODO: SAVE ARCHIVO.

            List<Map<String, String>> responseDocuments = new ArrayList<>();
            for (MultipartFile file : documents) {
                String hash = calculateHash(file, hashType);

                // Crear el registro y guardar en la base de datos
                FileRecord fileRecord = new FileRecord();
                fileRecord.setFileName(file.getOriginalFilename());
                if ("SHA-256".equals(hashType)) {
                    fileRecord.setHashSha256(hash);
                } else if ("SHA-512".equals(hashType)) {
                    fileRecord.setHashSha512(hash);
                }
                fileRecord.setAccount(accountRepository.findByUsername(userDetails.getUsername()));
                fileRecordRepository.save(fileRecord);

                // Agregar información de respuesta al documento
                Map<String, String> responseDocument = new HashMap<>();
                responseDocument.put("fileName", file.getOriginalFilename());
                responseDocument.put("hash", hash);
                responseDocuments.add(responseDocument);
            }

            // Construir la respuesta del endpoint
            Map<String, Object> responseBody = new HashMap<>();
            responseBody.put("algorithm", hashType);
            responseBody.put("documents", responseDocuments);

            return ResponseEntity.status(HttpStatus.CREATED).body(responseBody);
        } catch (IOException e) {
            return this.responseGenerator.buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Error al procesar los archivos.");
        } catch (Exception e) {
            return this.responseGenerator.buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Error inesperado.");
        }
    }
}
