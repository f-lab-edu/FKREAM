package com.flab.fkream.elasticsearch;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SearchCondition {

    private Long id;

    private String name;

    private String nickname;

    private int age;

    private Status status;

    private String size;

}
