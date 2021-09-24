package com.aps.quality.repository;

import com.aps.quality.entity.GroupInfo;
import com.aps.quality.model.authority.SearchGroupRequest;
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
public interface GroupInfoRepository extends JpaRepository<GroupInfo, Integer>,
        JpaSpecificationExecutor<GroupInfo>,
        PagingAndSortingRepository<GroupInfo, Integer> {

    Optional<Integer> countByGroupName(String groupName);

    List<GroupInfo> findByGroupName(String groupName);

    @Query("SELECT a FROM GroupInfo a " +
            "WHERE (:#{#search.groupId} IS NULL OR a.groupId = :#{#search.groupId}) " +
            "AND   (:#{#search.status} IS NULL OR a.status = :#{#search.status}) " +
            "AND   (:#{#search.groupName} IS NULL OR a.groupName LIKE :#{#search.groupName}) ")
    Page<GroupInfo> findPageable(@Param("search") SearchGroupRequest search, Pageable pageable);

    @Query("SELECT a FROM GroupInfo a " +
            "WHERE (:#{#search.groupId} IS NULL OR a.groupId = :#{#search.groupId}) " +
            "AND   (:#{#search.status} IS NULL OR a.status = :#{#search.status}) " +
            "AND   (:#{#search.groupName} IS NULL OR a.groupName LIKE :#{#search.groupName}) ")
    List<GroupInfo> find(@Param("search") SearchGroupRequest search);
}