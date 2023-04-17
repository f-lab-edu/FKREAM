package com.flab.fkream.salesAccount;


import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@ToString
public class SalesAccount {

    private Long id;
    @NotNull
    private Long userId;
    @NotNull
    private String bankName;
    @NotNull
    private String accountNumber;
    @NotNull
    private String accountHolder;
    private LocalDateTime createdAt;

    public void setCreatedAtToNow() {
        this.createdAt = LocalDateTime.now();
    }
}
