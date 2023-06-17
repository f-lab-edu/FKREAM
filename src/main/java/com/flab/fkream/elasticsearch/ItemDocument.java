package com.flab.fkream.elasticsearch;

import static org.springframework.data.elasticsearch.annotations.DateFormat.date_hour_minute_second_millis;
import static org.springframework.data.elasticsearch.annotations.DateFormat.epoch_millis;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.flab.fkream.brand.Brand;
import com.flab.fkream.item.ItemGender;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import javax.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.Mapping;
import org.springframework.data.elasticsearch.annotations.Setting;
import org.springframework.format.annotation.DateTimeFormat;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Document(indexName = "member")
@Mapping(mappingPath = "elastic/item-mapping.json")
@Setting(settingPath = "elastic/item-setting.json")
public class ItemDocument {

    @Id
    private Long id;

    private Long dealCount;

    @NotNull
    private String itemName;

    @Field(type = FieldType.Auto)
    private List<String> size;

    @NotNull
    private String modelNumber;
    @NotNull
    private Long categoryId;
    @NotNull
    private Long detailedCategoryId;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
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

    private boolean oneSize;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @Field(type = FieldType.Date, format = {date_hour_minute_second_millis, epoch_millis})
    private LocalDateTime createdAt;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @Field(type = FieldType.Date, format = {date_hour_minute_second_millis, epoch_millis})
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
