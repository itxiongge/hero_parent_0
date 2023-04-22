package com.hero.search.dao;

import com.hero.pojo.SkuInfo;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface SearchMapper extends ElasticsearchRepository<SkuInfo,Long> {}
