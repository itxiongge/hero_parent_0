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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Set;

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

    @Autowired
    private SearchService searchService;

    //对搜索入参带有特殊符号进行处理
    public void handlerSearchMap(Map<String,String> searchMap){
        if(null != searchMap){
            Set<Map.Entry<String, String>> entries = searchMap.entrySet();
            for (Map.Entry<String, String> entry : entries) {
                if(entry.getKey().startsWith("spec_")){
                    searchMap.put(entry.getKey(),entry.getValue().replace("+","%2B"));
                }
            }
        }

    }
    /**
     * 全文检索
     * @return
     */
    @GetMapping
    public Map search(@RequestParam Map<String, String> paramMap) throws Exception {
        //特殊符号处理
        handlerSearchMap(paramMap);
        Map resultMap = searchService.search(paramMap);
        return resultMap;
    }
}
