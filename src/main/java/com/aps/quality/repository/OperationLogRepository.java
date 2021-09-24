package com.aps.quality.repository;

import com.aps.quality.entity.OperationLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OperationLogRepository extends JpaRepository<OperationLog, Integer>,
        JpaSpecificationExecutor<OperationLog>,
        PagingAndSortingRepository<OperationLog, Integer> {
}