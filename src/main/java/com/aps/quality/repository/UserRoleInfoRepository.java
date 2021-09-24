package com.aps.quality.repository;

import com.aps.quality.entity.UserRoleInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRoleInfoRepository extends JpaRepository<UserRoleInfo, Integer>,
        JpaSpecificationExecutor<UserRoleInfo>,
        PagingAndSortingRepository<UserRoleInfo, Integer> {

    Optional<Integer> countByUserIdAndRoleId(Integer userId, Integer roleId);

    Optional<List<UserRoleInfo>> findByRoleId(Integer roleId);

    Optional<List<UserRoleInfo>> findByUserId(Integer userId);

    Optional<List<UserRoleInfo>> findByUserIdAndRoleId(Integer userId, Integer roleId);
}