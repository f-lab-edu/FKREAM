package com.flab.fkream.deal;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.flab.fkream.item.Item;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Builder
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Deal {

    private Long id;
    @NotNull
    @Setter
    private Item item;
    private DealType dealType;
    @NotNull
    private Long userId;
    @NotNull
    private int price;
    @NotNull
    private String size;
    @NotNull
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate period;
    @AssertTrue
    private boolean utilizationPolicy;
    @AssertTrue
    private boolean salesCondition;
    private DealStatus dealStatus;
    @Setter
    private Long otherId;
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDateTime createdAt;
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate tradingDay;

    public void setDealStatus(DealStatus dealStatus) {
        this.dealStatus = dealStatus;
    }

    public void setCreatedAtToNow() {
        createdAt = LocalDateTime.now();
    }

    public void setTradingDayToNow() {
        tradingDay = LocalDate.now();
    }
}
