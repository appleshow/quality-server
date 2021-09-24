package com.aps.quality.service.organization;

import com.aps.quality.model.ResponseData;
import com.aps.quality.model.dto.OrganizationInfoDto;
import com.aps.quality.model.organization.CreateOrganizationRequest;
import com.aps.quality.model.organization.SearchOrganizationRequest;
import com.aps.quality.model.organization.UpdateOrganizationRequest;
import org.springframework.data.domain.Page;

import java.util.List;

public interface OrganizationService {
    ResponseData<Boolean> create(CreateOrganizationRequest request);

    ResponseData<Boolean> update(UpdateOrganizationRequest request);

    ResponseData<Boolean> delete(Integer id);

    ResponseData<Page<OrganizationInfoDto>> findPageable(SearchOrganizationRequest request);

    ResponseData<List<OrganizationInfoDto>> find(SearchOrganizationRequest request);

    ResponseData<List<OrganizationInfoDto>> findByUser(Integer userId);
}
