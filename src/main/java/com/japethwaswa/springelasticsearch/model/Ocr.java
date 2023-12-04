package com.japethwaswa.springelasticsearch.model;

import java.util.Map;

public record Ocr(String fileId, ContentType contentType, Map<Integer,String>data,String userId,String channel,String fileName) {
}
