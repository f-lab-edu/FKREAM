package com.flab.fkream.elasticsearch;

import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Mapping;
import org.springframework.data.elasticsearch.annotations.Setting;

@Getter
@Builder
@AllArgsConstructor
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Document(indexName = "member")
@Mapping(mappingPath = "classpath:/elastic/member-mapping.json")
@Setting(settingPath = "classpath:/elastic/member-setting.json")
public class MemberEntity {

    @Id
    private Long id;

    private String name;

    private String nickname;

    private int age;

    private Status status;

    private List<String> size;

}
