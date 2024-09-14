package dev.andy.Api_Rest_UploadFile.services;


import java.io.IOException;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface StorageServices {
    void init() throws IOException; /**Metodo auxiliar */

    String store(MultipartFile file); /**Metodo para subir un archivo */

    Resource loadReourceFile(String fileName);/**Metodo para Cargar un archivo */
}
