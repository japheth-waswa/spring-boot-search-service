package com.japethwaswa.springelasticsearch.redis.subscriber;

import com.google.gson.Gson;
import com.japethwaswa.springelasticsearch.model.FileModel;
import com.japethwaswa.springelasticsearch.model.Ocr;
import com.japethwaswa.springelasticsearch.service.FileService;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ProcessedFileSubscriber implements MessageListener {

    private RedisTemplate<String, Object> redisTemplate;

//    @Autowired
    private FileService fileService;

    public ProcessedFileSubscriber(RedisTemplate<String, Object> redisTemplate,FileService fileService) {
        this.redisTemplate = redisTemplate;
        this.fileService = fileService;
    }

    @Override
    public void onMessage(Message message, byte[] pattern) {
        Gson gson = new Gson();
        Ocr ocr = gson.fromJson(message.toString(), Ocr.class);
//        System.out.println("Message Received -> " + ocr);

        if (ocr == null || ocr.fileId() == null || ocr.contentType() == null || ocr.data() == null || ocr.data().isEmpty())
            return;

        //delete
        fileService.deleteByFileIdContentType(ocr.fileId(), ocr.contentType());

        //prepare bulk files
        List<FileModel> files = new ArrayList<>();

        for (Map.Entry<Integer, String> pageContent : ocr.data().entrySet()) {
            files.add(new FileModel(null, ocr.fileId(), ocr.contentType(), pageContent.getValue(), pageContent.getKey(), null,ocr.userId(),ocr.channel(),ocr.fileName()));
        }

        //insert bulk
        fileService.insertManyFiles(files);

    }
}
