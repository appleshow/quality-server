package com.aps.quality.controller;

import com.aps.quality.model.ResponseData;
import com.aps.quality.model.credit.*;
import com.aps.quality.service.credit.CreditService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
    public ResponseData<Page<CreditReport>> findPageable(@RequestBody final SearchCreditRequest request) {
        return creditService.findPageable(request);
    }

    @ApiOperation("查询学分")
    @PostMapping("all")
    @PreAuthorize("hasAnyAuthority('PORTAL')")
    public ResponseData<List<CreditReport>> find(@RequestBody final SearchCreditRequest request) {
        return creditService.find(request);
    }

    @ApiOperation("查询展开学分")
    @PostMapping("all/sub")
    @PreAuthorize("hasAnyAuthority('PORTAL')")
    public ResponseData<List<CreditReport>> findSub(@RequestBody final SearchCreditRequest request) {
        return creditService.findSub(request);
    }

    @ApiOperation("上传材料")
    @PostMapping("upload/file")
    @PreAuthorize("hasAnyAuthority('PORTAL')")
    public ResponseData<UploadResponse> uploadFile(@RequestParam(value = "file") MultipartFile file) {
        return creditService.uploadFile(file);
    }

    @ApiOperation("删除已上传材料")
    @PutMapping("remove/file")
    @PreAuthorize("hasAnyAuthority('PORTAL')")
    public ResponseData<Boolean> removeUploadFile(@RequestBody RemoveUploadRequest request) {
        return creditService.removeUploadFile(request);
    }

    @ApiOperation("确认上传材料")
    @PostMapping("use/file")
    @PreAuthorize("hasAnyAuthority('PORTAL')")
    public ResponseData<Boolean> useUploadFile(@RequestBody List<UseUploadRequest> requests) {
        return creditService.useUploadFile(requests);
    }

    @ApiOperation("导入学分")
    @PostMapping("import/file")
    @PreAuthorize("hasAnyAuthority('PORTAL')")
    public ResponseData<ImportResponse> importCredit(@RequestParam(value = "file") MultipartFile file) {
        return creditService.importCredit(file);
    }
}
