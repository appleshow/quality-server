package com.aps.quality.repository;

import com.aps.quality.entity.UserInfo;
import com.aps.quality.model.user.SearchUserRequest;
import jdk.nashorn.internal.ir.Optimistic;
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
public interface UserInfoRepository extends JpaRepository<UserInfo, Integer>,
        JpaSpecificationExecutor<UserInfo>,
        PagingAndSortingRepository<UserInfo, Integer> {

    @Query("SELECT COUNT(a.userId) FROM UserInfo a " +
            "WHERE a.userId <> :userId " +
            "  AND a.userCode = :userCode")
    Optional<Integer> countByUserCode(@Param("userId") Integer userId, @Param("userCode") String userCode);

    Optional<Integer> countByOrganizationId(Integer organizationId);

    Optional<UserInfo> findByUserCode(String userCode);

    Optional<UserInfo> findBySsoId(String ssoId);

    @Query("SELECT a FROM UserInfo a " +
            "WHERE (:#{#search.userId} IS NULL OR a.userId = :#{#search.userId}) " +
            "  AND (:#{#search.userCode} IS NULL OR a.userCode = :#{#search.userCode}) " +
            "  AND (:#{#search.userName} IS NULL OR lower(a.userName) LIKE :#{#search.userName}) " +
            "  AND (:#{#search.userPhone} IS NULL OR a.userPhone LIKE :#{#search.userPhone}) " +
            "  AND (:#{#search.userEmail} IS NULL OR a.userEmail LIKE :#{#search.userEmail}) " +
            "  AND (:#{#search.userType} IS NULL OR a.userType = :#{#search.userType}) " +
            "  AND (:#{#search.userGender} IS NULL OR a.userGender = :#{#search.userGender}) " +
            "  AND (:#{#search.flag} IS NULL OR a.flag = :#{#search.flag}) " +
            "  AND (:#{#search.status} IS NULL OR a.status = :#{#search.status}) " +
            "  AND (:#{#search.organizationId} IS NULL OR a.organizationId IN (SELECT b.childOrganizationId FROM OrganizationMappingInfo b WHERE b.fatherOrganizationId = :#{#search.organizationId})) ")
    Page<UserInfo> findPageable(@Param("search") SearchUserRequest search, Pageable pageable);

    @Query("SELECT a FROM UserInfo a " +
            "WHERE (:#{#search.userId} IS NULL OR a.userId = :#{#search.userId}) " +
            "  AND (:#{#search.userCode} IS NULL OR a.userCode = :#{#search.userCode}) " +
            "  AND (:#{#search.userName} IS NULL OR lower(a.userName) LIKE :#{#search.userName}) " +
            "  AND (:#{#search.userPhone} IS NULL OR a.userPhone LIKE :#{#search.userPhone}) " +
            "  AND (:#{#search.userEmail} IS NULL OR a.userEmail LIKE :#{#search.userEmail}) " +
            "  AND (:#{#search.userType} IS NULL OR a.userType = :#{#search.userType}) " +
            "  AND (:#{#search.userGender} IS NULL OR a.userGender = :#{#search.userGender}) " +
            "  AND (:#{#search.flag} IS NULL OR a.flag = :#{#search.flag}) " +
            "  AND (:#{#search.status} IS NULL OR a.status = :#{#search.status}) " +
            "  AND (:#{#search.organizationId} IS NULL OR a.organizationId IN (SELECT b.childOrganizationId FROM OrganizationMappingInfo b WHERE b.fatherOrganizationId = :#{#search.organizationId})) ")
    List<UserInfo> find(@Param("search") SearchUserRequest search);

    @Query("SELECT a.userId FROM UserInfo a " +
            "WHERE a.status = 1 " +
            "  AND a.userType IN ('MASTER','YLC_L1','FACULTY') " +
            "  AND a.organizationId IN (SELECT b.fatherOrganizationId FROM OrganizationMappingInfo b WHERE b.childOrganizationId = :organizationId) ")
    Optional<List<Integer>> findApprovalUserId(@Param("organizationId") Integer organizationId);
}