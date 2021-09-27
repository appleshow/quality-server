package com.aps.quality.controller;

import com.aps.quality.model.ResponseData;
import com.aps.quality.model.campaign.CreateCampaignRequest;
import com.aps.quality.model.campaign.SearchCampaignRequest;
import com.aps.quality.model.campaign.UpdateCampaignRequest;
import com.aps.quality.model.dto.CampaignInfoDto;
import com.aps.quality.service.campaign.CampaignService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@Api(tags = "活动相关接口")
@RestController
@RequestMapping("/campaign")
public class CampaignController extends ExceptionController {
    @Resource
    private CampaignService campaignService;

    @ApiOperation("创建新活动")
    @PostMapping("create")
    @PreAuthorize("hasAnyAuthority('PORTAL')")
    public ResponseData<Boolean> create(@RequestBody final CreateCampaignRequest request) {
        return campaignService.create(request);
    }

    @ApiOperation("更新活动")
    @PutMapping("update")
    @PreAuthorize("hasAnyAuthority('PORTAL')")
    public ResponseData<Boolean> update(@RequestBody final UpdateCampaignRequest request) {
        return campaignService.update(request);
    }

    @ApiOperation("删除活动")
    @DeleteMapping("{id}/delete")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", required = true, example = "活动ID")
    })
    @PreAuthorize("hasAnyAuthority('PORTAL')")
    public ResponseData<Boolean> delete(@PathVariable final Integer id) {
        return campaignService.delete(id);
    }

    @ApiOperation("废弃活动")
    @PutMapping("{id}/invalid")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", required = true, example = "活动ID")
    })
    @PreAuthorize("hasAnyAuthority('PORTAL')")
    public ResponseData<Boolean> invalid(@PathVariable final Integer id) {
        return campaignService.invalid(id);
    }

    @ApiOperation("启用活动")
    @PutMapping("{id}/enable")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", required = true, example = "活动ID")
    })
    @PreAuthorize("hasAnyAuthority('PORTAL')")
    public ResponseData<Boolean> enable(@PathVariable final Integer id) {
        return campaignService.enable(id);
    }

    @ApiOperation("分页查询活动")
    @PostMapping("pageable")
    @PreAuthorize("hasAnyAuthority('PORTAL')")
    public ResponseData<Page<CampaignInfoDto>> findPageable(@RequestBody final SearchCampaignRequest request) {
        return campaignService.findPageable(request);
    }

    @ApiOperation("查询活动")
    @PostMapping("all")
    @PreAuthorize("hasAnyAuthority('PORTAL')")
    public ResponseData<List<CampaignInfoDto>> find(@RequestBody final SearchCampaignRequest request) {
        return campaignService.find(request);
    }
}
