package com.hero.goods.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.hero.goods.dao.BrandMapper;
import com.hero.goods.pojo.Brand;
import com.hero.goods.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;
import java.util.Map;

@Service
public class BrandServiceImpl implements BrandService {

    @Autowired
    private BrandMapper brandMapper;

    @Override
    public List<Brand> findAll() {
        return brandMapper.selectAll();
    }

    /**
     * 根据ID查询
     * @param id
     * @return
     */
    @Override
    public Brand findById(Integer id){
        return  brandMapper.selectByPrimaryKey(id);
    }

    /**
     * 增加
     * @param brand
     */
    @Override
    public void add(Brand brand){
        brandMapper.insertSelective(brand);
    }

    /**
     * 修改
     * @param brand
     */
    @Override
    public void update(Brand brand){
        brandMapper.updateByPrimaryKeySelective(brand);
    }

    /**
     * 删除
     * @param id
     */
    @Override
    public void delete(Integer id){
        brandMapper.deleteByPrimaryKey(id);
    }

    /**
     * 条件查询
     * @param searchMap
     * @return
     */
    @Override
    public List<Brand> findList(Map<String, Object> searchMap){
        Example example = createExample(searchMap);
        return brandMapper.selectByExample(example);
    }

    /**
     * 构建查询对象
     * @param searchMap
     * @return
     */
    private Example createExample(Map<String, Object> searchMap){
        Example example=new Example(Brand.class);
        Example.Criteria criteria = example.createCriteria();
        if(searchMap!=null){
            // 品牌名称
            if(searchMap.get("name")!=null && !"".equals(searchMap.get("name"))){
                criteria.andLike("name","%"+searchMap.get("name")+"%");
            }
            // 品牌的首字母
            if(searchMap.get("letter")!=null && !"".equals(searchMap.get("letter"))){
                criteria.andEqualTo("letter",searchMap.get("letter"));
            }
        }
        return example;
    }

    /**
     * 分页查询
     * @param page
     * @param size
     * @return
     */
    @Override
    public Page<Brand> findPage(int page, int size){
        PageHelper.startPage(page,size);
        return (Page<Brand>)brandMapper.selectAll();
    }

    /**
     * 条件+分页查询
     * @param searchMap 查询条件
     * @param page 页码
     * @param size 页大小
     * @return 分页结果
     */
    @Override
    public Page<Brand> findPage(Map<String,Object> searchMap, int page, int size){
        PageHelper.startPage(page,size);
        Example example = createExample(searchMap);
        return (Page<Brand>)brandMapper.selectByExample(example);
    }
}
