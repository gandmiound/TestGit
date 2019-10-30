package cc.mrbird.febs.gen.service;

import cc.mrbird.febs.gen.entity.Banner;

import cc.mrbird.febs.common.entity.QueryRequest;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 *  Service接口
 *
 * @author lxj
 * @date 2019-10-24 10:15:28
 */
public interface IBannerService extends IService<Banner> {
    /**
     * 查询（分页）
     *
     * @param request QueryRequest
     * @param banner banner
     * @return IPage<Banner>
     */
    IPage<Banner> findBanners(QueryRequest request, Banner banner);

    /**
     * 查询（所有）
     *
     * @param banner banner
     * @return List<Banner>
     */
    List<Banner> findBanners(Banner banner);

    /**
     * 新增
     *
     * @param banner banner
     */
    void createBanner(Banner banner);

    /**
     * 修改
     *
     * @param banner banner
     */
    void updateBanner(Banner banner);

    /**
     * 删除
     *
     * @param banner banner
     */
    void deleteBanner(Banner banner);
}
