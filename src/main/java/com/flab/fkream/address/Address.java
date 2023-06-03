package com.flab.fkream.address;

import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Builder
@EqualsAndHashCode(of = "id")
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Address {

    @Setter
    private Long id;
    @NotNull
    private Long userId;
    @NotNull
    private String name;
    @NotNull
    private String phoneNumber;
    @NotNull
    private String zipcode;
    @NotNull
    private String detail1;
    @NotNull
    private String detail2;
    private boolean defaultAddress;
    private LocalDateTime createdAt;


}
