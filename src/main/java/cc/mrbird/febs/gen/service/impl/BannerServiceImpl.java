package cc.mrbird.febs.gen.service.impl;

import cc.mrbird.febs.common.entity.QueryRequest;
import cc.mrbird.febs.gen.entity.Banner;
import cc.mrbird.febs.gen.mapper.BannerMapper;
import cc.mrbird.febs.gen.service.IBannerService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Propagation;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 *  Service实现
 *
 * @author lxj
 * @date 2019-10-24 10:15:28
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class BannerServiceImpl extends ServiceImpl<BannerMapper, Banner> implements IBannerService {

    @Autowired
    private BannerMapper bannerMapper;

    @Override
    public IPage<Banner> findBanners(QueryRequest request, Banner banner) {
        LambdaQueryWrapper<Banner> queryWrapper = new LambdaQueryWrapper<>();
    	if(!StringUtils.isBlank(banner.getName())) {
			queryWrapper.like(Banner::getName, banner.getName());
		}
        Page<Banner> page = new Page<>(request.getPageNum(), request.getPageSize());
        return this.page(page, queryWrapper);
    }

    @Override
    public List<Banner> findBanners(Banner banner) {
	    LambdaQueryWrapper<Banner> queryWrapper = new LambdaQueryWrapper<>();
		if(!StringUtils.isBlank(banner.getName())) {
			queryWrapper.like(Banner::getName, banner.getName());
		}
		return this.baseMapper.selectList(queryWrapper);
    }

    @Override
    @Transactional
    public void createBanner(Banner banner) {
        this.save(banner);
    }

    @Override
    @Transactional
    public void updateBanner(Banner banner) {
        this.saveOrUpdate(banner);
    }

    @Override
    @Transactional
    public void deleteBanner(Banner banner) {
        LambdaQueryWrapper<Banner> wapper = new LambdaQueryWrapper<>();

	    this.remove(wapper);
	}
}
