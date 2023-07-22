package com.box.files.utils;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Component
public class ResponseGenerator {

    public ResponseEntity<Object> buildErrorResponse(HttpStatus status, String message, String path) {
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("error", Collections.singletonMap(
                "timestamp", System.currentTimeMillis()
        ));
        errorResponse.put("status", status.value());
        errorResponse.put("message", message);
        errorResponse.put("path", path);
        return ResponseEntity.status(status).body(errorResponse);
    }
}
