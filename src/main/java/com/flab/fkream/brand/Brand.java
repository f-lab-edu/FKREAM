package com.flab.fkream.brand;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import javax.validation.constraints.NotNull;
import lombok.*;

@Getter
@ToString
@EqualsAndHashCode(of = "id")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Brand implements Serializable {

    private Long id;
    @NotNull
    private String brandName;
    @JsonProperty("luxury")
    private boolean luxury;
}
