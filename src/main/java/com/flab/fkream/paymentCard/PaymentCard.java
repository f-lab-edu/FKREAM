package com.flab.fkream.paymentCard;

import com.flab.fkream.user.User;
import com.flab.fkream.utils.SHA256Util;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
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
    @NotEmpty
    private String cardCompany;
    @NotEmpty
    @Pattern(regexp = "^\\d{16}$", message = "Invalid credit card number")
    private String cardNumber;
    @NotNull
    @Pattern(regexp = "^(0[1-9]|1[0-2])/[0-9]{2}$", message = "Invalid expiration date")
    private String expiration;
    @NotEmpty
    private String cardPw;
    private LocalDateTime createdAt;

    public void setCreatedAtToNow() {
        this.createdAt = LocalDateTime.now();
    }

    public void encryptPassword() {
        cardPw = SHA256Util.encrypt(cardPw);
    }
}
