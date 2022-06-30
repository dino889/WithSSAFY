package com.ssafy.withssafy.service.message;

import com.ssafy.withssafy.dto.message.MessageDto;

import java.util.List;

public interface MessageService {
    public void sendMessage(MessageDto messageDto);
    public List<MessageDto> findReceiveMessageByUid(Long u_id);
    public List<MessageDto> findAll();
    public List<MessageDto> findSendMessageByUid(Long id);
    public List<MessageDto> findList(Long id);
    public List<MessageDto> findChatList(Long toId, Long fromId);
    public List<Long> findCommonStudy(Long id1, Long id2);
    public void deleteMessage(Long id);
    public void deleteMessageAll(Long id1, Long id2);
}
