package com.hero.system.controller;
import com.hero.entity.PageResult;
import com.hero.entity.Result;
import com.hero.entity.StatusCode;
import com.hero.system.service.AdminService;
import com.hero.system.pojo.Admin;
import com.github.pagehelper.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;
@RestController
@CrossOrigin
@RequestMapping("/admin")
public class AdminController {


    @Autowired
    private AdminService adminService;

    /**
     * 查询全部数据
     * @return
     */
    @GetMapping
    public Result findAll(){
        List<Admin> adminList = adminService.findAll();
        return new Result(true, StatusCode.OK,"查询成功",adminList) ;
    }

    /***
     * 根据ID查询数据
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Result findById(@PathVariable Integer id){
        Admin admin = adminService.findById(id);
        return new Result(true,StatusCode.OK,"查询成功",admin);
    }


    /***
     * 新增数据
     * @param admin
     * @return
     */
    @PostMapping
    public Result add(@RequestBody Admin admin){
        adminService.add(admin);
        return new Result(true,StatusCode.OK,"添加成功");
    }


    /***
     * 修改数据
     * @param admin
     * @param id
     * @return
     */
    @PutMapping(value="/{id}")
    public Result update(@RequestBody Admin admin,@PathVariable Integer id){
        admin.setId(id);
        adminService.update(admin);
        return new Result(true,StatusCode.OK,"修改成功");
    }


    /***
     * 根据ID删除品牌数据
     * @param id
     * @return
     */
    @DeleteMapping(value = "/{id}" )
    public Result delete(@PathVariable Integer id){
        adminService.delete(id);
        return new Result(true,StatusCode.OK,"删除成功");
    }

    /***
     * 多条件搜索品牌数据
     * @param searchMap
     * @return
     */
    @GetMapping(value = "/search" )
    public Result findList(@RequestParam Map searchMap){
        List<Admin> list = adminService.findList(searchMap);
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
        Page<Admin> pageList = adminService.findPage(searchMap, page, size);
        PageResult pageResult=new PageResult(pageList.getTotal(),pageList.getResult());
        return new Result(true,StatusCode.OK,"查询成功",pageResult);
    }


}
