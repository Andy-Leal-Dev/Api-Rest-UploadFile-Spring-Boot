package dev.andy.Api_Rest_UploadFile.controller;

import dev.andy.Api_Rest_UploadFile.services.StorageServices;
import lombok.AllArgsConstructor;

import java.io.IOException;
import java.nio.file.Files;
import java.util.Map;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;



@RestController
@AllArgsConstructor
public class UploadController {

    private final StorageServices storageServices;

    @PostMapping("Upload")
    public Map<String, String> loadMediaFile(@RequestParam("file") MultipartFile file) {
        
        String path = this.storageServices.store(file);
        String url = ServletUriComponentsBuilder.fromPath("/media/").path(path).toUriString();

        return Map.of("url", url);
    }
 
    @GetMapping("/Get/{filename}")
    public ResponseEntity<Resource> getFile(@PathVariable String filename) throws IOException {

        Resource file = storageServices.loadReourceFile(filename);
        String contenType = Files.probeContentType(file.getFile().toPath());

        return ResponseEntity.ok().header(HttpHeaders.CONTENT_TYPE, contenType).body(file);
    }
        

}
