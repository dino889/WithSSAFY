package com.ssafy.withssafy.service.firebase;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.auth.oauth2.GoogleCredentials;
import com.ssafy.withssafy.dto.firebase.FcmMessage;
import com.ssafy.withssafy.dto.notification.NotificationRequestDto;
import com.ssafy.withssafy.dto.user.UserDto;
import com.ssafy.withssafy.service.notification.NotificationService;
import com.ssafy.withssafy.service.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import okhttp3.*;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;


@Service
@Slf4j
@RequiredArgsConstructor
public class FCMService {
    private final String API_URL = "https://fcm.googleapis.com/v1/projects/withssafy/messages:send";

    @Value("${firebase-sdk-path}")
    private String firebaseSdkPath;

    private final ObjectMapper objectMapper;

    @Autowired
    UserService userService;

    @Autowired
    NotificationService notificationService;


    /**
     * FCM에 push 요청을 보낼 때 인증을 위해 Header에 포함시킬 AccessToken 생성
     * @return
     * @throws IOException
     */
    private String getAccessToken() throws IOException {

        GoogleCredentials googleCredentials = GoogleCredentials
                .fromStream(new ClassPathResource(firebaseSdkPath).getInputStream())
                .createScoped(Arrays.asList("https://www.googleapis.com/auth/cloud-platform"));

        googleCredentials.refreshIfExpired();

        return googleCredentials.getAccessToken().getTokenValue();
    }

    /**
     * FCM 알림 메시지 생성
     * @param targetToken
     * @param title
     * @param body
     * @param img
     * @return
     * @throws JsonProcessingException
     */
    private String makeMessage(String targetToken, String title, String body, String img) throws JsonProcessingException {
        FcmMessage fcmMessage = FcmMessage.builder()
                .message(FcmMessage.Message.builder()
                        .token(targetToken)
                        .notification(FcmMessage.Notification.builder()
                                .title(title)
                                .body(body)
                                .image(img)
                                .build()
                        )
                        .build()
                ).validate_only(false).build();

        log.info(objectMapper.writeValueAsString(fcmMessage));
        return objectMapper.writeValueAsString(fcmMessage);
    }

    /**
     * targetToken에 해당하는 device로 FCM 푸시 알림 전송
     * @param targetToken
     * @param title
     * @param body
     * @param type
     * @param type
     * @throws Exception
     */
    public void sendMessageTo(String targetToken, String title, String body, String img, int type) throws Exception {
        String message = makeMessage(targetToken, title, body, img);
        OkHttpClient client = new OkHttpClient();
        RequestBody requestBody = RequestBody.create(message, MediaType.get("application/json; charset=utf-8"));
        Request request = new Request.Builder()
                .url(API_URL)
                .post(requestBody)
                .addHeader(HttpHeaders.AUTHORIZATION, "Bearer " + getAccessToken())
                .addHeader(HttpHeaders.CONTENT_TYPE, "application/json; UTF-8")
                .build();

        Response response = client.newCall(request).execute();

//        log.info(response.body().string());

        if(response.isSuccessful()) {
            NotificationRequestDto noti = new NotificationRequestDto();
            UserDto user = userService.findByDeviceToken(targetToken);
            noti.setUser(user.getId());
            noti.setTitle(title);
            noti.setContent(body);
            noti.setType(type);
            notificationService.insert(noti);
        }
    }

    /**
     * 등록된 모든 토큰을 이용해서 broadcasting
     * @param title
     * @param body
     * @param img
     * @param type
     * @return
     * @throws Exception
     */
    public int broadCastMessage(String title, String body, String img, int type) throws Exception {
        // path는 application 초기 화면
        List<UserDto> users = userService.findAll();
        for (UserDto user : users) {
            if (user.getDeviceToken() != null) {
//            if(user != null && !user.getToken().isEmpty() && user.getToken() != null) {
                log.debug("broadcastmessage : {},{},{}", user.getDeviceToken(), title, body);
                sendMessageTo(user.getDeviceToken(), title, body, img, type);
            }
        }
        return users.size();
    }

}
