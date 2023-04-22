package com.hero.search.service.impl;

import com.alibaba.fastjson.JSON;
import com.hero.goods.feign.SkuFeign;
import com.hero.goods.pojo.Sku;
import com.hero.pojo.SkuInfo;
import com.hero.search.dao.SearchMapper;
import com.hero.search.service.SearchManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/*
 * @Author yaxiongliu
 **/
@Service
public class SearchManagerServiceImpl implements SearchManagerService {

    @Autowired
    private SkuFeign skuFeign;

    @Autowired
    private SearchMapper searchMapper;

    @Override
    public void importBySpuId(String spuId) {
        //1.根据spuId查询到sku列表（通过feign调用）
        List<Sku> skuList = skuFeign.findBySpuId(spuId);
        //2.将skuList转为json字符串
        String skuListJson = JSON.toJSONString(skuList);
        //3.将json字符串转为List<SkuInfo>
        List<SkuInfo> skuInfoList = JSON.parseArray(skuListJson, SkuInfo.class);
        for (SkuInfo skuInfo : skuInfoList) {
            String specJSON = skuInfo.getSpec();//规格json字符串
            //这里要转换为map类型，map类型ES存储时会将所有的key当成field，那么这些field就可以用来实现精确搜索
            Map specMap = JSON.parseObject(specJSON, Map.class);
            skuInfo.setSpecMap(specMap);
        }
        //4.将sku数据导入到es中
        searchMapper.saveAll(skuInfoList);
    }
}
