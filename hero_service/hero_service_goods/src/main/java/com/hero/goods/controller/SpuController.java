package com.hero.goods.controller;
import com.hero.entity.PageResult;
import com.hero.entity.Result;
import com.hero.entity.StatusCode;
import com.hero.goods.pojo.Goods;
import com.hero.goods.service.SpuService;
import com.hero.goods.pojo.Spu;
import com.github.pagehelper.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;
@RestController
@CrossOrigin
@RequestMapping("/spu")
public class SpuController {


    @Autowired
    private SpuService spuService;
    /**
     * 物理删除
     * @param id
     * @return
     */
    @DeleteMapping("/realDelete/{id}")
    public Result realDelete(@PathVariable String id){
        spuService.realDelete(id);
        return new Result();
    }
    /**
     * 恢复数据
     * @param id
     * @return
     */
    @PutMapping("/restore/{id}")
    public Result restore(@PathVariable String id){
        spuService.restore(id);
        return new Result();
    }
    /**
     * 上架
     * @param id
     * @return
     */
    @PutMapping("/put/{id}")
    public Result put(@PathVariable String id){
        spuService.put(id);
        return new Result();
    }
    /**
     * 下架
     * @param id
     * @return
     */
    @PutMapping("/pull/{id}")
    public Result pull(@PathVariable String id){
        spuService.pull(id);
        return new Result();
    }
    /**
     * 审核
     * @param id
     * @return
     */
    @PutMapping("/audit/{id}")
    public Result audit(@PathVariable String id){
        spuService.audit(id);
        return new Result();
    }
    /***
     * 新增数据
     * @param goods
     * @return
     */
    @PostMapping
    public Result add(@RequestBody Goods goods){
        spuService.add(goods);
        return new Result(true,StatusCode.OK,"添加成功");
    }

    /**
     * 查询全部数据
     * @return
     */
    @GetMapping
    public Result findAll(){
        List<Spu> spuList = spuService.findAll();
        return new Result(true, StatusCode.OK,"查询成功",spuList) ;
    }

    /***
     * 根据ID查询数据
     * @param id
     * @return
     */
//    @GetMapping("/{id}")
//    public Result findById(@PathVariable String id){
//        Spu spu = spuService.findById(id);
//        return new Result(true,StatusCode.OK,"查询成功",spu);
//    }
    @GetMapping("/{id}")
    public Result findById(@PathVariable String id){
        Goods goods = spuService.findGoodsById(id);
        return new Result(true,StatusCode.OK,"查询成功",goods);
    }


    /***
     * 新增数据
     * @param spu
     * @return
     */
//    @PostMapping
//    public Result add(@RequestBody Spu spu){
//        spuService.add(spu);
//        return new Result(true,StatusCode.OK,"添加成功");
//    }


    /***
     * 修改数据
     * @param spu
     * @param id
     * @return
     */
//    @PutMapping(value="/{id}")
//    public Result update(@RequestBody Spu spu,@PathVariable String id){
//        spu.setId(id);
//        spuService.update(spu);
//        return new Result(true,StatusCode.OK,"修改成功");
//    }
    /***
     * 修改数据
     * @param goods
     * @param id
     * @return
     */
    @PutMapping(value="/{id}")
    public Result update(@RequestBody Goods goods,@PathVariable String id){
        goods.getSpu().setId(id);
        spuService.update(goods);
        return new Result(true,StatusCode.OK,"修改成功");
    }


    /***
     * 根据ID删除品牌数据
     * @param id
     * @return
     */
    @DeleteMapping(value = "/{id}" )
    public Result delete(@PathVariable String id){
        spuService.delete(id);
        return new Result(true,StatusCode.OK,"删除成功");
    }

    /***
     * 多条件搜索品牌数据
     * @param searchMap
     * @return
     */
    @GetMapping(value = "/search" )
    public Result findList(@RequestParam Map searchMap){
        List<Spu> list = spuService.findList(searchMap);
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
        Page<Spu> pageList = spuService.findPage(searchMap, page, size);
        PageResult pageResult=new PageResult(pageList.getTotal(),pageList.getResult());
        return new Result(true,StatusCode.OK,"查询成功",pageResult);
    }


}
