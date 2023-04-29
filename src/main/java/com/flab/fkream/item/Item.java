package com.flab.fkream.item;

import com.flab.fkream.itemCategory.ItemCategory;
import java.time.LocalDate;
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
    private Long categoryId;
    @NotNull
    private Long detailedCategoryId;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate releaseDate;

    @NotNull
    private String representativeColor;
    @NotNull
    private int releasedPrice;

    @NotNull
    private ItemGender gender;
    @NotNull
    private Brand brand;

    private Long managerId;

    private int latestPrice;

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
