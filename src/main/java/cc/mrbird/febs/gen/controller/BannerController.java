package cc.mrbird.febs.gen.controller;

import cc.mrbird.febs.common.annotation.Log;
import cc.mrbird.febs.common.utils.FebsUtil;
import cc.mrbird.febs.common.entity.FebsConstant;
import cc.mrbird.febs.common.controller.BaseController;
import cc.mrbird.febs.common.entity.FebsResponse;
import cc.mrbird.febs.common.entity.QueryRequest;
import cc.mrbird.febs.common.exception.FebsException;
import cc.mrbird.febs.gen.entity.Banner;
import cc.mrbird.febs.gen.service.IBannerService;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.wuwenze.poi.ExcelKit;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 *  Controller
 *
 * @author lxj
 * @date 2019-10-24 10:15:28
 */
@Slf4j
@Validated
@Controller
public class BannerController extends BaseController {

    @Autowired
    private IBannerService bannerService;

    @GetMapping(FebsConstant.VIEW_PREFIX + "gen/banner")
    @RequiresPermissions("banner:list")
    private String bannerIndex(){
        return FebsUtil.view("gen/banner/banner");
    }
	@GetMapping(FebsConstant.VIEW_PREFIX + "gen/banner/add")
   	@RequiresPermissions("banner:add")
   	public String bannerAdd() {
   		return FebsUtil.view("gen/banner/bannerAdd");
   	}

   	@GetMapping(FebsConstant.VIEW_PREFIX + "gen/banner/update/{bannerId}")
   	@RequiresPermissions("banner:update")
   	public String bannerUpdate(@PathVariable Integer bannerId, Model model) {
   		Banner banner = bannerService.getById(bannerId);
   		model.addAttribute("banner", banner);
   		return FebsUtil.view("gen/banner/bannerUpdate");
   	}
    @GetMapping("banner")
    @ResponseBody
    @RequiresPermissions("banner:list")
    public FebsResponse getAllBanners(Banner banner) {
        return new FebsResponse().success().data(bannerService.findBanners(banner));
    }

    @GetMapping("banner/list")
    @ResponseBody
    @RequiresPermissions("banner:list")
    public FebsResponse bannerList(QueryRequest request, Banner banner) {
        Map<String, Object> dataTable = getDataTable(this.bannerService.findBanners(request, banner));
        return new FebsResponse().success().data(dataTable);
    }

    @Log("新增Banner")
    @PostMapping("banner")
    @ResponseBody
    @RequiresPermissions("banner:add")
    public FebsResponse addBanner(@Valid Banner banner) throws FebsException {
        try {
            this.bannerService.createBanner(banner);
            return new FebsResponse().success();
        } catch (Exception e) {
            String message = "新增Banner失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }

    @Log("删除Banner")
    @GetMapping("banner/delete/{bannerIds}")
    @ResponseBody
    @RequiresPermissions("banner:delete")
    public FebsResponse deleteBanner(@NotBlank(message = "{required}") @PathVariable String bannerIds) throws FebsException {
        try {
            String[] ids = bannerIds.split(StringPool.COMMA);
            List<String> list = Arrays.asList(ids);
            this.bannerService.removeByIds(list);
            return new FebsResponse().success();
        } catch (Exception e) {
            String message = "删除Banner失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }

    @Log("修改Banner")
    @PostMapping("banner/update")
    @ResponseBody
    @RequiresPermissions("banner:update")
    public FebsResponse updateBanner(Banner banner) throws FebsException {
        try {
            this.bannerService.updateBanner(banner);
            return new FebsResponse().success();
        } catch (Exception e) {
            String message = "修改Banner失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }

    @PostMapping("banner/excel")
    @ResponseBody
    @RequiresPermissions("banner:export")
    public void export(QueryRequest queryRequest, Banner banner, HttpServletResponse response) throws FebsException {
        try {
            List<Banner> banners = this.bannerService.findBanners(queryRequest, banner).getRecords();
            ExcelKit.$Export(Banner.class, response).downXlsx(banners, false);
        } catch (Exception e) {
            String message = "导出Excel失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }
}
