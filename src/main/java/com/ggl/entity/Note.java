package com.ggl.entity;



import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.Data;


@Entity
@Data
@Table(name="t_note",schema = "public")
public class Note {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @Column(name="user_id")
    private String userId;
    @Column(name =  "title")
    private String title;
    @Column(name = "content")
    private String content;
    @Column(name = "create_time")
    private String createTime;
    @Column(name = "update_time")
    private String updateTime;
    @PrePersist
    public void preSet(){
        DateTimeFormatter pattern = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        createTime=pattern.format(LocalDateTime.now());
        updateTime = pattern.format(LocalDateTime.now());
    }
    @PreUpdate
    public void preUpdate(){
        DateTimeFormatter pattern = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        updateTime = pattern.format(LocalDateTime.now());
    }
}
