package com.aps.quality.service.credit;

import com.aps.quality.model.ResponseData;
import com.aps.quality.model.credit.*;
import com.aps.quality.model.dto.CreditInfoDto;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface CreditService {
    ResponseData<Boolean> create(CreateCreditRequest request);

    ResponseData<Boolean> update(UpdateCreditRequest request);

    ResponseData<Boolean> delete(Integer id);

    ResponseData<Boolean> submit(Integer id);

    ResponseData<Page<CreditReport>> findPageable(SearchCreditRequest request);

    ResponseData<List<CreditReport>> find(SearchCreditRequest request);

    ResponseData<List<CreditReport>> findSub(SearchCreditRequest request);

    ResponseData<UploadResponse> uploadFile(MultipartFile file);

    ResponseData<Boolean> removeUploadFile(RemoveUploadRequest request);

    ResponseData<Boolean> useUploadFile(List<UseUploadRequest> requests);

    ResponseData<ImportResponse> importCredit(MultipartFile file);


}