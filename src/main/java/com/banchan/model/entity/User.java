//package com.banchan.model.entity;
//
//
//import lombok.Data;
//
//import javax.persistence.Column;
//import javax.persistence.Entity;
//import javax.persistence.FetchType;
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
//import javax.persistence.Id;
//import javax.persistence.JoinColumn;
//import javax.persistence.OneToMany;
//import javax.persistence.OneToOne;
//import javax.persistence.OrderBy;
//import java.time.LocalDateTime;
//
//@Data
//@Entity(name = "users")
//public class User {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @Column(name = "device_key", nullable = false)
//    private String deviceKey;
//
//    @Column(name = "username_id")
//    private Long usernameId;
//
//    @Column
//    private Integer age;
//
//    @Column
//    private String color;
//
//    @Column(name = "use_yn")
//    private String useYn;
//
//    @Column
//    private String sex;
//
//    @Column(name = "updated_at")
//    private LocalDateTime updatedAt;
//
//    @Column(name = "created_at")
//    private LocalDateTime createdAt;
//
////    @OneToOne(fetch = FetchType.LAZY)
////    @JoinColumn(referencedColumnName = "username_id")
////    private Username username;
////
////    @OneToMany(fetch = FetchType.LAZY)
////    @JoinColumn(name = "user_id")
////    private UserAuthInfo userAuthInfo;
//}
