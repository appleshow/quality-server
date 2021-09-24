package com.aps.quality.controller;

import com.aps.quality.model.ResponseData;
import com.aps.quality.model.dto.OrganizationInfoDto;
import com.aps.quality.model.organization.CreateOrganizationRequest;
import com.aps.quality.model.organization.SearchOrganizationRequest;
import com.aps.quality.model.organization.UpdateOrganizationRequest;
import com.aps.quality.service.organization.OrganizationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@Api(tags = "组织相关接口")
@RestController
@RequestMapping("/organization")
public class OrganizationController extends ExceptionController {
    @Resource
    private OrganizationService organizationService;

    @ApiOperation("创建组织")
    @PostMapping("create")
    @PreAuthorize("hasAnyAuthority('PORTAL')")
    public ResponseData<Boolean> create(@RequestBody final CreateOrganizationRequest request) {
        return organizationService.create(request);
    }

    @ApiOperation("更新组织")
    @PutMapping("update")
    @PreAuthorize("hasAnyAuthority('PORTAL')")
    public ResponseData<Boolean> update(@RequestBody final UpdateOrganizationRequest request) {
        return organizationService.update(request);
    }

    @ApiOperation("删除组织")
    @DeleteMapping("{id}/delete")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", required = true, example = "组织 ID")
    })
    @PreAuthorize("hasAnyAuthority('PORTAL')")
    public ResponseData<Boolean> delete(@PathVariable final Integer id) {
        return organizationService.delete(id);
    }

    @ApiOperation("分页查询组织")
    @PostMapping("findPageable")
    @PreAuthorize("hasAnyAuthority('PORTAL')")
    public ResponseData<Page<OrganizationInfoDto>> findPageable(@RequestBody final SearchOrganizationRequest request) {
        return organizationService.findPageable(request);
    }

    @ApiOperation("查询组织")
    @PostMapping("all")
    @PreAuthorize("hasAnyAuthority('PORTAL')")
    public ResponseData<List<OrganizationInfoDto>> find(@RequestBody final SearchOrganizationRequest request) {
        return organizationService.find(request);
    }

    @ApiOperation("查询组所属组织")
    @GetMapping("find/{userId}/byUser")
    @PreAuthorize("hasAnyAuthority('PORTAL')")
    public ResponseData<List<OrganizationInfoDto>> findByGroup(@PathVariable final Integer userId) {
        return organizationService.findByUser(userId);
    }
}
