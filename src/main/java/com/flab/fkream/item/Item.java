package com.flab.fkream.item;

import java.time.LocalDateTime;

import com.flab.fkream.brand.Brand;
import javax.validation.constraints.NotNull;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

@Getter
@ToString
@EqualsAndHashCode(of = "id")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Item {

    private Long id;
    @NotNull
    private String itemName;
    @NotNull
    private String modelNumber;
    @NotNull
    private String category1;
    @NotNull
    private String category2;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDateTime releaseDate;

    @NotNull
    private String representativeColor;
    @NotNull
    private int releasedPrice;
    private int latestPrice;
    @NotNull
    private Brand brand;
    private Long managerId;

    private boolean isOneSize;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime createdAt;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime modifiedAt;

    public void setCreatedAtToNow() {
        createdAt = LocalDateTime.now();
    }

    public void setModifiedAtToNow() {
        modifiedAt = LocalDateTime.now();
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }
}
