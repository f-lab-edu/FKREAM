package com.flab.fkream.cache;


import static org.assertj.core.api.Assertions.*;

import com.flab.fkream.search.SearchCriteria;
import com.flab.fkream.search.SearchItemDto;
import com.flab.fkream.search.SearchMapper;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
@ActiveProfiles({"test"})
public class SearchTest {

    @Autowired
    CacheManager cacheManager;

    @SpyBean
    SearchMapper searchMapper;

    @Test
    void searchAll() {
        searchMapper.searchAll();
        Cache searchAll = cacheManager.getCache("Search");
        assertThat(searchAll.get("searchAll")).isNotNull();
    }

    @Test
    void searchByCriteria() {
        SearchCriteria searchCriteria = SearchCriteria.builder().context("an").build();
        searchMapper.searchByCriteria(searchCriteria);
        Cache search = cacheManager.getCache("Search");
        assertThat(search.get(searchCriteria)).isNotNull();
        assertThat(search.get(SearchCriteria.builder().context("an").build())).isNotNull();
        assertThat(search.get(SearchCriteria.builder().context("ab").build())).isNull();
    }

    @Test
    void findAllCount() {
        searchMapper.findAllCount();
        Cache searchAll = cacheManager.getCache("SearchCount");
        assertThat(searchAll.get("findAllCount")).isNotNull();

    }

    @Test
    void findCountByCriteria() {
        SearchCriteria searchCriteria = SearchCriteria.builder().context("an").build();
        searchMapper.findCountByCriteria(searchCriteria);
        Cache search = cacheManager.getCache("SearchCount");
        assertThat(search.get(searchCriteria)).isNotNull();
        assertThat(search.get(SearchCriteria.builder().context("an").build())).isNotNull();
        assertThat(search.get(SearchCriteria.builder().context("ab").build())).isNull();
    }

    @Test
    void autoComplete() {
        List<String> result = List.of("a", "ab", "abc", "abcd");
        searchMapper.autoComplete(result);
        Cache autoComplete = cacheManager.getCache("AutoComplete");
        assertThat(autoComplete.get(result)).isNotNull();
        assertThat(autoComplete.get(List.of("a", "ab", "abc", "abcd"))).isNotNull();
        assertThat(autoComplete.get(List.of("a","ab","abc"))).isNull();
    }

}
