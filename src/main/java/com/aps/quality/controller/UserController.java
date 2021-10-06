package com.aps.quality.controller;

import com.aps.quality.model.ResponseData;
import com.aps.quality.model.dto.UserConciseDto;
import com.aps.quality.model.dto.UserInfoDto;
import com.aps.quality.model.user.CreateUserRequest;
import com.aps.quality.model.user.ResetPasswordRequest;
import com.aps.quality.model.user.SearchUserRequest;
import com.aps.quality.model.user.UpdateUserRequest;
import com.aps.quality.service.user.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@Api(tags = "用户相关接口")
@RestController
@RequestMapping("/user")
public class UserController extends ExceptionController {
    @Resource
    private UserService userService;

    @ApiOperation("创建新用户")
    @PostMapping("create")
    @PreAuthorize("hasAnyAuthority('PORTAL')")
    public ResponseData<Boolean> create(@RequestBody final CreateUserRequest request) {
        return userService.create(request);
    }

    @ApiOperation("更新用户")
    @PutMapping("update")
    @PreAuthorize("hasAnyAuthority('PORTAL')")
    public ResponseData<Boolean> update(@RequestBody final UpdateUserRequest request) {
        return userService.update(request);
    }

    @ApiOperation("废弃用户")
    @PutMapping("{id}/invalid")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", required = true, example = "用户ID")
    })
    @PreAuthorize("hasAnyAuthority('PORTAL')")
    public ResponseData<Boolean> invalid(@PathVariable final Integer id) {
        return userService.invalid(id);
    }

    @ApiOperation("启用用户")
    @PutMapping("{id}/enable")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", required = true, example = "用户ID")
    })
    @PreAuthorize("hasAnyAuthority('PORTAL')")
    public ResponseData<Boolean> enable(@PathVariable final Integer id) {
        return userService.enable(id);
    }


    @ApiOperation("使用默认值重置用户密码")
    @PutMapping("{id}/resetPassword")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", required = true, example = "用户ID")
    })
    @PreAuthorize("hasAnyAuthority('PORTAL')")
    public ResponseData<Boolean> resetPassword(@PathVariable final Integer id) {
        return userService.resetPassword(id);
    }

    @ApiOperation("设置当前用户密码")
    @PutMapping("setPassword")
    @PreAuthorize("hasAnyAuthority('PORTAL')")
    public ResponseData<Boolean> setPassword(@RequestBody final ResetPasswordRequest request) {
        return userService.setPassword(request);
    }

    @ApiOperation("分页查询用户")
    @PostMapping("pageable")
    @PreAuthorize("hasAnyAuthority('PORTAL')")
    public ResponseData<Page<UserInfoDto>> findPageable(@RequestBody final SearchUserRequest request) {
        return userService.findPageable(request);
    }

    @ApiOperation("查询用户")
    @PostMapping("all")
    @PreAuthorize("hasAnyAuthority('PORTAL')")
    public ResponseData<List<UserInfoDto>> find(@RequestBody final SearchUserRequest request) {
        return userService.find(request);
    }

    @ApiOperation("查询可审批用户")
    @GetMapping("all/approval")
    @PreAuthorize("hasAnyAuthority('PORTAL')")
    public ResponseData<List<UserConciseDto>> findApproval() {
        return userService.findApproval();
    }
}
