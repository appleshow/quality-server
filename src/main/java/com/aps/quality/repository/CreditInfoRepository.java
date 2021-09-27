package com.aps.quality.repository;

import com.aps.quality.entity.CreditInfo;
import com.aps.quality.model.credit.SearchCreditRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CreditInfoRepository extends JpaRepository<CreditInfo, Integer>,
        JpaSpecificationExecutor<CreditInfo>,
        PagingAndSortingRepository<CreditInfo, Integer> {

    @Query("SELECT a FROM CreditInfo a " +
            "WHERE (:#{#search.creditId} IS NULL OR a.creditId = :#{#search.creditId}) " +
            "  AND (:#{#search.creditTimeFrom} IS NULL OR a.createTime >= :#{#search.creditTimeFrom}) " +
            "  AND (:#{#search.creditTimeTo} IS NULL OR a.createTime <= :#{#search.creditTimeTo}) " +
            "  AND (:#{#search.instructor} IS NULL OR a.instructor LIKE :#{#search.instructor}) " +
            "  AND (:#{#search.flag} IS NULL OR a.flag = :#{#search.flag}) " +
            "  AND (:#{#search.status} IS NULL OR a.status = :#{#search.status}) ")
    Page<CreditInfo> findPageable(@Param("search") SearchCreditRequest search, Pageable pageable);

    @Query("SELECT a FROM CreditInfo a " +
            "WHERE (:#{#search.creditId} IS NULL OR a.creditId = :#{#search.creditId}) " +
            "  AND (:#{#search.creditTimeFrom} IS NULL OR a.createTime >= :#{#search.creditTimeFrom}) " +
            "  AND (:#{#search.creditTimeTo} IS NULL OR a.createTime <= :#{#search.creditTimeTo}) " +
            "  AND (:#{#search.instructor} IS NULL OR a.instructor LIKE :#{#search.instructor}) " +
            "  AND (:#{#search.flag} IS NULL OR a.flag = :#{#search.flag}) " +
            "  AND (:#{#search.status} IS NULL OR a.status = :#{#search.status}) ")
    List<CreditInfo> find(@Param("search") SearchCreditRequest search);
}