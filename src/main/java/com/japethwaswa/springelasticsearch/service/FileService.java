package com.japethwaswa.springelasticsearch.service;

import com.japethwaswa.springelasticsearch.model.ContentType;
import com.japethwaswa.springelasticsearch.model.FileModel;
import com.japethwaswa.springelasticsearch.repo.FileRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class FileService {

    @Autowired
    FileRepo fileRepo;

    public Iterable<FileModel> getFiles() {
        Sort pageNumberSort = Sort.by("pageNumber").descending();
        Sort dateSort = Sort.by("createdAt").descending();
        Sort multiSort = pageNumberSort.and(dateSort);
        return fileRepo.findAll(multiSort);
    }

    public Iterable<FileModel> getFiles(int pageNo, int pageSize) {

        //sort
        Sort pageNumberSort = Sort.by("pageNumber").descending();
        Sort dateSort = Sort.by("createdAt").descending();
        Sort multiSort = pageNumberSort.and(dateSort);

        //create page request object
        PageRequest pageRequest = PageRequest.of(pageNo, pageSize, multiSort);

        //pass it to repos
        Page<FileModel> paging = fileRepo.findAll(pageRequest);

        return paging.getContent();
    }

    public FileModel insertFile(FileModel file) {
        file.setId(UUID.randomUUID().toString());
        file.setCreatedAt(new Date());

        return fileRepo.save(file);
    }

    public Iterable<FileModel> insertManyFiles(List<FileModel> files) {
        for (FileModel file : files) {
            file.setId(UUID.randomUUID().toString());
            file.setCreatedAt(new Date());
        }
        return fileRepo.saveAll(files);
    }


    //    public FileModel updatedFile(FileModel file, String id) {
//        if (file == null) return null;
//
//        FileModel fileRecord = fileRepo.findById(id).orElse(null);
//
//        if (fileRecord == null) return null;
//
//        if (file.getContentType() != null) fileRecord.setContentType(file.getContentType());
//        if (file.getContent() != null) fileRecord.setContent(file.getContent());
//        if (file.getPageNumber() != 0) fileRecord.setPageNumber(file.getPageNumber());
//        if (file.getCreatedAt() != null) fileRecord.setCreatedAt(file.getCreatedAt());
//
//        return fileRecord;
//    }
//
//    public Iterable<FileModel> insertBulkFiles(List<FileModel> files) {
//        return fileRepo.saveAll(files);
//    }
//
//    public void deleteFile(String id) {
//        fileRepo.deleteById(id);
//    }
//
//    public Iterable<FileModel> findByContent(String content) {
//        return fileRepo.findAllByContent(content);
//    }
//
//    public Iterable<FileModel> findByContent(String fileId, String content) {
//        return fileRepo.findAllByContent(fileId, content);
//    }
    public Iterable<FileModel> findByContent(String fileId, String content, int pageNo, int pageSize) {
        //sort
        Sort pageNumberSort = Sort.by("pageNumber").descending();
        Sort dateSort = Sort.by("createdAt").descending();
        Sort multiSort = pageNumberSort.and(dateSort);

        //create page request object
        PageRequest pageRequest = PageRequest.of(pageNo, pageSize, multiSort);

        return fileRepo.findAllByContent(fileId, content, pageRequest);
    }

    public Iterable<FileModel> findByUser(String userId, String channel, String content, int pageNo, int pageSize) {
        //sort
        Sort pageNumberSort = Sort.by("pageNumber").descending();
        Sort dateSort = Sort.by("createdAt").descending();
        Sort multiSort = pageNumberSort.and(dateSort);

        //create page request object
        PageRequest pageRequest = PageRequest.of(pageNo, pageSize, multiSort);

        return fileRepo.findAllByUsersContent(userId, channel, content, pageRequest);
    }

//    public Iterable<FileModel> findAllByFileId(String fileId) {
//        return fileRepo.findAllByFileId(fileId);
//    }

    public void updateByFileIdAndContentType(String fileId, ContentType contentType, FileModel file) {
        fileRepo.updateFileByFileIdAndContentType(fileId, contentType, file);
    }

    public void deleteByFileIdContentType(String fileId, ContentType contentType) {
        fileRepo.deleteByFileIdAndContentType(fileId, contentType);
    }

    public void deleteAllFiles() {
        fileRepo.deleteAll();
    }

}
