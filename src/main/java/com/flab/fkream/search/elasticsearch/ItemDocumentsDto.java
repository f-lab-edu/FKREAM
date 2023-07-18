package com.flab.fkream.search.elasticsearch;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ItemDocumentsDto {

    private List<ItemDocument> itemDocumentList;
    private Object[] sortValues;

}
