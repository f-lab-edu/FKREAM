package com.flab.fkream.search;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SearchMapper {

    List<SearchItemDto> searchAll();

    List<SearchItemDto> searchByCriteria(SearchCriteria searchCriteria);

    int findAllCount();

    int findCountByCriteria(SearchCriteria searchCriteria);

    List<AutoCompletedItemDto> autoComplete(List<String> result);


}
