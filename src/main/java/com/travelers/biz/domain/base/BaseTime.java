package com.travelers.biz.domain.base;

import lombok.Getter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class BaseTime {

    @Column(name = "created_at")
    private String createdAt;

    @Column(name = "modified_at")
    private String modifiedAt;

    @PrePersist
    public void prePersist(){
        this.createdAt = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy.MM.dd"));
        this.modifiedAt = this.createdAt;
        init();
    }

    @PreUpdate
    public void preUpdate(){
        this.modifiedAt = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy.MM.dd"));
    }

    protected void init(){

    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
}
