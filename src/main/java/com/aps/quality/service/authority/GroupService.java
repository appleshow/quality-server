package com.aps.quality.service.authority;

import com.aps.quality.model.ResponseData;
import com.aps.quality.model.authority.AssignGroupToUserRequest;
import com.aps.quality.model.authority.CreateGroupRequest;
import com.aps.quality.model.authority.SearchGroupRequest;
import com.aps.quality.model.authority.UpdateGroupRequest;
import com.aps.quality.model.dto.GroupInfoDto;
import org.springframework.data.domain.Page;

import java.util.List;

public interface GroupService {
    ResponseData<GroupInfoDto> create(CreateGroupRequest request);

    ResponseData<GroupInfoDto> update(UpdateGroupRequest request);

    ResponseData<Boolean> delete(Integer id);

    ResponseData<Boolean> assignGroupToUser(AssignGroupToUserRequest request);

    ResponseData<Page<GroupInfoDto>> findGroupPageable(SearchGroupRequest request);

    ResponseData<List<GroupInfoDto>> findGroup(SearchGroupRequest search);
}
