package com.flab.fkream.elasticsearch;

import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Document(indexName = "ranking")
public class SearchDocument {

    @Id
    private Long id;

    @Field(type = FieldType.Keyword)
    private String searchWord;

    @Field(type = FieldType.Integer)
    private Integer searchCount;

    @Field(type = FieldType.Date, format = {DateFormat.date_hour_minute_second_millis,
        DateFormat.epoch_millis})
    private LocalDateTime createdAt;
}
