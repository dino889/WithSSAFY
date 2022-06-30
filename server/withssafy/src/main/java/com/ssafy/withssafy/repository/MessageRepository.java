package com.ssafy.withssafy.repository;

import com.ssafy.withssafy.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

public interface MessageRepository  extends JpaRepository<Message,Long> {

    @Query(value = "SELECT * FROM tbl_message WHERE u_to_id=:id", nativeQuery = true)
    List<Message> findReceiveMessageByUid(Long id);

    @Query(value = "SELECT * FROM tbl_message WHERE u_from_id=:id", nativeQuery = true)
    List<Message> findSendMessageByUid(Long id);

//    @Query(value = "select m1.id, m1.content, m1.send_dt, m1.u_from_id, m1.u_to_id\n" +
//            "from tbl_message m1\n" +
//            "inner join\n" +
//            "(select u_from_id, max(send_dt) send_dt\n" +
//            "from tbl_message m where u_to_id =:id\n" +
//            "group by u_from_id) m2\n" +
//            "on m1.u_from_id = m2.u_from_id and m1.send_dt = m2.send_dt\n" +
//            "order by send_dt desc",nativeQuery = true)
//    List<Message> findMyMessageList(Long id);

    @Query(value = "select * from tbl_message m\n" +
            "where m.u_from_id = :id or m.u_to_id = :id\n" +
            "order by send_dt desc;",nativeQuery = true)
    List<Message> findMyMessageList(Long id);

    @Query(value = "select *\n" +
            "from tbl_message m\n" +
            "where( m.u_from_id =:fromId and m.u_to_id =:toId)\n" +
            "or( m.u_from_id = :toId and m.u_to_id = :fromId)\n" +
            "order by send_dt desc", nativeQuery = true)
    List<Message> findChatList(Long toId,Long fromId);

    @Query(value = "select s1.sb_id from tbl_study_member s1, tbl_study_member s2\n" +
            "where s1.sb_id = s2.sb_id\n" +
            "and s1.user_id = :id1 and s2.user_id = :id2", nativeQuery = true)
    List<Long> findCommonStudy(Long id1,Long id2);

    @Modifying
    @Transactional
    @Query(value = "delete from tbl_message m where (m.u_to_id = :id1 and m.u_from_id= :id2) or (m.u_to_id = :id2 and m.u_from_id= :id1)", nativeQuery = true)
    int deleteMessageAll(Long id1, Long id2);
}
