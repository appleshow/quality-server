package com.aps.quality.repository;

import com.aps.quality.entity.OrganizationMappingInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrganizationMappingInfoRepository extends JpaRepository<OrganizationMappingInfo, Integer>,
        JpaSpecificationExecutor<OrganizationMappingInfo>,
        PagingAndSortingRepository<OrganizationMappingInfo, Integer> {

    Optional<Integer> countByFatherOrganizationIdAndFlag(Integer fatherOrganizationId, String flag);

    Optional<List<OrganizationMappingInfo>> findByFatherOrganizationId(Integer fatherOrganizationId);

    Optional<List<OrganizationMappingInfo>> findByChildOrganizationId(Integer childOrganizationId);

    @Query("SELECT a FROM OrganizationMappingInfo a " +
            "WHERE a.childOrganizationId = :childOrganizationId" +
            "  AND a.flag = :flag " +
            "ORDER BY a.fatherOrganizationId DESC")
    Optional<List<OrganizationMappingInfo>> findByChildOrganizationIdAndFlag(Integer childOrganizationId, String flag);
}