package com.flab.fkream.domain;


import com.flab.fkream.dto.MemberDto;
import com.flab.fkream.dto.RegisteredMemberDto;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50, unique = true)
    private String username;

    @Column(length = 128)
    private String password;

    @Column(length = 20)
    private String name;

    @Column(length = 20)
    private String phoneNumber;

    @Column(length = 20)
    private String birthday;

    @Column(length = 20)
    private String gender;

    @Enumerated(EnumType.STRING)
    private Authority authority;

    public static Member of(RegisteredMemberDto registeredMemberDto) {
        return Member.builder()
            .username(registeredMemberDto.username())
            .password(registeredMemberDto.password())
            .name(registeredMemberDto.name())
            .phoneNumber(registeredMemberDto.phoneNumber())
            .birthday(registeredMemberDto.birthday())
            .gender(registeredMemberDto.gender())
            .build();
    }

    public MemberDto toMemberDto() {
        return new MemberDto(this.username, this.name, this.phoneNumber, this.birthday, this.gender);
    }
}
