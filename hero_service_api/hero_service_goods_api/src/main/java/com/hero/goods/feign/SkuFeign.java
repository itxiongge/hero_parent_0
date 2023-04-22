package com.hero.goods.feign;

import com.hero.entity.Result;
import com.hero.goods.pojo.Sku;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@FeignClient(name = "goods")
@RequestMapping("/sku")
public interface SkuFeign {

    @GetMapping(value = "/findBySpuId/{spuId}" )
    List<Sku> findBySpuId(@PathVariable String spuId);

    /**
     * 查看所有SKU，用于全量同步到ES
     * @return
     */
    @GetMapping(value = "/search" )
    Result findList(@RequestParam Map searchMap);
}
