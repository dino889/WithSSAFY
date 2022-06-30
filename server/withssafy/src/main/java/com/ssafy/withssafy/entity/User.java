package com.ssafy.withssafy.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
@Table(name = "tbl_user")
@Getter
@NoArgsConstructor
public class User {
    @Id @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "classroom_id")
    private ClassRoom classRoom;

    @Size(max = 25)
    @Column(length = 25, unique = true)
    private String userId;

    @Column()
    private String studentId;

    @Size(max = 255)
    private String password;

    @Column
    private String name;

    @Column
    private int state;

    @Size(max = 255)
    @Column(name = "device_token")
    private String deviceToken;

    @Builder
    public User(Long id, ClassRoom classRoom, String userId, String password, int state,
                String deviceToken, String studentId, String name){
        this.id = id;
        this.userId = userId;
        this.studentId = studentId;
        this.password = password;
        this.name = name;
        this.state = state;
        this.deviceToken = deviceToken;
        this.classRoom = classRoom;
    }

    public void encodePassword(PasswordEncoder passwordEncoder) {
        password = passwordEncoder.encode(password);
    }

    public void updateDeviceToken(String token) {
        this.deviceToken = token;
    }

    public void updateState(int state) {
        this.state = state;
    }
}
