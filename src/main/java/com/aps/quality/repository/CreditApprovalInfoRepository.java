package com.aps.quality.repository;

import com.aps.quality.entity.CreditApprovalInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CreditApprovalInfoRepository extends JpaRepository<CreditApprovalInfo, Integer>,
        JpaSpecificationExecutor<CreditApprovalInfo>,
        PagingAndSortingRepository<CreditApprovalInfo, Integer> {

    Optional<List<CreditApprovalInfo>> findByCreditId(Integer creditId);
}