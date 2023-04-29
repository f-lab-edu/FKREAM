package com.flab.fkream.search;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.cache.annotation.Cacheable;

@Mapper
public interface SearchMapper {


    @Cacheable(cacheNames = "Search", key = "#root.methodName")
    List<SearchItemDto> searchAll();

    @Cacheable(cacheNames = "Search", key = "#p0")
    List<SearchItemDto> searchByCriteria(SearchCriteria searchCriteria);

    @Cacheable(cacheNames = "SearchCount", key = "#root.methodName")
    int findAllCount();

    @Cacheable(cacheNames = "SearchCount", key = "#p0")
    int findCountByCriteria(SearchCriteria searchCriteria);

    @Cacheable(cacheNames = "AutoComplete", key = "#p0")
    List<AutoCompletedItemDto> autoComplete(@Param("results") List<String> results);
}
