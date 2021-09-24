package com.aps.quality.repository;

import com.aps.quality.entity.RoleInfo;
import com.aps.quality.model.authority.SearchRoleRequest;
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
public interface RoleInfoRepository extends JpaRepository<RoleInfo, Integer>,
        JpaSpecificationExecutor<RoleInfo>,
        PagingAndSortingRepository<RoleInfo, Integer> {

    Optional<Integer> countByRoleName(String roleName);

    List<RoleInfo> findByRoleName(String roleName);

    @Query("SELECT a FROM RoleInfo a " +
            "WHERE (:#{#search.roleId} IS NULL OR a.roleId = :#{#search.roleId}) " +
            "  AND (:#{#search.status} IS NULL OR a.status = :#{#search.status}) " +
            "  AND (:#{#search.roleName} IS NULL OR a.roleName LIKE :#{#search.roleName}) ")
    Page<RoleInfo> findPageable(@Param("search") SearchRoleRequest search, Pageable pageable);

    @Query("SELECT a FROM RoleInfo a " +
            "WHERE (:#{#search.roleId} IS NULL OR a.roleId = :#{#search.roleId}) " +
            "  AND (:#{#search.status} IS NULL OR a.status = :#{#search.status}) " +
            "  AND (:#{#search.roleName} IS NULL OR a.roleName LIKE :#{#search.roleName}) " +
            "ORDER BY a.fatherRoleId,a.roleId")
    List<RoleInfo> find(@Param("search") SearchRoleRequest search);
}