package com.hero.search.service;

public interface SearchManagerService {
    /**
     * 导入商品信息
     * @param spuId
     */
    void importBySpuId(String spuId);

    /**
     * 全量同步商品信息
     */
    void importAll();
}
