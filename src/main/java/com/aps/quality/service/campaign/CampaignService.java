package com.aps.quality.service.campaign;

import com.aps.quality.model.ResponseData;
import com.aps.quality.model.campaign.CreateCampaignRequest;
import com.aps.quality.model.campaign.SearchCampaignRequest;
import com.aps.quality.model.campaign.UpdateCampaignRequest;
import com.aps.quality.model.dto.CampaignInfoDto;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CampaignService {
    ResponseData<Boolean> create(CreateCampaignRequest request);

    ResponseData<Boolean> update(UpdateCampaignRequest request);

    ResponseData<Boolean> delete(Integer id);

    ResponseData<Boolean> invalid(Integer id);

    ResponseData<Boolean> enable(Integer id);

    ResponseData<Page<CampaignInfoDto>> findPageable(SearchCampaignRequest request);

    ResponseData<List<CampaignInfoDto>> find(SearchCampaignRequest request);
}