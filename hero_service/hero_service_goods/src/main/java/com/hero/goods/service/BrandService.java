package com.hero.goods.service;

import com.github.pagehelper.Page;
import com.hero.goods.pojo.Brand;

import java.util.List;
import java.util.Map;

public interface BrandService {

    /**
     * 根据商品分类名称查询品牌列表
     * @param categoryName
     * @return
     */
    public List<Map> findListByCategoryName(String categoryName);

    /***
     * 查询所有品牌
     * @return
     */
    List<Brand> findAll();

    /**
     * 根据ID查询
     * @param id
     * @return
     */
    Brand findById(Integer id);

    /***
     * 新增品牌
     * @param brand
     */
    void add(Brand brand);

    /***
     * 修改品牌数据
     * @param brand
     */
    void update(Brand brand);

    /***
     * 删除品牌
     * @param id
     */
    void delete(Integer id);

    /***
     * 多条件搜索品牌方法
     * @param searchMap
     * @return
     */
    List<Brand> findList(Map<String, Object> searchMap);
    /***
     * 分页查询
     * @param page
     * @param size
     * @return
     */
    Page<Brand> findPage(int page, int size);
    /***
     * 多条件分页查询
     * @param searchMap
     * @param page
     * @param size
     * @return
     */
    Page<Brand> findPage(Map<String, Object> searchMap, int page, int size);
}
