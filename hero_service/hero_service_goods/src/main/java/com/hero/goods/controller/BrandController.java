package com.hero.goods.controller;

import com.github.pagehelper.Page;
import com.hero.entity.PageResult;
import com.hero.entity.Result;
import com.hero.entity.StatusCode;
import com.hero.goods.pojo.Brand;
import com.hero.goods.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/brand")
public class BrandController {

    @Autowired
    private BrandService brandService;

    /**
     * 根据分类名称查询品牌列表
     * @param category
     * @return
     */
    @GetMapping("/category/{category}")
    public Result  findListByCategoryName(@PathVariable String category){
        System.out.println(category);
        List<Map> brandList = brandService.findListByCategoryName(category);
        return new Result(true,StatusCode.OK,"查询成功",brandList);
    }

    @GetMapping
    public Result findAll(){
        List<Brand> brandList = brandService.findAll();
        return new Result(true, StatusCode.OK,"查询成功",brandList) ;
    }

    /***
     * 根据ID查询品牌数据
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Result findById(@PathVariable Integer id){
        int i=id/0;
        Brand brand = brandService.findById(id);
        return new Result(true,StatusCode.OK,"查询成功",brand);
    }

    /***
     * 新增品牌数据
     * @param brand
     * @return
     */
    @PostMapping
    public Result add(@RequestBody Brand brand){
        brandService.add(brand);
        return new Result(true,StatusCode.OK,"添加成功");
    }

    /***
     * 修改品牌数据
     * @param brand
     * @param id
     * @return
     */
    @PutMapping(value="/{id}")
    public Result update(@RequestBody Brand brand,@PathVariable Integer id){
        brand.setId(id);
        brandService.update(brand);
        return new Result(true,StatusCode.OK,"修改成功");
    }

    /***
     * 根据ID删除品牌数据
     * @param id
     * @return
     */
    @DeleteMapping(value = "/{id}" )
    public Result delete(@PathVariable Integer id){
        brandService.delete(id);
        return new Result(true,StatusCode.OK,"删除成功");
    }

    /***
     * 多条件搜索品牌数据
     * @param searchMap
     * @return
     */
    @GetMapping(value = "/search" )
    public Result findList(@RequestParam Map searchMap){
        List<Brand> list = brandService.findList(searchMap);
        return new Result(true,StatusCode.OK,"查询成功",list);
    }

    /***
     * 分页搜索实现
     * @param searchMap
     * @param page
     * @param size
     * @return
     */
    @GetMapping(value = "/search/{page}/{size}" )
    public Result findPage(@RequestParam Map searchMap, @PathVariable  int page, @PathVariable  int size){
        Page<Brand> pageList = brandService.findPage(searchMap, page, size);
        PageResult pageResult=new PageResult(pageList.getTotal(),pageList.getResult());
        return new Result(true,StatusCode.OK,"查询成功",pageResult);
    }
}
