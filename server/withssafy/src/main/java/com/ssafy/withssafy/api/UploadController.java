package com.ssafy.withssafy.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("/upload")
public class UploadController {

    @PostMapping()
    public ResponseEntity<?> uploadComplete(HttpServletRequest servletRequest) throws IOException {
        Map<String, String[]> fileInfo = servletRequest.getParameterMap();

//        for(String key : fileInfo.keySet()){
//            String[] values = fileInfo.get(key);
//            System.out.println("KEY : " + key);
//            for(String value : values){
//                System.out.println(value);
//            }
//        }

        String responseFilename = null;
        if(fileInfo.containsKey("file_path")){
            String filePath = fileInfo.get("file_path")[0];
            String[] splitFilePath = filePath.split("/");

            responseFilename = splitFilePath[splitFilePath.length - 1];
        }

        return new ResponseEntity<>(responseFilename, HttpStatus.OK);
    }
}
