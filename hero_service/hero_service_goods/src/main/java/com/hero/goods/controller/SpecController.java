package com.hero.goods.controller;
import com.hero.entity.PageResult;
import com.hero.entity.Result;
import com.hero.entity.StatusCode;
import com.hero.goods.service.SpecService;
import com.hero.goods.pojo.Spec;
import com.github.pagehelper.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;
@RestController
@CrossOrigin
@RequestMapping("/spec")
public class SpecController {


    @Autowired
    private SpecService specService;

    /**
     * 查询全部数据
     * @return
     */
    @GetMapping
    public Result findAll(){
        List<Spec> specList = specService.findAll();
        return new Result(true, StatusCode.OK,"查询成功",specList) ;
    }

    /***
     * 根据ID查询数据
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Result findById(@PathVariable Integer id){
        Spec spec = specService.findById(id);
        return new Result(true,StatusCode.OK,"查询成功",spec);
    }


    /***
     * 新增数据
     * @param spec
     * @return
     */
    @PostMapping
    public Result add(@RequestBody Spec spec){
        specService.add(spec);
        return new Result(true,StatusCode.OK,"添加成功");
    }


    /***
     * 修改数据
     * @param spec
     * @param id
     * @return
     */
    @PutMapping(value="/{id}")
    public Result update(@RequestBody Spec spec,@PathVariable Integer id){
        spec.setId(id);
        specService.update(spec);
        return new Result(true,StatusCode.OK,"修改成功");
    }


    /***
     * 根据ID删除品牌数据
     * @param id
     * @return
     */
    @DeleteMapping(value = "/{id}" )
    public Result delete(@PathVariable Integer id){
        specService.delete(id);
        return new Result(true,StatusCode.OK,"删除成功");
    }

    /***
     * 多条件搜索品牌数据
     * @param searchMap
     * @return
     */
    @GetMapping(value = "/search" )
    public Result findList(@RequestParam Map searchMap){
        List<Spec> list = specService.findList(searchMap);
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
        Page<Spec> pageList = specService.findPage(searchMap, page, size);
        PageResult pageResult=new PageResult(pageList.getTotal(),pageList.getResult());
        return new Result(true,StatusCode.OK,"查询成功",pageResult);
    }


}
