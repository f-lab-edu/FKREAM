package com.flab.fkream.search;

import java.io.IOException;

public interface SearchService<T> {

    T search(SearchCriteria searchCriteria) throws IOException;

    int findCount(SearchCriteria searchCriteria) throws IOException;
}
