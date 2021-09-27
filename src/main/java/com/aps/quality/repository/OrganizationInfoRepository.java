package com.aps.quality.repository;

import com.aps.quality.entity.OrganizationInfo;
import com.aps.quality.model.organization.SearchOrganizationRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrganizationInfoRepository extends JpaRepository<OrganizationInfo, Integer>,
        JpaSpecificationExecutor<OrganizationInfo>,
        PagingAndSortingRepository<OrganizationInfo, Integer> {

    @Query("SELECT COUNT(a.organizationId) FROM OrganizationInfo a " +
            "WHERE a.organizationId <> :organizationId " +
            "  AND a.fatherOrganizationId = :fatherOrganizationId " +
            "  AND a.organizationName = :organizationName")
    Optional<Integer> countByOrganizationName(@Param("organizationId") Integer organizationId, @Param("fatherOrganizationId") Integer fatherOrganizationId, @Param("organizationName") String organizationName);

    @Query("SELECT a FROM OrganizationInfo a " +
            "WHERE (:#{#search.organizationId} IS NULL OR a.organizationId = :#{#search.organizationId})" +
            "  AND (:#{#search.fatherOrganizationId} IS NULL OR a.fatherOrganizationId = :#{#search.fatherOrganizationId}) " +
            "  AND (:#{#search.organizationName} IS NULL OR a.organizationName LIKE :#{#search.organizationName}) " +
            "  AND (:#{#search.organizationType} IS NULL OR a.organizationType = :#{#search.organizationType}) " +
            "  AND (:#{#search.flag} IS NULL OR a.flag = :#{#search.flag}) " +
            "  AND (:#{#search.status} IS NULL OR a.status = :#{#search.status}) ")
    Page<OrganizationInfo> findPageable(@Param("search") SearchOrganizationRequest search, Pageable pageable);

    @Query("SELECT a FROM OrganizationInfo a " +
            "WHERE (:#{#search.organizationId} IS NULL OR a.organizationId = :#{#search.organizationId})" +
            "  AND (:#{#search.fatherOrganizationId} IS NULL OR a.fatherOrganizationId = :#{#search.fatherOrganizationId}) " +
            "  AND (:#{#search.organizationName} IS NULL OR a.organizationName LIKE :#{#search.organizationName}) " +
            "  AND (:#{#search.organizationType} IS NULL OR a.organizationType = :#{#search.organizationType}) " +
            "  AND (:#{#search.flag} IS NULL OR a.flag = :#{#search.flag}) " +
            "  AND (:#{#search.status} IS NULL OR a.status = :#{#search.status}) " +
            "ORDER BY a.fatherOrganizationId,a.organizationType DESC, a.organizationId")
    List<OrganizationInfo> find(@Param("search") SearchOrganizationRequest search);

    @Query("SELECT a FROM OrganizationInfo a " +
            "WHERE a.organizationId IN(SELECT b.organizationId FROM UserOrganizationInfo b WHERE b.userId = :userId)")
    List<OrganizationInfo> findByUser(@Param("userId") Integer userId);
}