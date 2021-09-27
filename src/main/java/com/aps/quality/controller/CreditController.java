package com.aps.quality.controller;

import com.aps.quality.model.ResponseData;
import com.aps.quality.model.credit.CreateCreditRequest;
import com.aps.quality.model.credit.SearchCreditRequest;
import com.aps.quality.model.credit.UpdateCreditRequest;
import com.aps.quality.model.dto.CreditInfoDto;
import com.aps.quality.service.credit.CreditService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@Api(tags = "学分相关接口")
@RestController
@RequestMapping("/credit")
public class CreditController extends ExceptionController {
    @Resource
    private CreditService creditService;

    @ApiOperation("创建新学分")
    @PostMapping("create")
    @PreAuthorize("hasAnyAuthority('PORTAL')")
    public ResponseData<Boolean> create(@RequestBody final CreateCreditRequest request) {
        return creditService.create(request);
    }

    @ApiOperation("更新学分")
    @PutMapping("update")
    @PreAuthorize("hasAnyAuthority('PORTAL')")
    public ResponseData<Boolean> update(@RequestBody final UpdateCreditRequest request) {
        return creditService.update(request);
    }

    @ApiOperation("删除学分")
    @DeleteMapping("{id}/delete")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", required = true, example = "学分ID")
    })
    @PreAuthorize("hasAnyAuthority('PORTAL')")
    public ResponseData<Boolean> delete(@PathVariable final Integer id) {
        return creditService.delete(id);
    }

    @ApiOperation("提交学分")
    @PutMapping("{id}/submit")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", required = true, example = "学分ID")
    })
    @PreAuthorize("hasAnyAuthority('PORTAL')")
    public ResponseData<Boolean> submit(@PathVariable final Integer id) {
        return creditService.submit(id);
    }

    @ApiOperation("分页查询学分")
    @PostMapping("pageable")
    @PreAuthorize("hasAnyAuthority('PORTAL')")
    public ResponseData<Page<CreditInfoDto>> findPageable(@RequestBody final SearchCreditRequest request) {
        return creditService.findPageable(request);
    }

    @ApiOperation("查询学分")
    @PostMapping("all")
    @PreAuthorize("hasAnyAuthority('PORTAL')")
    public ResponseData<List<CreditInfoDto>> find(@RequestBody final SearchCreditRequest request) {
        return creditService.find(request);
    }
}
