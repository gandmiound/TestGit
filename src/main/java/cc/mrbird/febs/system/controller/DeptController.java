package cc.mrbird.febs.system.controller;


import cc.mrbird.febs.common.annotation.Log;
import cc.mrbird.febs.common.entity.DeptTree;
import cc.mrbird.febs.common.entity.FebsResponse;
import cc.mrbird.febs.common.entity.QueryRequest;
import cc.mrbird.febs.common.exception.FebsException;
import cc.mrbird.febs.common.utils.TreeUtil;
import cc.mrbird.febs.system.entity.Dept;
import cc.mrbird.febs.system.entity.Role;
import cc.mrbird.febs.system.service.IDeptService;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.wuwenze.poi.ExcelKit;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * @author MrBird
 */
@Slf4j
@RestController
@RequestMapping("dept")
public class DeptController {

    @Autowired
    private IDeptService deptService;

    @GetMapping("select/tree")
    public List<DeptTree<Dept>> getDeptTree() throws FebsException {
        try {
            return this.deptService.findDepts();
        } catch (Exception e) {
            String message = "获取部门树失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }
    @Log("获取下级部门")
    @GetMapping("child/{deptId}")
    public List<DeptTree<Dept>> getChilds(@NotBlank(message = "{required}") @PathVariable Long deptId) throws FebsException {
            try {
            	Dept dept =deptService.getById(deptId);
                return this.deptService.findDepts( dept);
            } catch (Exception e) {
                String message = "获取部门树失败";
                log.error(message, e);
                throw new FebsException(message);
            }
    }
    @Log("获取下级部门")
    @ResponseBody
    @GetMapping("getChild/{deptId}")
    public List<Dept> getChild(@NotBlank(message = "{required}") @PathVariable String deptId) throws FebsException {
            try {
                return this.deptService.findChilds( deptId);
            } catch (Exception e) {
                String message = "获取部门树失败";
                log.error(message, e);
                throw new FebsException(message);
            }
    }
    
    @ResponseBody
    @GetMapping("getChild2/{deptId}")
    public FebsResponse getChild2(@NotBlank(message = "{required}") @PathVariable String deptId) throws FebsException {
                return new FebsResponse().success().data(deptService.findChilds(deptId));          
    }
   
    @GetMapping("tree")
    public FebsResponse getDeptTree(Dept dept) throws FebsException {
        try {
            List<DeptTree<Dept>> depts = this.deptService.findDepts(dept);
            return new FebsResponse().success().data(depts);
        } catch (Exception e) {
            String message = "获取部门树失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }

    @Log("新增部门")
    @PostMapping
    @RequiresPermissions("dept:add")
    public FebsResponse addDept(@Valid Dept dept) throws FebsException {
        try {
            this.deptService.createDept(dept);
            return new FebsResponse().success();
        } catch (Exception e) {
            String message = "新增部门失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }

    @Log("删除部门")
    @GetMapping("delete/{deptIds}")
    @RequiresPermissions("dept:delete")
    public FebsResponse deleteDepts(@NotBlank(message = "{required}") @PathVariable String deptIds) throws FebsException {
        try {
            String[] ids = deptIds.split(StringPool.COMMA);
            this.deptService.deleteDepts(ids);
            return new FebsResponse().success();
        } catch (Exception e) {
            String message = "删除部门失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }

    @Log("修改部门")
    @PostMapping("update")
    @RequiresPermissions("dept:update")
    public FebsResponse updateDept(@Valid Dept dept) throws FebsException {
        try {
            this.deptService.updateDept(dept);
            return new FebsResponse().success();
        } catch (Exception e) {
            String message = "修改部门失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }

    @GetMapping("excel")
    @RequiresPermissions("dept:export")
    public void export(Dept dept, QueryRequest request, HttpServletResponse response) throws FebsException {
        try {
            List<Dept> depts = this.deptService.findDepts(dept, request);
            ExcelKit.$Export(Dept.class, response).downXlsx(depts, false);
        } catch (Exception e) {
            String message = "导出Excel失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }
}
