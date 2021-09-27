package com.aps.quality.entity;

import lombok.Data;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "user_info")
@EntityListeners(AuditingEntityListener.class)
public class UserConcise implements Serializable {
    @Id
    @GeneratedValue
    @Column(name = "user_id", nullable = false)
    private Integer userId;

    @Column(name = "user_code", nullable = false, length = 60)
    private String userCode;

    @Column(name = "user_name", nullable = true, length = 60)
    private String userName;
}