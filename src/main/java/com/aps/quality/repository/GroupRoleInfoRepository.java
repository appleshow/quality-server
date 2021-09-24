package com.aps.quality.repository;

import com.aps.quality.entity.GroupRoleInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GroupRoleInfoRepository extends JpaRepository<GroupRoleInfo, Integer>,
        JpaSpecificationExecutor<GroupRoleInfo>,
        PagingAndSortingRepository<GroupRoleInfo, Integer> {

    Optional<Integer> countByGroupIdAndRoleId(Integer groupId, Integer roleId);

    List<GroupRoleInfo> findByRoleId(Integer roleId);

    List<GroupRoleInfo> findByGroupId(Integer groupId);

    List<GroupRoleInfo> findByGroupIdAndRoleId(Integer groupId, Integer roleId);
}