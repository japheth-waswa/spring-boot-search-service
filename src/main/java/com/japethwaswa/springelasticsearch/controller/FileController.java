package com.japethwaswa.springelasticsearch.controller;

import com.japethwaswa.springelasticsearch.model.FileModel;
import com.japethwaswa.springelasticsearch.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class FileController {

    @Autowired
    private FileService fileService;

    @GetMapping("/api/file")
    public Iterable<FileModel> find() {
        return fileService.getFiles();
    }

    @PostMapping("/api/file")
    public FileModel insertFile(@RequestBody FileModel file) {
        return fileService.insertFile(file);
    }
    @DeleteMapping("/api/file")
    public void deleteFiles(@RequestBody  FileModel file) {
         fileService.deleteByFileIdContentType( file.getFileId(),  file.getContentType());
    }

//    @GetMapping("/api/file/search-by-content")
//    public Iterable<FileModel> searchByContent(@RequestParam String searchTerm
//    ) {
//        return fileService.findByContent(searchTerm);
//    }
//    @GetMapping("/api/file/search-by-file-id")
//    public Iterable<FileModel> findAllByFileId(@RequestParam String searchTerm
//    ) {
//        return fileService.findAllByFileId(searchTerm);
//    }

    @GetMapping("/api/file/search-by-content-file-id")
    public Iterable<FileModel> searchByContent(@RequestParam String fileId, @RequestParam String searchTerm

    ) {
        return fileService.findByContent(fileId, searchTerm);
    }

    @PatchMapping("/api/file/update")
//    public void updateFile(@RequestBody String fileId, @RequestBody ContentType contentType, @RequestBody FileModel file) {
    public void updateFile(@RequestBody FileModel file) {
//        System.out.println(fileId);
//        System.out.println(contentType);
        System.out.println(file);
        System.out.println("*".repeat(50));
         fileService.updateByFileIdAndContentType(file.getFileId(),file.getContentType(),file);
    }
}
