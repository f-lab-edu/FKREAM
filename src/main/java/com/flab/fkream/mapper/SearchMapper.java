package com.flab.fkream.mapper;

import com.flab.fkream.AutoComplete.AutoCompletedItemDto;
import com.flab.fkream.search.SearchCriteria;
import com.flab.fkream.search.dbSearch.SearchItemDto;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface SearchMapper {


    List<SearchItemDto> searchAll();

    List<SearchItemDto> searchByCriteria(SearchCriteria searchCriteria);

    int findAllCount();

    int findCountByCriteria(SearchCriteria searchCriteria);

//    List<AutoCompletedItemDto> autoComplete(@Param("results") List<String> results);

    List<AutoCompletedItemDto> autoComplete(@Param("results") String results);

}
