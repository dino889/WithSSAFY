package com.ssafy.withssafy.service.message;

import com.ssafy.withssafy.dto.message.MessageDto;
import com.ssafy.withssafy.entity.Message;
import com.ssafy.withssafy.repository.MessageRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class MessageServiceImpl implements MessageService{
    @Autowired
    MessageRepository messageRepository;

    @Autowired
    ModelMapper modelMapper;

    @Override
    public void sendMessage(MessageDto messageDto) {
        messageDto.setSend_dt(LocalDateTime.now());
        Message message = modelMapper.map(messageDto, Message.class);
        messageRepository.save(message);
    }

    @Override
    public List<MessageDto> findReceiveMessageByUid(Long id) {
        List<Message> messages = messageRepository.findReceiveMessageByUid(id);
        return messages.stream().map(message -> modelMapper.map(message, MessageDto.class)).collect(Collectors.toList());
    }

    @Override
    public List<MessageDto> findAll() {
        List<Message> messages = messageRepository.findAll();
        return messages.stream().map(message -> modelMapper.map(message, MessageDto.class)).collect(Collectors.toList());
    }

    @Override
    public List<MessageDto> findSendMessageByUid(Long id) {
        List<Message> messages = messageRepository.findSendMessageByUid(id);
        return messages.stream().map(message -> modelMapper.map(message, MessageDto.class)).collect(Collectors.toList());
    }

    @Override
    public List<MessageDto> findList(Long id) {
        List<Message> messages = messageRepository.findMyMessageList(id);
        List<MessageDto> res = new ArrayList<>();
        Set<Long> set = new HashSet<>();
        for(Message m: messages){
            long to = m.getU_to().getId();
            long from = m.getU_from().getId();
            long a = to==id?from:to;
            if(!set.contains(a)){
                set.add(a);
                res.add(modelMapper.map(m, MessageDto.class));
            }
        }
        return res;
    }

    @Override
    public List<MessageDto> findChatList(Long toId, Long fromId) {
        List<Message> messages = messageRepository.findChatList(toId, fromId);
        return messages.stream().map(message -> modelMapper.map(message, MessageDto.class)).collect(Collectors.toList());
    }

    @Override
    public List<Long> findCommonStudy(Long id1, Long id2) {
        return messageRepository.findCommonStudy(id1,id2);
    }

    @Override
    public void deleteMessage(Long id){
        messageRepository.deleteById(id);
    }

    @Override
    public void deleteMessageAll(Long id1, Long id2){
        messageRepository.deleteMessageAll(id1, id2);
    }
}
