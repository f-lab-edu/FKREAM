package com.flab.fkream.search;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.cache.annotation.Cacheable;

@Mapper
public interface SearchMapper {


    List<SearchItemDto> searchAll();

    List<SearchItemDto> searchByCriteria(SearchCriteria searchCriteria);

    int findAllCount();

    int findCountByCriteria(SearchCriteria searchCriteria);

//    List<AutoCompletedItemDto> autoComplete(@Param("results") List<String> results);

    List<AutoCompletedItemDto> autoComplete(@Param("results") String results);

}
