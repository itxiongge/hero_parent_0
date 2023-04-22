package com.hero.goods.feign;

import com.hero.goods.pojo.Sku;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@FeignClient(name = "goods")
@RequestMapping("/sku")
public interface SkuFeign {

    @GetMapping(value = "/findBySpuId/{spuId}" )
    List<Sku> findBySpuId(@PathVariable String spuId);

}
