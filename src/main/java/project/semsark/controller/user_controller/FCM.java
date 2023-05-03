package project.semsark.controller.user_controller;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import project.semsark.jwt.JwtUtil;
import project.semsark.model.entity.ChatRoom;
import project.semsark.model.entity.Message;
import project.semsark.model.entity.User;
import project.semsark.model.request_body.ChatRequest;
import project.semsark.repository.ChatRoomRepository;
import project.semsark.repository.MessageRepository;
import project.semsark.repository.UserRepository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@PreAuthorize("hasRole('ROLE_USER')")
public class FCM {

    @Autowired
    UserRepository userRepository;
    @Autowired
    MessageRepository messageRepository;
    @Autowired
    ChatRoomRepository chatRoomRepository;
    @Autowired
    JwtUtil jwtUtil;


    private final FirebaseDatabase database;

    public FCM(FirebaseApp firebaseApp) {
        database = FirebaseDatabase.getInstance(firebaseApp);
    }


    public Message saveMessage(ChatRequest chatRequest) {
        User user = jwtUtil.getUserDataFromToken();
        Message message = Message.builder()
                .message(chatRequest.getMessage())
                .dates(chatRequest.getDate())
                .status(chatRequest.isStatus())
                .receiverEmail(chatRequest.getReceiverEmail())
                .senderEmail(user.getEmail())
                .build();
        return messageRepository.save(message);
    }

    public Long check(Long senderId,Long receiverId){
        if(chatRoomRepository.findChatRoomBySenderIdAndReceiverId(senderId,receiverId).isPresent()){
            return chatRoomRepository.findChatRoomBySenderIdAndReceiverId(senderId,receiverId).get().getId();
        }
        else if(chatRoomRepository.findChatRoomBySenderIdAndReceiverId(receiverId,senderId).isPresent())
            return chatRoomRepository.findChatRoomBySenderIdAndReceiverId(receiverId,senderId).get().getId();

        return -1L;
    }

    @PostMapping("/chat-semsark")
    public void sendMessage(@RequestBody ChatRequest chatMessage) {
        Long receiver= userRepository.findByEmail(chatMessage.getReceiverEmail()).get().getId();
        User user = jwtUtil.getUserDataFromToken();

        Long id=check(user.getId(),receiver);

        DatabaseReference ref;

        if(id!=-1){
            ref = database.getReference("chat/"+id);
        }else{
            ChatRoom chatRoom=ChatRoom.builder()
                    .senderId(user.getId())
                    .receiverId(receiver)
                    .build();

            ref= database.getReference("chat/"+chatRoomRepository.save(chatRoom).getId());
        }


        ref.push().setValueAsync(saveMessage(chatMessage));
    }

    @GetMapping("/room-semsark/{receiverEmail}")
    public Long getRooms(@PathVariable String receiverEmail) {
        Long receiver= userRepository.findByEmail(receiverEmail).get().getId();
        User user = jwtUtil.getUserDataFromToken();

        Long id= check(user.getId(),receiver);


        if(id==-1){
            ChatRoom chatRoom=ChatRoom.builder()
                    .senderId(user.getId())
                    .receiverId(receiver)
                    .build();

            return chatRoomRepository.save(chatRoom).getId();
        }

        return id;
    }
}
