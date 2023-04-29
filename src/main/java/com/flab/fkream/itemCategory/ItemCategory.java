package com.flab.fkream.itemCategory;

import java.io.Serializable;
import java.util.List;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Builder
@Getter
@EqualsAndHashCode
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ItemCategory implements Serializable {

    private Long id;
    @NotNull
    private String name;
    private Long parentId;
}
