package com.flab.fkream.paymentCard;

import com.flab.fkream.user.User;
import com.flab.fkream.utils.SHA256Util;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Builder
@EqualsAndHashCode(of = "id")
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PaymentCard {

    private Long id;
    @NotNull
    private Long userId;
    @NotNull
    private String cardCompany;
    @NotNull
    private String cardNumber;
    @NotNull
    private String expiration;
    @NotNull
    private String cardPw;
    private LocalDateTime createdAt;

    public void setCreatedAtToNow() {
        this.createdAt = LocalDateTime.now();
    }

    public void encryptPassword() {
        cardPw=SHA256Util.encrypt(cardPw);
    }
}
