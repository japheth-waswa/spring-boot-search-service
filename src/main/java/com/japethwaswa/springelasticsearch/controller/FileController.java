package com.japethwaswa.springelasticsearch.controller;

import com.japethwaswa.springelasticsearch.model.FileModel;
import com.japethwaswa.springelasticsearch.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class FileController {

    @Autowired
    private FileService fileService;

    @GetMapping("/api/file")
    public Iterable<FileModel> find() {
        return fileService.getFiles();
    }

    @GetMapping("/api/file/paginate")
    public Iterable<FileModel> findWithPagination(@RequestParam int pageNo, @RequestParam int pageSize) {
        return fileService.getFiles(pageNo, pageSize);
    }

    @GetMapping("/api/file/list")
    public Iterable<FileModel> findWithList(@RequestParam String userId, @RequestParam String channel, @RequestParam int pageNo, @RequestParam int pageSize, @RequestParam String searchTerm) {
        return fileService.findByUser(userId, channel, searchTerm, pageNo, pageSize);
    }

    @PostMapping("/api/file")
    public FileModel insertFile(@RequestBody FileModel file) {
        return fileService.insertFile(file);
    }

    @PostMapping("/api/file/multiple")
    public Iterable<FileModel> insertMultipleFile(@RequestBody List<FileModel> files) {
        return fileService.insertManyFiles(files);
    }

    @DeleteMapping("/api/file")
    public void deleteFiles(@RequestBody FileModel file) {
        fileService.deleteByFileIdContentType(file.getFileId(), file.getContentType());
    }

    @DeleteMapping("/api/file-delete-all")
    public void deleteAllFiles() {
        fileService.deleteAllFiles();
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
    public Iterable<FileModel> searchByContent(@RequestParam String fileId, @RequestParam String searchTerm, @RequestParam int pageNo, @RequestParam int pageSize

    ) {
        return fileService.findByContent(fileId, searchTerm, pageNo, pageSize);
    }

    @PatchMapping("/api/file/update")
//    public void updateFile(@RequestBody String fileId, @RequestBody ContentType contentType, @RequestBody FileModel file) {
    public void updateFile(@RequestBody FileModel file) {
//        System.out.println(fileId);
//        System.out.println(contentType);
        System.out.println(file);
        System.out.println("*".repeat(50));
        fileService.updateByFileIdAndContentType(file.getFileId(), file.getContentType(), file);
    }
}
