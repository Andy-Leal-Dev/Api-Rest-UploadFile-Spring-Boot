package dev.andy.Api_Rest_UploadFile.services;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import javax.management.RuntimeErrorException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import jakarta.annotation.PostConstruct;

@Service
public class FileLocalStorageServices implements StorageServices{

    @Value("${media.location}")
    private String locationFile;

    private Path rootLocationFile; /** Ruta raiz de almacenamiento de archivos
     * @throws IOException */

    @Override
    @PostConstruct
    public void init() throws IOException {
        rootLocationFile = Paths.get(locationFile);
        Files.createDirectories(rootLocationFile);
    }

    @Override
    public String store(MultipartFile file) {
        try {
            if(file.isEmpty()){/** validamos que haya un archivo */
                throw new RuntimeErrorException(null,"Error. no hay un archivo");
            }

            String filename = file.getOriginalFilename(); //Obtenemos el nombre original del archivo

            Path destinationFile = rootLocationFile.resolve(Paths.get(filename))
            .normalize().
            toAbsolutePath();//Obtenemos la ruta de almacenamiento

            try (InputStream inputStream = file.getInputStream()){
                Files.copy(inputStream, destinationFile, StandardCopyOption.REPLACE_EXISTING);
            }

            return filename; //Retornamos el nombre del archivo
        } catch (Exception e) {
            throw new RuntimeErrorException(null,"Error. no hay un archivo");
        }
        
    }

    @Override
    public Resource loadReourceFile(String fileName) {
        try {
            Path file = rootLocationFile.resolve(fileName);
            Resource resource = new UrlResource(file.toUri());

            if(resource.exists() || resource.isReadable()){
                return resource;
            } else{
                throw new RuntimeErrorException(null,"Error. No se encontro el archivo" + fileName);
            }

        } catch (Exception e) {
            throw new RuntimeErrorException(null,"Error. No se encontro el archivo" + fileName);
        }
    }
    
}
