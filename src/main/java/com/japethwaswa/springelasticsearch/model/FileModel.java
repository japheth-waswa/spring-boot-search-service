package com.japethwaswa.springelasticsearch.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.japethwaswa.springelasticsearch.utils.HelperUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Document(indexName = HelperUtil.FILES_INDEX)
public class FileModel {

    @Id
    @Field(type = FieldType.Keyword)
    private String id;

    @Field(type = FieldType.Keyword)
    private String fileId;

    @Field(type = FieldType.Keyword)
    private ContentType contentType;

    private String content;

    private int pageNumber;

    @JsonFormat(pattern = HelperUtil.DATE_FORMAT)
    private Date createdAt;
}
