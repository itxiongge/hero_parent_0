package com.hero.search.service.impl;

import com.alibaba.fastjson.JSON;
import com.hero.pojo.SkuInfo;
import com.hero.search.service.SearchService;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.Operator;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.ParsedStringTerms;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.*;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

/*
 * @Author yaxiongliu
 **/
@Service
public class SearchServiceImpl implements SearchService {

    @Autowired
    private ElasticsearchOperations elasticsearchOperations;

    //设置每页查询条数据
    public final static Integer PAGE_SIZE = 20;

    @Override
    public Map search(Map<String, String> searchMap) throws Exception {
        Map<String, Object> resultMap = new HashMap<>();

        //有条件才查询ES
        if (null == searchMap) {
            return new HashMap();
        }

        String pageNum = searchMap.get("pageNum");
        if (null == pageNum) {
            pageNum = "1";
        }

        //组合条件对象
        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();
        //0:关键词
        if (StringUtils.hasText(searchMap.get("keywords"))) {
            boolQuery.must(QueryBuilders.matchQuery("name", searchMap.get("keywords")).operator(Operator.AND));
        }
        //1:条件 品牌
        if (StringUtils.hasText(searchMap.get("brand"))) {
            boolQuery.filter(QueryBuilders.termQuery("brandName", searchMap.get("brand")));
        }

        //2:条件 规格
        for (String key : searchMap.keySet()) {
            if (key.startsWith("spec_")) {
                String value = searchMap.get(key).replace("%2B", "+");
                boolQuery.filter(QueryBuilders.termQuery("specMap." + key.substring(5) + ".keyword",value));
            }
        }
        //3:条件 价格
        if (StringUtils.hasText(searchMap.get("price"))) {
            String[] p = searchMap.get("price").split("-");
            boolQuery.filter(QueryBuilders.rangeQuery("price").gte(p[0]));
            if (p.length == 2) {
                boolQuery.filter(QueryBuilders.rangeQuery("price").lte(p[1]));
            }
        }

        //4. 原生搜索实现类
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
        queryBuilder.withQuery(boolQuery);
        //5:高亮
        HighlightBuilder.Field field = new HighlightBuilder
                .Field("name")
                .preTags("<span style='color:red'>")
                .postTags("</span>");
        queryBuilder.withHighlightFields(field);

        //6. 品牌聚合(分组)查询
        String skuBrand = "skuBrand";
        queryBuilder.addAggregation(AggregationBuilders.terms(skuBrand).field("brandName"));
        //7. 规格聚合(分组)查询
        String skuSpec = "skuSpec";
        queryBuilder.addAggregation(AggregationBuilders.terms(skuSpec).field("spec.keyword"));
        //8: 排序
        if (StringUtils.hasText(searchMap.get("sortField"))) {
            if ("ASC".equals(searchMap.get("sortRule"))) {
                queryBuilder.withSort(SortBuilders.fieldSort(searchMap.get("sortField")).order(SortOrder.ASC));
            } else {

                queryBuilder.withSort(SortBuilders.fieldSort(searchMap.get("sortField")).order(SortOrder.DESC));
            }

        }

        //9: 分页
        queryBuilder.withPageable(PageRequest.of(Integer.parseInt(pageNum) - 1, PAGE_SIZE));

        //10: 执行查询, 返回结果对象
        SearchHits<SkuInfo> searchHits = elasticsearchOperations.search(queryBuilder.build(), SkuInfo.class);
        //11. 总条数
        long totalHits = searchHits.getTotalHits();
        resultMap.put("total", totalHits);
        int totalPages = (int) Math.ceil((double) totalHits / PAGE_SIZE);
        //12. 总页数
        resultMap.put("totalPages", totalPages);
        //13. 查询结果集合
        List<SkuInfo> skuInfos = new ArrayList<>();
        for (SearchHit<SkuInfo> searchHit : searchHits) {
            SkuInfo skuInfo = searchHit.getContent();
            List<String> name = searchHit.getHighlightField("name");
            skuInfo.setName(name.toString());
            skuInfos.add(skuInfo);
        }
        resultMap.put("rows", skuInfos);

        //14. 获取品牌聚合结果
        ParsedStringTerms brandTerms = searchHits.getAggregations().get(skuBrand);
        List<String> brandList = brandTerms.getBuckets().stream().map(bucket -> bucket.getKeyAsString()).collect(Collectors.toList());
        resultMap.put("brandList", brandList);

        //15. 获取规格聚合结果
        ParsedStringTerms specTerms = searchHits.getAggregations().get(skuSpec);
        List<String> specList = specTerms.getBuckets().stream().map(bucket -> bucket.getKeyAsString()).collect(Collectors.toList());
        resultMap.put("specList", specList(specList));

        //16. 返回当前页
        resultMap.put("pageNum", pageNum);
        return resultMap;
    }
    //处理规格集合
    public Map<String, Set<String>> specList(List<String> specList) {
        Map<String, Set<String>> specMap = new HashMap<>();
        if (null != specList && specList.size() > 0) {
            for (String spec : specList) {
                Map<String, String> map = JSON.parseObject(spec, Map.class);
                Set<Map.Entry<String, String>> entries = map.entrySet();
                for (Map.Entry<String, String> entry : entries) {
                    String key = entry.getKey();
                    String value = entry.getValue();

                    Set<String> specValues = specMap.get(key);
                    if (null == specValues) {
                        specValues = new HashSet<>();
                    }
                    specValues.add(value);
                    specMap.put(key, specValues);
                }
            }
        }
        return specMap;
    }
}
