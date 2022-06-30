package com.ssafy.withssafy.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "tbl_recruit")
@Getter
@NoArgsConstructor
public class Recruit {
    @Id @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(length = 50)
    private String company;

    @Column(length = 50)
    private String career;

    @Column(length = 50)
    private String education;

    @Column(length = 50)
    private String job;

    @Column(length = 50)
    private String employType;

    @Column(length = 50)
    private String salary;

    @Column(length = 50)
    private String location;

    @Column(length = 500)
    private String taskDescription;

    @Column(length = 500)
    private String preferenceDescription;

    @Column(length = 500)
    private String welfare;

    @Column(length = 100)
    private String workingHours;

    @Column(length = 10)
    private String startDate;

    @Column(length = 10)
    private String endDate;

    @Column(name = "photo_path")
    private String photoPath;


    @Builder
    public Recruit(Long id, User user, String company, String career, String education, String job, String employType, String salary, String location, String taskDescription, String preferenceDescription, String welfare, String workingHours, String startDate, String endDate, String photoPath) {
        this.id = id;
        this.user = user;
        this.company = company;
        this.career = career;
        this.education = education;
        this.job = job;
        this.employType = employType;
        this.salary = salary;
        this.location = location;
        this.taskDescription = taskDescription;
        this.preferenceDescription = preferenceDescription;
        this.welfare = welfare;
        this.workingHours = workingHours;
        this.startDate = startDate;
        this.endDate = endDate;
        this.photoPath = photoPath;
    }
}
