package com.aps.quality.controller;

import com.aps.quality.model.ResponseData;
import com.aps.quality.model.authority.*;
import com.aps.quality.model.dto.GroupInfoDto;
import com.aps.quality.model.dto.RoleInfoDto;
import com.aps.quality.service.authority.GroupService;
import com.aps.quality.service.authority.RoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@Api(tags = "ISMP 权限相关业务")
@RestController
@RequestMapping(value = "/authority")
public class AuthorityController extends ExceptionController {
    @Resource
    private RoleService roleService;
    @Resource
    private GroupService groupService;

    @ApiOperation("分页查询权限信息")
    @PostMapping("role/pageable")
    @PreAuthorize("hasAnyAuthority('PORTAL')")
    public ResponseData<Page<RoleInfoDto>> findRolePageable(@RequestBody SearchRoleRequest search) {
        return roleService.findRolePageable(search);
    }

    @ApiOperation("查询权限信息")
    @PostMapping("role/all")
    @PreAuthorize("hasAnyAuthority('PORTAL')")
    public ResponseData<List<RoleInfoDto>> findRole(@RequestBody SearchRoleRequest search) {
        return roleService.findRole(search);
    }

    @ApiOperation("创建权限")
    @PostMapping("role/create")
    @PreAuthorize("hasAnyAuthority('PORTAL')")
    public ResponseData<RoleInfoDto> createRole(@RequestBody CreateRoleRequest request) {
        return roleService.create(request);
    }

    @ApiOperation("更新权限。权限状态将同步更新到权限组和用户中。")
    @PutMapping("role/update")
    @PreAuthorize("hasAnyAuthority('PORTAL')")
    public ResponseData<RoleInfoDto> updateRole(@RequestBody UpdateRoleRequest request) {
        return roleService.update(request);
    }

    @ApiOperation("删除权限。操作前需要移除权限组和用户中对应的权限。")
    @ApiImplicitParams({@ApiImplicitParam(name = "id", required = true, example = "权限ID")})
    @DeleteMapping("role/{id}/delete")
    @PreAuthorize("hasAnyAuthority('PORTAL')")
    public ResponseData<Boolean> deleteRole(@PathVariable Integer id) {
        return roleService.delete(id);
    }

    @ApiOperation("将权限赋予权限组。不影响已分配权限组用户的权限。")
    @PutMapping("role/assign/group")
    @PreAuthorize("hasAnyAuthority('PORTAL')")
    public ResponseData<Boolean> assignRoleToGroup(@RequestBody AssignRoleToGroupRequest request) {
        return roleService.assignRoleToGroup(request);
    }

    @ApiOperation("将权限赋予用户")
    @PutMapping("role/assign/user")
    @PreAuthorize("hasAnyAuthority('PORTAL')")
    public ResponseData<Boolean> assignRoleToUser(@RequestBody AssignRoleToUserRequest request) {
        return roleService.assignRoleToUser(request);
    }

    @ApiOperation("将权限从权限组中移除。不影响用户已分配权限，仅影响以后分配权限组的用户权限。")
    @DeleteMapping("role/remove/group")
    @PreAuthorize("hasAnyAuthority('PORTAL')")
    public ResponseData<Boolean> removeRoleFromGroup(@RequestBody RemoveRoleFromGroupRequest request) {
        return roleService.removeRoleFromGroup(request);
    }

    @ApiOperation("将权限从用户中移除")
    @DeleteMapping("role/remove/user")
    @PreAuthorize("hasAnyAuthority('PORTAL')")
    public ResponseData<Boolean> removeRoleFromUser(@RequestBody RemoveRoleFromUserRequest request) {
        return roleService.removeRoleFromUser(request);
    }

    @ApiOperation("分页查询权限组信息")
    @PostMapping("group/pageable")
    @PreAuthorize("hasAnyAuthority('PORTAL')")
    public ResponseData<Page<GroupInfoDto>> findGroupPageable(@RequestBody SearchGroupRequest search) {
        return groupService.findGroupPageable(search);
    }

    @ApiOperation("查询权限组信息")
    @PostMapping("group/all")
    @PreAuthorize("hasAnyAuthority('PORTAL')")
    public ResponseData<List<GroupInfoDto>> findGroup(@RequestBody SearchGroupRequest search) {
        return groupService.findGroup(search);
    }

    @ApiOperation("创建权限组")
    @PostMapping("group/create")
    @PreAuthorize("hasAnyAuthority('PORTAL')")
    public ResponseData<GroupInfoDto> createGroup(@RequestBody CreateGroupRequest request) {
        return groupService.create(request);
    }

    @ApiOperation("更新权限组")
    @PutMapping("group/update")
    @PreAuthorize("hasAnyAuthority('PORTAL')")
    public ResponseData<GroupInfoDto> updateGroup(@RequestBody UpdateGroupRequest request) {
        return groupService.update(request);
    }

    @ApiOperation("删除权限组")
    @ApiImplicitParams({@ApiImplicitParam(name = "id", required = true, example = "权限组ID")})
    @DeleteMapping("group/{id}/delete")
    @PreAuthorize("hasAnyAuthority('PORTAL')")
    public ResponseData<Boolean> deleteGroup(@PathVariable Integer id) {
        return groupService.delete(id);
    }

    @ApiOperation("将权限组赋予用户")
    @PutMapping("group/assign/user")
    @PreAuthorize("hasAnyAuthority('PORTAL')")
    public ResponseData<Boolean> assignGroupToUser(@RequestBody AssignGroupToUserRequest request) {
        return groupService.assignGroupToUser(request);
    }
}