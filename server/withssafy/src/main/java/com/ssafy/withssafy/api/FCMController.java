package com.ssafy.withssafy.api;

import com.ssafy.withssafy.dto.firebase.FcmRequest;
import com.ssafy.withssafy.service.firebase.FCMService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/fcm")
@Api(tags = "FCM")
public class FCMController {

    private final FCMService fcmService;


    @ApiOperation(value = "전체 알림 전송", notes = "모든 사용자에게 공지사항 또는 이벤트와 같은 전체 알림을 전송한다.")
    @PostMapping("/broadcast")
    public ResponseEntity<?> broadCast(@RequestBody FcmRequest fcmRequest) throws Exception {
        fcmService.broadCastMessage(fcmRequest.getTitle(), fcmRequest.getBody(), fcmRequest.getImg(), fcmRequest.getType());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @ApiOperation(value = "개인 일정 알림 전송", notes = "개인 일정 알림을 전송한다.")
    @PostMapping("/sendMessageTo")
    public ResponseEntity<?> sendMessageTo(@RequestParam("token") String token, @RequestBody FcmRequest fcmRequest) throws Exception {
        fcmService.sendMessageTo(token, fcmRequest.getTitle(), fcmRequest.getBody(), fcmRequest.getImg(), fcmRequest.getType());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
