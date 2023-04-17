package com.flab.fkream.search;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SearchMapper {

    List<SearchItemDto> search(String context);

    int findCount(String context);

    List<AutoCompletedItemDto> autoComplete(List<String> result);

    List<SearchItemDto> searchByCategory(String context, Long[] categoryId);

}
