package com.japethwaswa.springelasticsearch.repo;

import com.japethwaswa.springelasticsearch.model.ContentType;
import com.japethwaswa.springelasticsearch.model.FileModel;
import com.japethwaswa.springelasticsearch.utils.HelperUtil;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.data.elasticsearch.core.query.UpdateQuery;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class FileRepoImpl implements FileRepoCustom {
    private final ElasticsearchOperations elasticsearchOperations;

    public FileRepoImpl(ElasticsearchOperations elasticsearchOperations) {
        this.elasticsearchOperations = elasticsearchOperations;
    }

    @Override
    public void updateFileByFileIdAndContentType(String fileId, ContentType contentType, FileModel file) {
        Map<String, Object> queryFieldsMap = new HashMap<>();
        queryFieldsMap.put("fileId", fileId);
        queryFieldsMap.put("contentType", contentType);

        //build query
        Query query = HelperUtil.buildQuery(queryFieldsMap);

        //map fields and their new values
//        Map<String, Object> fieldsMap = HelperUtil.convertObjectToMap(file, false, true,new ArrayList<>(List.of("id", "createdAt","contentType","fileId")));
//        Map<String, Object> fieldsMap = HelperUtil.convertObjectToMap(file, false, true,new ArrayList<>(List.of("createdAt","contentType","fileId")));
        Map<String, Object> fieldsMap = HelperUtil.convertObjectToMap(file, false, true,null);

//        /**
        //parse update script
        StringBuilder updateScript = new StringBuilder();
        for (Map.Entry<String, Object> field : fieldsMap.entrySet()) {
            updateScript.append("ctx._source.").append(field.getKey()).append("=params.").append(field.getKey()).append(";");
        }
        String updateScriptStr  = updateScript.toString();
//        **/
//        String updateScriptStr  = "ctx._source=params.data";
        System.out.println("%".repeat(50));
        System.out.println(fieldsMap);
        System.out.println(updateScriptStr);
        System.out.println("^".repeat(50));

        //new update script
//        Map<String,Object> param = new HashMap<>();
//        param.put("data",fieldsMap);

        //todo this updateQuery does not work, later on we have to find a way to update this.
        UpdateQuery updateQuery = UpdateQuery.builder(query)
//        UpdateQuery updateQuery = UpdateQuery.builder(file.getId())
//                .withScriptType(ScriptType.INLINE)
                .withScript(updateScriptStr)
                .withParams(fieldsMap)
//                .withParams(param)
                .build();



//         elasticsearchOperations.update(updateQuery,FileModel.class);
        elasticsearchOperations.update(updateQuery);
//        elasticsearchOperations.updateByQuery(updateQuery, IndexCoordinates.of(HelperUtil.FILES_INDEX));
    }

}
