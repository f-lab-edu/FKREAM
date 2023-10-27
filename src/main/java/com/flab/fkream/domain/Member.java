package com.flab.fkream.domain;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Member extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50, unique = true)
    private String email;

    @Column(length = 128)
    private String password;

    @Column(length = 20)
    private String username;

    @Column(length = 20)
    private String phoneNumber;

    @Column(length = 20)
    private String birthday;

    @Column(length = 20)
    private String gender;

    private String refreshToken;

    private boolean deleted;

}
