package com.japethwaswa.springelasticsearch.service;

import com.japethwaswa.springelasticsearch.model.ContentType;
import com.japethwaswa.springelasticsearch.model.FileModel;
import com.japethwaswa.springelasticsearch.repo.FileRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class FileService {

    @Autowired
    FileRepo fileRepo;

    public Iterable<FileModel> getFiles() {
        return fileRepo.findAll();
    }

    public FileModel insertFile(FileModel file) {
        file.setId(UUID.randomUUID().toString());
        file.setCreatedAt(new Date());
        return fileRepo.save(file);
    }

    public FileModel updatedFile(FileModel file, String id) {
        if (file == null) return null;

        FileModel fileRecord = fileRepo.findById(id).orElse(null);

        if (fileRecord == null) return null;

        if (file.getContentType() != null) fileRecord.setContentType(file.getContentType());
        if (file.getContent() != null) fileRecord.setContent(file.getContent());
        if (file.getPageNumber() != 0) fileRecord.setPageNumber(file.getPageNumber());
        if (file.getCreatedAt() != null) fileRecord.setCreatedAt(file.getCreatedAt());

        return fileRecord;
    }

    public Iterable<FileModel> insertFileBulk(List<FileModel> files) {
        return fileRepo.saveAll(files);
    }

    public void deleteFile(String id) {
        fileRepo.deleteById(id);
    }

    public Iterable<FileModel> findByContent(String content) {
        return fileRepo.findAllByContent(content);
    }

    public Iterable<FileModel> findByContent(String fileId, String content) {
        return fileRepo.findAllByContent(fileId, content);
    }

    public Iterable<FileModel> findAllByFileId(String fileId) {
        return fileRepo.findAllByFileId(fileId);
    }

    public void updateByFileIdAndContentType(String fileId, ContentType contentType, FileModel file){
        fileRepo.updateFileByFileIdAndContentType(fileId,contentType,file);
    }
    public void deleteByFileIdContentType(String fileId, ContentType contentType){
        fileRepo.deleteByFileIdAndContentType( fileId,  contentType);
    }

}
