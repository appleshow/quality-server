package com.aps.quality.service.authority;

import com.aps.quality.model.ResponseData;
import com.aps.quality.model.authority.*;
import com.aps.quality.model.dto.RoleInfoDto;
import org.springframework.data.domain.Page;

import java.util.List;

public interface RoleService {
    ResponseData<RoleInfoDto> create(CreateRoleRequest request);

    ResponseData<RoleInfoDto> update(UpdateRoleRequest request);

    ResponseData<Boolean> delete(Integer id);

    ResponseData<Boolean> assignRoleToGroup(AssignRoleToGroupRequest request);

    ResponseData<Boolean> assignRoleToUser(AssignRoleToUserRequest request);

    ResponseData<Boolean> removeRoleFromGroup(RemoveRoleFromGroupRequest request);

    ResponseData<Boolean> removeRoleFromUser(RemoveRoleFromUserRequest request);

    ResponseData<Page<RoleInfoDto>> findRolePageable(SearchRoleRequest request);

    ResponseData<List<RoleInfoDto>> findRole(SearchRoleRequest search);
}
