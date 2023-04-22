package com.hero.search.service;

import java.util.Map;

public interface SearchService {
    /**
     * 全文检索
     * @param paramMap  查询参数
     * @return
     */
    Map search(Map<String, String> paramMap) throws Exception;
}
