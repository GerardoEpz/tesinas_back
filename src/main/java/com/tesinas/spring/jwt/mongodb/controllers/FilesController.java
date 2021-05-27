package com.tesinas.spring.jwt.mongodb.controllers;
import org.springframework.core.io.Resource;
import com.tesinas.spring.jwt.mongodb.models.FileInfo;
import com.tesinas.spring.jwt.mongodb.payload.response.MessageResponse;
import com.tesinas.spring.jwt.mongodb.services.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/files")
public class FilesController {

    @Autowired
    FileStorageService storageService;

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/upload-tesina")
    public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file){

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = ((UserDetails)principal).getUsername();
        final Path folder = Paths.get("uploads/tesinas/"+username+".docx");
        try {
            byte[] bytes = file.getBytes();
            storageService.deleteAll(folder); //clean the folder
            storageService.save(bytes,folder); //save de file
            MessageResponse message = new MessageResponse("Uploaded the file successfully: " + file.getOriginalFilename());
            return ResponseEntity.status(HttpStatus.OK).body(message.getMessage());
        } catch (Exception e){
            MessageResponse message = new MessageResponse("Could not upload the file: " + file.getOriginalFilename() + "!");

            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(message.getMessage());
        }
    }

    @GetMapping("/get-tesinas")
    public ResponseEntity<List<FileInfo>> getListFiles(){
        final Path folder = Paths.get("uploads/tesinas/");
        List<FileInfo> fileInfos = storageService.loadAll(folder).map(path -> {
            String filename = path.getFileName().toString();
            String url = MvcUriComponentsBuilder
                    .fromMethodName(FilesController.class, "getFile", path.getFileName().toString()).build().toString();

            return new FileInfo(filename, url);
        }).collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK).body(fileInfos);
    }

    @GetMapping("file/tesina/{filename:.+}")
    @ResponseBody
    public ResponseEntity<?> getFile(@PathVariable String filename){
//        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        String username = ((UserDetails)principal).getUsername();
        try {
            final Path folder = Paths.get("uploads/tesinas");
            Resource file = storageService.load(filename, folder);
            return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"").body(file);
        } catch (RuntimeException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
