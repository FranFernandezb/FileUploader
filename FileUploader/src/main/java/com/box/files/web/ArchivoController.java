package com.box.files.web;

import com.box.files.domain.Archivo;
import com.box.files.model.DocumentData;
import com.box.files.model.DocumentJsonRequest;
import com.box.files.model.DocumentJsonResponse;
import com.box.files.model.SingleDocumentJsonResponse;
import com.box.files.service.ArchivoService;
import com.box.files.utils.ResponseGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/documents")
public class ArchivoController {

    @Autowired
    private ArchivoService archivoService;

    @Autowired
    private ResponseGenerator responseGenerator;

    @PostMapping(value = "/hash", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Object> uploadFiles(@RequestParam("hashType") String hashType,
                                              @RequestBody DocumentJsonRequest documents,
                                              @RequestHeader("Authorization") String token) {

        List<Map<String, String>> responseDocuments = new ArrayList<>();
        String path = "/api/documents/hash";
        try {
            if (!this.archivoService.isHashTypeValid(hashType)) {
                return this.responseGenerator.buildErrorResponse(HttpStatus.BAD_REQUEST, "El parámetro 'hashType' solo puede ser 'SHA-256' o 'SHA-512'.", path);
            }

            if (documents.getDocuments().isEmpty()) {
                return this.responseGenerator.buildErrorResponse(HttpStatus.BAD_REQUEST, "No se subieron archivos.", path);
            }
            for (DocumentData document : documents.getDocuments()) {
                byte[] file = document.getFile();
                String hash256 = this.archivoService.encodeToHash256(file);
                String hash512 = this.archivoService.encodeToHash512(file);

                Archivo archivo = this.archivoService.createAndSaveFile(hash512, hash256, document.getFilename());

                // Agregar información de respuesta al documento
                Map<String, String> responseDocument = new HashMap<>();
                responseDocument.put("fileName", archivo.getFileName());
                String hash = this.archivoService.isHashType256(hashType) ? archivo.getHashSha256() : archivo.getHashSha512();
                responseDocument.put("hash", hash);

                if (archivo.getLastUpload() != null) {
                    responseDocument.put("lastUpload", String.valueOf(archivo.getLastUpload()));
                }
                responseDocuments.add(responseDocument);
            }


            // Construir la respuesta del endpoint
            Map<String, Object> responseBody = new HashMap<>();
            responseBody.put("algorithm", hashType);
            responseBody.put("documents", responseDocuments);

            return ResponseEntity.status(HttpStatus.CREATED).body(responseBody);
        } catch (Exception e) {
            return this.responseGenerator.buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Error inesperado.", path);
        }
    }

    @GetMapping()
    public ResponseEntity<Object> getAllDocuments(@RequestHeader("Authorization") String token) {
        List<DocumentJsonResponse> documentResponses = new ArrayList<>();
        String path = "/api/documents";

        try {
            List<Archivo> archivos = this.archivoService.findByUsuario(Long.valueOf(55));

            for (Archivo archivo : archivos) {
                DocumentJsonResponse documentResponse = null;
                if (archivo.getLastUpload() != null) {
                    documentResponse = new DocumentJsonResponse(
                            archivo.getFileName(),
                            archivo.getHashSha256(),
                            archivo.getHashSha512(),
                            archivo.getLastUpload()
                    );
                } else {
                    documentResponse = new DocumentJsonResponse(archivo.getFileName(),
                            archivo.getHashSha256(),
                            archivo.getHashSha512());
                }
                documentResponses.add(documentResponse);
            }
            return ResponseEntity.ok(documentResponses);
        } catch (Exception e) {
            return this.responseGenerator.buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Error inesperado.", path);
         }
    }

    @GetMapping()
    public ResponseEntity<Object> getDocumentByHash(@RequestParam("hashType") String hashType,
                                                    @RequestParam("hash") String hash,
                                                    @RequestHeader("Authorization") String token) {

        String path = "/api/documents";
        try {
            if (!this.archivoService.isHashTypeValid(hashType)) {
                NoSuchFieldException noSuchFieldException = new NoSuchFieldException();
                throw noSuchFieldException;
            }
            boolean isHash256 = this.archivoService.isHashType256(hashType);
            Archivo archivo;

            if (isHash256) {
                archivo = this.archivoService.findArchivoByHashSha256AndUsuarioId(hash, Long.valueOf(2222));
            } else {
                archivo = this.archivoService.findArchivoByHashSha512AndUsuarioId(hash, Long.valueOf(55));
            }

            if (archivo == null) {
                return ResponseEntity.notFound().build();
            }
            SingleDocumentJsonResponse documentResponse = new SingleDocumentJsonResponse();

            String responseHash = isHash256 ? archivo.getHashSha256() : archivo.getHashSha512();
            documentResponse.setHash(responseHash);
            documentResponse.setFilename(archivo.getFileName());
            LocalDateTime lastUpload = archivo.getLastUpload();
            if (lastUpload != null) {
                documentResponse.setLastUpload(lastUpload);
            }

            return ResponseEntity.ok(documentResponse);
        } catch (NoSuchFieldException n) {
            return this.responseGenerator.buildErrorResponse(HttpStatus.BAD_REQUEST, "El parámetro ‘hash’ solo puede ser ‘SHA-256’ o ‘SHA-512’", path);
        } catch (Exception e) {
            return this.responseGenerator.buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Error inesperado.", path);
        }
    }


}
