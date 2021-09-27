package com.aps.quality.repository;

import com.aps.quality.entity.CampaignInfo;
import com.aps.quality.model.campaign.SearchCampaignRequest;
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
public interface CampaignInfoRepository extends JpaRepository<CampaignInfo, Integer>,
        JpaSpecificationExecutor<CampaignInfo>,
        PagingAndSortingRepository<CampaignInfo, Integer> {

    @Query("SELECT COUNT(a.campaignId) FROM CampaignInfo a " +
            "WHERE a.campaignId <> :campaignId " +
            "  AND (a.campaignName = :campaignName)")
    Optional<Integer> countByName(@Param("campaignId") Integer campaignId, @Param("campaignName") String campaignName);

    @Query("SELECT a FROM CampaignInfo a " +
            "WHERE (:#{#search.campaignId} IS NULL OR a.campaignId = :#{#search.campaignId}) " +
            "  AND (:#{#search.campaignName} IS NULL OR lower(a.campaignName) LIKE :#{#search.campaignName}) " +
            "  AND (:#{#search.sponsor} IS NULL OR a.sponsor LIKE :#{#search.sponsor}) " +
            "  AND (:#{#search.campaignType} IS NULL OR a.campaignType = :#{#search.campaignType}) " +
            "  AND (:#{#search.campaignStart} IS NULL OR a.campaignStart >= :#{#search.campaignStart}) " +
            "  AND (:#{#search.campaignEnd} IS NULL OR a.campaignEnd <= :#{#search.campaignEnd}) " +
            "  AND (:#{#search.flag} IS NULL OR a.flag = :#{#search.flag}) " +
            "  AND (:#{#search.status} IS NULL OR a.status = :#{#search.status}) ")
    Page<CampaignInfo> findPageable(@Param("search") SearchCampaignRequest search, Pageable pageable);

    @Query("SELECT a FROM CampaignInfo a " +
            "WHERE (:#{#search.campaignId} IS NULL OR a.campaignId = :#{#search.campaignId}) " +
            "  AND (:#{#search.campaignName} IS NULL OR lower(a.campaignName) LIKE :#{#search.campaignName}) " +
            "  AND (:#{#search.sponsor} IS NULL OR a.sponsor LIKE :#{#search.sponsor}) " +
            "  AND (:#{#search.campaignType} IS NULL OR a.campaignType = :#{#search.campaignType}) " +
            "  AND (:#{#search.campaignStart} IS NULL OR a.campaignStart >= :#{#search.campaignStart}) " +
            "  AND (:#{#search.campaignEnd} IS NULL OR a.campaignEnd <= :#{#search.campaignEnd}) " +
            "  AND (:#{#search.flag} IS NULL OR a.flag = :#{#search.flag}) " +
            "  AND (:#{#search.status} IS NULL OR a.status = :#{#search.status}) ")
    List<CampaignInfo> find(@Param("search") SearchCampaignRequest search);
}