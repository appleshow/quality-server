package com.aps.quality.repository;

import com.aps.quality.entity.UserOrganizationInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserOrganizationInfoRepository extends JpaRepository<UserOrganizationInfo, Integer>,
        JpaSpecificationExecutor<UserOrganizationInfo>,
        PagingAndSortingRepository<UserOrganizationInfo, Integer> {

    Optional<Integer> countByOrganizationId(Integer organizationId);

    Optional<List<UserOrganizationInfo>> findByUserId(Integer userId);

    Optional<List<UserOrganizationInfo>> findByOrganizationId(Integer organizationId);
}