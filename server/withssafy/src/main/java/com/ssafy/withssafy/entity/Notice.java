package com.ssafy.withssafy.entity;

import com.ssafy.withssafy.dto.notice.NoticeModifyReqDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "tbl_notice")
@Getter
@NoArgsConstructor
public class Notice {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "type_id")
    private NoticeType type;

    @Column
    private String title;

    @Column
    private String content;

    @Column
    private String photoPath;

    @Column
    private LocalDateTime writeDt;

    @Column
    private String filePath;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "classroom_id")
    private ClassRoom classRoom;

    public Notice(Long id, User user, NoticeType type, String title, String content, String photoPath, LocalDateTime writeDt, String filePath, ClassRoom classRoom) {
        this.id = id;
        this.user = user;
        this.type = type;
        this.title = title;
        this.content = content;
        this.photoPath = photoPath;
        this.writeDt = writeDt;
        this.filePath = filePath;
        this.classRoom = classRoom;
    }

    public void updateNotice(NoticeModifyReqDto noticeReqDto){
        title = noticeReqDto.getTitle();
        content = noticeReqDto.getContent();
        photoPath = noticeReqDto.getPhotoPath();
        filePath = noticeReqDto.getFilePath();
    }
}
