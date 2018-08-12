//package com.banchan.model.entity;
//
//import lombok.Data;
//
//import javax.persistence.Column;
//import javax.persistence.Entity;
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
//import javax.persistence.Id;
//import java.time.LocalDateTime;
//
//@Data
//@Entity(name = "user_auth_infos")
//public class UserAuthInfo {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @Column(name = "user_id")
//    private Long userId;
//
//    @Column(name = "token_key")
//    private String tokenKey;
//
//    @Column(name = "use_yn")
//    private String useYn;
//
//    @Column(name = "updated_at")
//    private LocalDateTime updatedAt;
//
//    @Column(name = "created_at")
//    private LocalDateTime createdAt;
//}
