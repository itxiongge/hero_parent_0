package com.hero.goods.dao;

import com.hero.goods.pojo.Brand;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

public interface BrandMapper extends Mapper<Brand> {
    /**
     * 根据分类名称查询品牌列表
     * @param categoryName
     * @return
     */
    @Select("SELECT name,image FROM tb_brand WHERE id  IN (SELECT brand_id FROM tb_category_brand WHERE  category_id IN (SELECT id FROM tb_category WHERE NAME=#{name}) )order by seq")
    List<Map> findListByCategoryName(@Param("name") String categoryName);
}
