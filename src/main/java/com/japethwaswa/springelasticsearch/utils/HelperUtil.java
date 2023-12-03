package com.japethwaswa.springelasticsearch.utils;

import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.core.query.Criteria;
import org.springframework.data.elasticsearch.core.query.CriteriaQuery;
import org.springframework.data.elasticsearch.core.query.Query;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HelperUtil {
    public static final String FILES_INDEX = "files";
    public static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

    public static Query buildQuery(Map<String, Object> queryMap) {
//        //working start
//       NativeQueryBuilder nativeQueryBuilder  = NativeQuery.builder();
//        for(Map.Entry<String,String> entry:queryMap.entrySet()){
//            nativeQueryBuilder.withQuery(MatchQuery.of(m->m.field(entry.getKey()).query(entry.getValue()))._toQuery());
//        }
//        return nativeQueryBuilder.build();
//        //working end
        System.out.println(queryMap);

        //working start
        Criteria criteria = new Criteria();
        for (Map.Entry<String, Object> entry : queryMap.entrySet()) {
            criteria.and(entry.getKey()).is(entry.getValue());
        }
        return NativeQuery.builder().withQuery(new CriteriaQuery(criteria)).build();
        //working end
    }

    public static Map<String, Object> convertObjectToMap(Object obj, boolean withInheritedFields,boolean ignoreNullValues, List<String> excludedFields) {
        Map<String, Object> fieldMap = new HashMap<>();

        //if withInheritedFields then Get all fields,including inherited fields
        Class<?> clazz = obj.getClass();
        while (clazz != null) {
            for (Field field : clazz.getDeclaredFields()) {
                //make private fields accessible to retrieve their values
                field.setAccessible(true);
                try {
                    String fieldName  = field.getName();
                    Object objValue  = field.get(obj);

                    //check if value is null and null values allowed
                    if(!ignoreNullValues && objValue ==null)continue;

                    //check if field not allowed
                    if(excludedFields != null && !excludedFields.isEmpty() && excludedFields.contains(fieldName))continue;

                    //add field name and value to the map
                    fieldMap.put(fieldName, objValue);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }

            if (withInheritedFields) {
                //get inherited class
                clazz = clazz.getSuperclass();
            } else {
                clazz = null;
            }
        }

        return fieldMap;
    }

}
