package com.ssafy.withssafy.api;

import com.ssafy.withssafy.util.FileManager;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;

@Controller
@RequiredArgsConstructor
@RequestMapping("/download")
@Api(tags = "다운로드 페이지")
public class DownloadController {

    @GetMapping()
    public void doDownload(HttpServletResponse res){
        FileManager.downloadAPK(res);
    }
}
