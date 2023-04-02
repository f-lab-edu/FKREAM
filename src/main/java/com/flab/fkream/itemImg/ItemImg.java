package com.flab.fkream.itemImg;

import java.time.LocalDateTime;

import com.flab.fkream.item.Item;
import javax.validation.constraints.NotNull;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

@Getter
@ToString
@EqualsAndHashCode(of = "id")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ItemImg {

    private Long id;
    @NotNull
    private Item item;
    @NotNull
    private String imgName;
    @NotNull
    private String imgUrl;
    @NotNull
    private String originName;
    private boolean isRepresentativeImg;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime createdAt;

    public void setCreatedAt() {
        createdAt = LocalDateTime.now();
    }
}
