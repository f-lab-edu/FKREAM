package com.flab.fkream.item;

import java.time.LocalDateTime;

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
    @NonNull
    private String itemName;
    @NonNull
    private String modelNumber;
    @NonNull
    private String category1;
    @NonNull
    private String category2;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDateTime releaseDate;
    @NonNull
    private String representativeColor;
    @NonNull
    private int releasedPrice;
    private int latestPrice;
    private Long brandId;
    private Long managerId;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime createdAt;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime modifiedAt;


    public void setCreatedAt() {
        createdAt = LocalDateTime.now();
    }

    public void setModifiedAt() {
        modifiedAt = LocalDateTime.now();
    }


}


