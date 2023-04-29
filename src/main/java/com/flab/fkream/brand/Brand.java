package com.flab.fkream.brand;

import java.io.Serializable;
import javax.validation.constraints.NotNull;
import lombok.*;

@Getter
@ToString
@EqualsAndHashCode(of = "id")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Brand implements Serializable{

    private Long id;
    @NotNull
    private String brandName;
    private boolean isLuxury;
}
