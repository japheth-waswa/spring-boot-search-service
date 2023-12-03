package com.japethwaswa.springelasticsearch.repo;

import com.japethwaswa.springelasticsearch.model.ContentType;
import com.japethwaswa.springelasticsearch.model.FileModel;
import org.springframework.stereotype.Repository;

@Repository
public interface FileRepoCustom {
    void updateFileByFileIdAndContentType(String fileId, ContentType contentType, FileModel file);

}
