package com.aps.quality.service.user;

import com.aps.quality.model.ResponseData;
import com.aps.quality.model.dto.UserInfoDto;
import com.aps.quality.model.user.CreateUserRequest;
import com.aps.quality.model.user.ResetPasswordRequest;
import com.aps.quality.model.user.SearchUserRequest;
import com.aps.quality.model.user.UpdateUserRequest;
import org.springframework.data.domain.Page;

import java.util.List;

public interface UserService {
    ResponseData<Boolean> create(CreateUserRequest request);

    ResponseData<Boolean> update(UpdateUserRequest request);

    ResponseData<Boolean> invalid(Integer id);

    ResponseData<Boolean> enable(Integer id);

    ResponseData<Boolean> resetPassword(Integer id);

    ResponseData<Boolean> setPassword(ResetPasswordRequest request);

    ResponseData<Page<UserInfoDto>> findPageable(SearchUserRequest request);

    ResponseData<List<UserInfoDto>> find(SearchUserRequest request);
}