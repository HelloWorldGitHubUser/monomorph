package io.gulimall.service.search;

import io.gulimall.vo.search.SearchParam;
import io.gulimall.vo.search.SearchResult;

public interface SearchService {
    SearchResult getSearchResult(SearchParam searchParam);
}
