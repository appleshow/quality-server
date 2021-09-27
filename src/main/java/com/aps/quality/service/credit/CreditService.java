package com.aps.quality.service.credit;

import com.aps.quality.model.ResponseData;
import com.aps.quality.model.credit.CreateCreditRequest;
import com.aps.quality.model.credit.SearchCreditRequest;
import com.aps.quality.model.credit.UpdateCreditRequest;
import com.aps.quality.model.dto.CreditInfoDto;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CreditService {
    ResponseData<Boolean> create(CreateCreditRequest request);

    ResponseData<Boolean> update(UpdateCreditRequest request);

    ResponseData<Boolean> delete(Integer id);

    ResponseData<Boolean> submit(Integer id);

    ResponseData<Page<CreditInfoDto>> findPageable(SearchCreditRequest request);

    ResponseData<List<CreditInfoDto>> find(SearchCreditRequest request);
}