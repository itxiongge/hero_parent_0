package com.hero.search.controller;

import com.hero.entity.Result;
import com.hero.entity.StatusCode;
import com.hero.pojo.SkuInfo;
import com.hero.search.service.SearchManagerService;
import com.hero.search.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/search")
public class SearchController {

    @Autowired
    private ElasticsearchOperations elasticsearchOperations;

    @Autowired
    private SearchManagerService searchManagerService;

    @GetMapping("/createIndexAndMapping")
    public Result createIndexAndMapping() {
        //创建索引
        elasticsearchOperations.createIndex(SkuInfo.class);
        //创建映射
        elasticsearchOperations.putMapping(SkuInfo.class);
        return new Result(true, StatusCode.OK, "创建成功");
    }
    @GetMapping("/importAll")
    public Result importAll(){
        searchManagerService.importAll();
        return new Result(true, StatusCode.OK, "导入全部数据成功");
    }
}
