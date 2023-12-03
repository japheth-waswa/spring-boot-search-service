package com.japethwaswa.springelasticsearch.repo;

import com.japethwaswa.springelasticsearch.model.ContentType;
import com.japethwaswa.springelasticsearch.model.FileModel;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface FileRepo extends ElasticsearchRepository<FileModel,String>,FileRepoCustom {
//public interface FileRepo extends ElasticsearchRepository<FileModel,String> {
    List<FileModel> findAllByContent(String content);

    @Query("{\"bool\": {\"must\": [{\"match\": {\"fileId\": \"?0\"}}, {\"match\": {\"content\": \"?1\"}}]}}")
    List<FileModel> findAllByContent(String fileId,String content);
    List<FileModel> findAllByFileId(String fileId);

    void deleteByFileIdAndContentType(String fileId, ContentType contentType);

}
