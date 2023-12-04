package com.japethwaswa.springelasticsearch.repo;

import com.japethwaswa.springelasticsearch.model.ContentType;
import com.japethwaswa.springelasticsearch.model.FileModel;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface FileRepo extends ElasticsearchRepository<FileModel, String>, PagingAndSortingRepository<FileModel, String>, FileRepoCustom {
    //public interface FileRepo extends ElasticsearchRepository<FileModel,String> {
    List<FileModel> findAllByContent(String content);

    @Query("{\"bool\": {\"must\": [{\"match\": {\"fileId\": \"?0\"}}, {\"match\": {\"content\": \"?1\"}}]}}")
    List<FileModel> findAllByContent(String fileId, String content);

    @Query("{\"bool\": {\"must\": [{\"match\": {\"fileId\": \"?0\"}}, {\"match\": {\"content\": \"?1\"}}]}}")
    List<FileModel> findAllByContent(String fileId, String content, Pageable pageable);

    @Query("{\"bool\": {\"must\": [{\"match\": {\"userId\": \"?0\"}}, {\"match\": {\"channel\": \"?1\"}}, {\"match\": {\"content\": \"?2\"}}]}}")
    List<FileModel> findAllByUsersContent(String userId, String channel, String content, Pageable pageable);

    List<FileModel> findAllByFileId(String fileId);

    void deleteByFileIdAndContentType(String fileId, ContentType contentType);

}
