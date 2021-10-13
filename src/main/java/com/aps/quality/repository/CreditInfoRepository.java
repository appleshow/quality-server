package com.aps.quality.repository;

import com.aps.quality.entity.CreditInfo;
import com.aps.quality.model.credit.CreditReport;
import com.aps.quality.model.credit.SearchCreditRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CreditInfoRepository extends JpaRepository<CreditInfo, Integer>,
        JpaSpecificationExecutor<CreditInfo>,
        PagingAndSortingRepository<CreditInfo, Integer> {

    Optional<Integer> countByUserId(Integer userId);

    @Query(nativeQuery = true,
            value = "SELECT a.* FROM ( " +
                    "  SELECT UUID() AS guid, " +
                    "         1 AS total, " +
                    "         IF(ci.status<20,0,1) AS submit, " +
                    "         IF(ci.status<40,0,1) AS approve, " +
                    "         ci.atr5 AS certificate, " +
                    //"         NULL AS creditIds, " +
                    "         ci.credit_id AS creditId, " +
                    "         ci.campaign_name AS campaignName, " +
                    "         ci.campaign_type AS campaignType, " +
                    "         ci.user_id AS userId, " +
                    "         ui.user_code AS userCode, " +
                    "         ui.user_name AS userName, " +
                    "         ui.user_gender AS userGender, " +
                    "         ui.user_phone AS userPhone, " +
                    "         ui.organization_id AS organizationId, " +
                    "         ui.atr1 AS userOrganizationLink, " +
                    "         ci.credit AS credit, " +
                    "         ci.credit_time AS creditTime, " +
                    "         ci.instructor AS instructor, " +
                    "         ci.atr1 AS rejectReason, " +
                    "         ci.atr2 AS rejectBy, " +
                    "         ci.atr3 AS rejectUserType, " +
                    "         ci.remark AS remark, " +
                    "         ci.status AS status, " +
                    "         ci.version AS version, " +
                    "         ci.create_by AS createByUserCode, " +
                    "         uc.user_name AS createBy, " +
                    "         uc.atr1 AS createUserOrganizationLink, " +
                    "         ci.create_time AS createTime" +
                    "  FROM credit_info ci " +
                    "    INNER JOIN user_info ui ON ci.user_id = ui.user_id " +
                    "    INNER JOIN user_info uc ON ci.create_by = uc.user_code " +
                    "  WHERE IF(ISNULL(:#{#search.status}),          1,ci.status = :#{#search.status}) " +
                    "    AND IF(ISNULL(:#{#search.statusFrom}),      1,ci.status >= :#{#search.statusFrom}) " +
                    "    AND IF(ISNULL(:#{#search.statusTo}),        1,ci.status <= :#{#search.statusTo}) " +
                    "    AND IF(ISNULL(:#{#search.userId}),          1,ci.user_id = :#{#search.userId}) " +
                    "    AND IF(ISNULL(:#{#search.userCode}),        1,ui.user_code = :#{#search.userCode}) " +
                    "    AND IF(ISNULL(:#{#search.userName}),        1,ui.user_name LIKE :#{#search.userName}) " +
                    "    AND IF(ISNULL(:#{#search.userGender}),      1,ui.user_gender = :#{#search.userGender}) " +
                    "    AND IF(ISNULL(:#{#search.campaignType}),    1,ci.campaign_type = :#{#search.campaignType}) " +
                    "    AND IF(ISNULL(:#{#search.campaignName}),    1,ci.campaign_name LIKE :#{#search.campaignName}) " +
                    "    AND IF(ISNULL(:#{#search.instructor}),      1,ci.instructor = :#{#search.instructor}) " +
                    "    AND IF(ISNULL(:#{#search.creditTimeFrom}),  1,ci.create_time >= :#{#search.creditTimeFrom}) " +
                    "    AND IF(ISNULL(:#{#search.creditTimeTo}),    1,ci.create_time <= :#{#search.creditTimeTo}) " +
                    "    AND IF(ISNULL(:#{#search.createBy}),        1,ci.create_by = :#{#search.createBy}) " +
                    "    AND IF(:#{#search.ignoreOrganizationIds},   1,ui.organization_id IN :#{#search.organizationIds}) " +
                    ") a",
            countQuery = "SELECT COUNT(a.creditId) FROM ( " +
                    "  SELECT ci.credit_id AS creditId " +
                    "  FROM credit_info ci " +
                    "    INNER JOIN user_info ui ON ci.user_id = ui.user_id " +
                    "    INNER JOIN user_info uc ON ci.create_by = uc.user_code " +
                    "  WHERE IF(ISNULL(:#{#search.status}),          1,ci.status = :#{#search.status}) " +
                    "    AND IF(ISNULL(:#{#search.statusFrom}),      1,ci.status >= :#{#search.statusFrom}) " +
                    "    AND IF(ISNULL(:#{#search.statusTo}),        1,ci.status <= :#{#search.statusTo}) " +
                    "    AND IF(ISNULL(:#{#search.userId}),          1,ci.user_id = :#{#search.userId}) " +
                    "    AND IF(ISNULL(:#{#search.userCode}),        1,ui.user_code = :#{#search.userCode}) " +
                    "    AND IF(ISNULL(:#{#search.userName}),        1,ui.user_name LIKE :#{#search.userName}) " +
                    "    AND IF(ISNULL(:#{#search.userGender}),      1,ui.user_gender = :#{#search.userGender}) " +
                    "    AND IF(ISNULL(:#{#search.campaignType}),    1,ci.campaign_type = :#{#search.campaignType}) " +
                    "    AND IF(ISNULL(:#{#search.campaignName}),    1,ci.campaign_name LIKE :#{#search.campaignName}) " +
                    "    AND IF(ISNULL(:#{#search.instructor}),      1,ci.instructor = :#{#search.instructor}) " +
                    "    AND IF(ISNULL(:#{#search.creditTimeFrom}),  1,ci.create_time >= :#{#search.creditTimeFrom}) " +
                    "    AND IF(ISNULL(:#{#search.creditTimeTo}),    1,ci.create_time <= :#{#search.creditTimeTo}) " +
                    "    AND IF(ISNULL(:#{#search.createBy}),        1,ci.create_by = :#{#search.createBy}) " +
                    "    AND IF(:#{#search.ignoreOrganizationIds},   1,ui.organization_id IN :#{#search.organizationIds}) " +
                    ") a")
    Page<CreditReport> findPageable(@Param("search") SearchCreditRequest search, Pageable pageable);

    @Query(nativeQuery = true,
            value = "SELECT a.* FROM ( " +
                    "  SELECT UUID() AS guid, " +
                    "         1 AS total, " +
                    "         IF(ci.status<20,0,1) AS submit, " +
                    "         IF(ci.status<40,0,1) AS approve, " +
                    "         ci.atr5 AS certificate, " +
                    //"         NULL AS creditIds, " +
                    "         ci.credit_id AS creditId, " +
                    "         ci.campaign_name AS campaignName, " +
                    "         ci.campaign_type AS campaignType, " +
                    "         ci.user_id AS userId, " +
                    "         ui.user_code AS userCode, " +
                    "         ui.user_name AS userName, " +
                    "         ui.user_gender AS userGender, " +
                    "         ui.user_phone AS userPhone, " +
                    "         ui.organization_id AS organizationId, " +
                    "         ui.atr1 AS userOrganizationLink, " +
                    "         ci.credit AS credit, " +
                    "         ci.credit_time AS creditTime, " +
                    "         ci.instructor AS instructor, " +
                    "         ci.atr1 AS rejectReason, " +
                    "         ci.atr2 AS rejectBy, " +
                    "         ci.atr3 AS rejectUserType, " +
                    "         ci.remark AS remark, " +
                    "         ci.status AS status, " +
                    "         ci.version AS version, " +
                    "         ci.create_by AS createByUserCode, " +
                    "         uc.user_name AS createBy, " +
                    "         uc.atr1 AS createUserOrganizationLink, " +
                    "         ci.create_time AS createTime" +
                    "  FROM credit_info ci " +
                    "    INNER JOIN user_info ui ON ci.user_id = ui.user_id " +
                    "    INNER JOIN user_info uc ON ci.create_by = uc.user_code " +
                    "  WHERE IF(ISNULL(:#{#search.status}),          1,ci.status = :#{#search.status}) " +
                    "    AND IF(ISNULL(:#{#search.statusFrom}),      1,ci.status >= :#{#search.statusFrom}) " +
                    "    AND IF(ISNULL(:#{#search.statusTo}),        1,ci.status <= :#{#search.statusTo}) " +
                    "    AND IF(ISNULL(:#{#search.userId}),          1,ci.user_id = :#{#search.userId}) " +
                    "    AND IF(ISNULL(:#{#search.userCode}),        1,ui.user_code = :#{#search.userCode}) " +
                    "    AND IF(ISNULL(:#{#search.userName}),        1,ui.user_name LIKE :#{#search.userName}) " +
                    "    AND IF(ISNULL(:#{#search.userGender}),      1,ui.user_gender = :#{#search.userGender}) " +
                    "    AND IF(ISNULL(:#{#search.campaignType}),    1,ci.campaign_type = :#{#search.campaignType}) " +
                    "    AND IF(ISNULL(:#{#search.campaignName}),    1,ci.campaign_name LIKE :#{#search.campaignName}) " +
                    "    AND IF(ISNULL(:#{#search.instructor}),      1,ci.instructor = :#{#search.instructor}) " +
                    "    AND IF(ISNULL(:#{#search.creditTimeFrom}),  1,ci.create_time >= :#{#search.creditTimeFrom}) " +
                    "    AND IF(ISNULL(:#{#search.creditTimeTo}),    1,ci.create_time <= :#{#search.creditTimeTo}) " +
                    "    AND IF(ISNULL(:#{#search.createBy}),        1,ci.create_by = :#{#search.createBy}) " +
                    "    AND IF(:#{#search.ignoreOrganizationIds},   1,ui.organization_id IN :#{#search.organizationIds}) " +
                    ") a " +
                    "ORDER BY ?#{#sort}")
    List<CreditReport> find(@Param("search") SearchCreditRequest search, @Param("sort") Sort sort);

    @Query(nativeQuery = true,
            value = "SELECT a.* FROM ( " +
                    "  SELECT UUID() AS guid, " +
                    "         1 AS total, " +
                    "         IF(ci.status<20,0,1) AS submit, " +
                    "         IF(ci.status<40,0,1) AS approve, " +
                    "         ci.atr5 AS certificate, " +
                    //"         NULL AS creditIds, " +
                    "         ci.credit_id AS creditId, " +
                    "         ci.campaign_name AS campaignName, " +
                    "         ci.campaign_type AS campaignType, " +
                    "         ci.user_id AS userId, " +
                    "         ui.user_code AS userCode, " +
                    "         ui.user_name AS userName, " +
                    "         ui.user_gender AS userGender, " +
                    "         ui.user_phone AS userPhone, " +
                    "         ui.organization_id AS organizationId, " +
                    "         ui.atr1 AS userOrganizationLink, " +
                    "         ci.credit AS credit, " +
                    "         ci.credit_time AS creditTime, " +
                    "         ci.instructor AS instructor, " +
                    "         ci.atr1 AS rejectReason, " +
                    "         ci.atr2 AS rejectBy, " +
                    "         ci.atr3 AS rejectUserType, " +
                    "         ci.remark AS remark, " +
                    "         ci.status AS status, " +
                    "         ci.version AS version, " +
                    "         ci.create_by AS createByUserCode, " +
                    "         uc.user_name AS createBy, " +
                    "         uc.atr1 AS createUserOrganizationLink, " +
                    "         ci.create_time AS createTime" +
                    "  FROM credit_info ci " +
                    "    INNER JOIN user_info ui ON ci.user_id = ui.user_id " +
                    "    INNER JOIN user_info uc ON ci.create_by = uc.user_code " +
                    "  WHERE IF(ISNULL(:#{#search.status}),          1,ci.status = :#{#search.status}) " +
                    "    AND IF(ISNULL(:#{#search.statusFrom}),      1,ci.status >= :#{#search.statusFrom}) " +
                    "    AND IF(ISNULL(:#{#search.statusTo}),        1,ci.status <= :#{#search.statusTo}) " +
                    "    AND IF(ISNULL(:#{#search.userId}),          1,ui.user_id = :#{#search.userId}) " +
                    "    AND IF(ISNULL(:#{#search.creditIds}),       1,ci.credit_id IN :#{#search.creditIds}) " +
                    "    AND IF(ISNULL(:#{#search.userCode}),        1,ui.user_code = :#{#search.userCode}) " +
                    "    AND IF(ISNULL(:#{#search.userName}),        1,ui.user_name LIKE :#{#search.userName}) " +
                    "    AND IF(ISNULL(:#{#search.userGender}),      1,ui.user_gender = :#{#search.userGender}) " +
                    "    AND IF(ISNULL(:#{#search.campaignType}),    1,ci.campaign_type = :#{#search.campaignType}) " +
                    "    AND IF(ISNULL(:#{#search.campaignName}),    1,ci.campaign_name LIKE :#{#search.campaignName}) " +
                    "    AND IF(ISNULL(:#{#search.instructor}),      1,ci.instructor = :#{#search.instructor}) " +
                    "    AND IF(ISNULL(:#{#search.creditTimeFrom}),  1,ci.create_time >= :#{#search.creditTimeFrom}) " +
                    "    AND IF(ISNULL(:#{#search.creditTimeTo}),    1,ci.create_time <= :#{#search.creditTimeTo}) " +
                    "    AND IF(ISNULL(:#{#search.createBy}),        1,ci.create_by = :#{#search.createBy}) " +
                    "    AND IF(:#{#search.ignoreOrganizationIds},   1,ui.organization_id IN :#{#search.organizationIds}) " +
                    ") a " +
                    "ORDER BY ?#{#sort}")
    List<CreditReport> findSub(@Param("search") SearchCreditRequest search, @Param("sort") Sort sort);

    @Query(nativeQuery = true,
            value = "SELECT a.* FROM ( " +
                    "  SELECT UUID() AS guid, " +
                    "         COUNT(ci.credit_id) AS total, " +
                    "         SUM(IF(ci.status<20,0,1)) AS submit, " +
                    "         SUM(IF(ci.status<40,0,1)) AS approve, " +
                    "         GROUP_CONCAT(ci.credit_id) AS creditIds, " +
                    "         SUM(IF(ci.atr5 IS NULL,0,ci.atr5)) AS certificate, " +
                    "         NULL AS creditId, " +
                    "         ci.campaign_name AS campaignName, " +
                    "         ci.campaign_type AS campaignType, " +
                    "         NULL AS userId, " +
                    "         NULL AS userCode, " +
                    "         NULL AS userName, " +
                    "         NULL AS userGender, " +
                    "         NULL AS userPhone, " +
                    "         NULL AS organizationId, " +
                    "         NULL AS userOrganizationLink, " +
                    "         SUM(ci.credit) AS credit, " +
                    "         MIN(ci.credit_time)  AS creditTime, " +
                    "         NULL AS instructor, " +
                    "         NULL AS rejectReason, " +
                    "         NULL AS rejectBy, " +
                    "         NULL AS rejectUserType, " +
                    "         NULL AS remark, " +
                    "         NULL AS status, " +
                    "         NULL AS version, " +
                    "         NULL AS createByUserCode, " +
                    "         NULL AS createBy, " +
                    "         NULL AS createUserOrganizationLink, " +
                    "         NULL AS createTime" +
                    "  FROM credit_info ci " +
                    "    INNER JOIN user_info ui ON ci.user_id = ui.user_id " +
                    "    INNER JOIN user_info uc ON ci.create_by = uc.user_code " +
                    "  WHERE IF(ISNULL(:#{#search.status}),          1,ci.status = :#{#search.status}) " +
                    "    AND IF(ISNULL(:#{#search.statusFrom}),      1,ci.status >= :#{#search.statusFrom}) " +
                    "    AND IF(ISNULL(:#{#search.statusTo}),        1,ci.status <= :#{#search.statusTo}) " +
                    "    AND IF(ISNULL(:#{#search.userId}),          1,ci.user_id = :#{#search.userId}) " +
                    "    AND IF(ISNULL(:#{#search.userCode}),        1,ui.user_code = :#{#search.userCode}) " +
                    "    AND IF(ISNULL(:#{#search.userName}),        1,ui.user_name LIKE :#{#search.userName}) " +
                    "    AND IF(ISNULL(:#{#search.userGender}),      1,ui.user_gender = :#{#search.userGender}) " +
                    "    AND IF(ISNULL(:#{#search.campaignType}),    1,ci.campaign_type = :#{#search.campaignType}) " +
                    "    AND IF(ISNULL(:#{#search.campaignName}),    1,ci.campaign_name LIKE :#{#search.campaignName}) " +
                    "    AND IF(ISNULL(:#{#search.instructor}),      1,ci.instructor = :#{#search.instructor}) " +
                    "    AND IF(ISNULL(:#{#search.creditTimeFrom}),  1,ci.create_time >= :#{#search.creditTimeFrom}) " +
                    "    AND IF(ISNULL(:#{#search.creditTimeTo}),    1,ci.create_time <= :#{#search.creditTimeTo}) " +
                    "    AND IF(ISNULL(:#{#search.createBy}),        1,ci.create_by = :#{#search.createBy}) " +
                    "    AND IF(:#{#search.ignoreOrganizationIds},   1,ui.organization_id IN :#{#search.organizationIds}) " +
                    "  GROUP BY ci.campaign_type, ci.campaign_name" +
                    ") a",
            countQuery = "SELECT COUNT(a.creditId) FROM ( " +
                    "  SELECT 0 AS creditId " +
                    "  FROM credit_info ci " +
                    "    INNER JOIN user_info ui ON ci.user_id = ui.user_id " +
                    "    INNER JOIN user_info uc ON ci.create_by = uc.user_code " +
                    "  WHERE IF(ISNULL(:#{#search.status}),          1,ci.status = :#{#search.status}) " +
                    "    AND IF(ISNULL(:#{#search.statusFrom}),      1,ci.status >= :#{#search.statusFrom}) " +
                    "    AND IF(ISNULL(:#{#search.statusTo}),        1,ci.status <= :#{#search.statusTo}) " +
                    "    AND IF(ISNULL(:#{#search.userId}),          1,ci.user_id = :#{#search.userId}) " +
                    "    AND IF(ISNULL(:#{#search.userCode}),        1,ui.user_code = :#{#search.userCode}) " +
                    "    AND IF(ISNULL(:#{#search.userName}),        1,ui.user_name LIKE :#{#search.userName}) " +
                    "    AND IF(ISNULL(:#{#search.userGender}),      1,ui.user_gender = :#{#search.userGender}) " +
                    "    AND IF(ISNULL(:#{#search.campaignType}),    1,ci.campaign_type = :#{#search.campaignType}) " +
                    "    AND IF(ISNULL(:#{#search.campaignName}),    1,ci.campaign_name LIKE :#{#search.campaignName}) " +
                    "    AND IF(ISNULL(:#{#search.instructor}),      1,ci.instructor = :#{#search.instructor}) " +
                    "    AND IF(ISNULL(:#{#search.creditTimeFrom}),  1,ci.create_time >= :#{#search.creditTimeFrom}) " +
                    "    AND IF(ISNULL(:#{#search.creditTimeTo}),    1,ci.create_time <= :#{#search.creditTimeTo}) " +
                    "    AND IF(ISNULL(:#{#search.createBy}),        1,ci.create_by = :#{#search.createBy}) " +
                    "    AND IF(:#{#search.ignoreOrganizationIds},   1,ui.organization_id IN :#{#search.organizationIds}) " +
                    "  GROUP BY ci.campaign_type, ci.campaign_name" +
                    ") a")
    Page<CreditReport> findByCampaignGroupPageable(@Param("search") SearchCreditRequest search, Pageable pageable);

    @Query(nativeQuery = true,
            value = "SELECT a.* FROM ( " +
                    "  SELECT UUID() AS guid, " +
                    "         COUNT(ci.credit_id) AS total, " +
                    "         SUM(IF(ci.status<20,0,1)) AS submit, " +
                    "         SUM(IF(ci.status<40,0,1)) AS approve, " +
                    "         GROUP_CONCAT(ci.credit_id) AS creditIds, " +
                    "         SUM(IF(ci.atr5 IS NULL,0,ci.atr5)) AS certificate, " +
                    "         NULL AS creditId, " +
                    "         ci.campaign_name AS campaignName, " +
                    "         ci.campaign_type AS campaignType, " +
                    "         NULL AS userId, " +
                    "         NULL AS userCode, " +
                    "         NULL AS userName, " +
                    "         NULL AS userGender, " +
                    "         NULL AS userPhone, " +
                    "         NULL AS organizationId, " +
                    "         NULL AS userOrganizationLink, " +
                    "         SUM(ci.credit) AS credit, " +
                    "         MIN(ci.credit_time)  AS creditTime, " +
                    "         NULL AS instructor, " +
                    "         NULL AS rejectReason, " +
                    "         NULL AS rejectBy, " +
                    "         NULL AS rejectUserType, " +
                    "         NULL AS remark, " +
                    "         NULL AS status, " +
                    "         NULL AS version, " +
                    "         NULL AS createByUserCode, " +
                    "         NULL AS createBy, " +
                    "         NULL AS createUserOrganizationLink, " +
                    "         NULL AS createTime" +
                    "  FROM credit_info ci " +
                    "    INNER JOIN user_info ui ON ci.user_id = ui.user_id " +
                    "    INNER JOIN user_info uc ON ci.create_by = uc.user_code " +
                    "  WHERE IF(ISNULL(:#{#search.status}),          1,ci.status = :#{#search.status}) " +
                    "    AND IF(ISNULL(:#{#search.statusFrom}),      1,ci.status >= :#{#search.statusFrom}) " +
                    "    AND IF(ISNULL(:#{#search.statusTo}),        1,ci.status <= :#{#search.statusTo}) " +
                    "    AND IF(ISNULL(:#{#search.userId}),          1,ci.user_id = :#{#search.userId}) " +
                    "    AND IF(ISNULL(:#{#search.userCode}),        1,ui.user_code = :#{#search.userCode}) " +
                    "    AND IF(ISNULL(:#{#search.userName}),        1,ui.user_name LIKE :#{#search.userName}) " +
                    "    AND IF(ISNULL(:#{#search.userGender}),      1,ui.user_gender = :#{#search.userGender}) " +
                    "    AND IF(ISNULL(:#{#search.campaignType}),    1,ci.campaign_type = :#{#search.campaignType}) " +
                    "    AND IF(ISNULL(:#{#search.campaignName}),    1,ci.campaign_name LIKE :#{#search.campaignName}) " +
                    "    AND IF(ISNULL(:#{#search.instructor}),      1,ci.instructor = :#{#search.instructor}) " +
                    "    AND IF(ISNULL(:#{#search.creditTimeFrom}),  1,ci.create_time >= :#{#search.creditTimeFrom}) " +
                    "    AND IF(ISNULL(:#{#search.creditTimeTo}),    1,ci.create_time <= :#{#search.creditTimeTo}) " +
                    "    AND IF(ISNULL(:#{#search.createBy}),        1,ci.create_by = :#{#search.createBy}) " +
                    "    AND IF(:#{#search.ignoreOrganizationIds},   1,ui.organization_id IN :#{#search.organizationIds}) " +
                    "  GROUP BY ci.campaign_type, ci.campaign_name" +
                    ") a " +
                    "ORDER BY ?#{#sort}")
    List<CreditReport> findByCampaignGroup(@Param("search") SearchCreditRequest search, @Param("sort") Sort sort);

    @Query(nativeQuery = true,
            value = "SELECT a.* FROM ( " +
                    "  SELECT UUID() AS guid, " +
                    "         COUNT(ci.credit_id) AS total, " +
                    "         SUM(IF(ci.status<20,0,1)) AS submit, " +
                    "         SUM(IF(ci.status<40,0,1)) AS approve, " +
                    "         GROUP_CONCAT(ci.credit_id) AS creditIds, " +
                    "         SUM(IF(ci.atr5 IS NULL,0,ci.atr5)) AS certificate, " +
                    "         NULL AS creditId, " +
                    "         NULL AS campaignName, " +
                    "         NULL AS campaignType, " +
                    "         MIN(ci.user_id) AS userId, " +
                    "         MIN(ui.user_code) AS userCode, " +
                    "         MIN(ui.user_name) AS userName, " +
                    "         MIN(ui.user_gender) AS userGender, " +
                    "         MIN(ui.user_phone) AS userPhone, " +
                    "         MIN(ui.organization_id) AS organizationId, " +
                    "         MIN(ui.atr1) AS userOrganizationLink, " +
                    "         SUM(ci.credit) AS credit, " +
                    "         NULL  AS creditTime, " +
                    "         NULL AS instructor, " +
                    "         NULL AS rejectReason, " +
                    "         NULL AS rejectBy, " +
                    "         NULL AS rejectUserType, " +
                    "         NULL AS remark, " +
                    "         NULL AS status, " +
                    "         NULL AS version, " +
                    "         NULL AS createByUserCode, " +
                    "         NULL AS createBy, " +
                    "         NULL AS createUserOrganizationLink, " +
                    "         NULL AS createTime" +
                    "  FROM credit_info ci " +
                    "    INNER JOIN user_info ui ON ci.user_id = ui.user_id " +
                    "    INNER JOIN user_info uc ON ci.create_by = uc.user_code " +
                    "  WHERE IF(ISNULL(:#{#search.status}),          1,ci.status = :#{#search.status}) " +
                    "    AND IF(ISNULL(:#{#search.statusFrom}),      1,ci.status >= :#{#search.statusFrom}) " +
                    "    AND IF(ISNULL(:#{#search.statusTo}),        1,ci.status <= :#{#search.statusTo}) " +
                    "    AND IF(ISNULL(:#{#search.userId}),          1,ci.user_id = :#{#search.userId}) " +
                    "    AND IF(ISNULL(:#{#search.userCode}),        1,ui.user_code = :#{#search.userCode}) " +
                    "    AND IF(ISNULL(:#{#search.userName}),        1,ui.user_name LIKE :#{#search.userName}) " +
                    "    AND IF(ISNULL(:#{#search.userGender}),      1,ui.user_gender = :#{#search.userGender}) " +
                    "    AND IF(ISNULL(:#{#search.campaignType}),    1,ci.campaign_type = :#{#search.campaignType}) " +
                    "    AND IF(ISNULL(:#{#search.campaignName}),    1,ci.campaign_name LIKE :#{#search.campaignName}) " +
                    "    AND IF(ISNULL(:#{#search.instructor}),      1,ci.instructor = :#{#search.instructor}) " +
                    "    AND IF(ISNULL(:#{#search.creditTimeFrom}),  1,ci.create_time >= :#{#search.creditTimeFrom}) " +
                    "    AND IF(ISNULL(:#{#search.creditTimeTo}),    1,ci.create_time <= :#{#search.creditTimeTo}) " +
                    "    AND IF(ISNULL(:#{#search.createBy}),        1,ci.create_by = :#{#search.createBy}) " +
                    "    AND IF(:#{#search.ignoreOrganizationIds},   1,ui.organization_id IN :#{#search.organizationIds}) " +
                    "  GROUP BY ci.user_id" +
                    ") a",
            countQuery = "SELECT COUNT(a.creditId) FROM ( " +
                    "  SELECT 0 AS creditId " +
                    "  FROM credit_info ci " +
                    "    INNER JOIN user_info ui ON ci.user_id = ui.user_id " +
                    "    INNER JOIN user_info uc ON ci.create_by = uc.user_code " +
                    "  WHERE IF(ISNULL(:#{#search.status}),          1,ci.status = :#{#search.status}) " +
                    "    AND IF(ISNULL(:#{#search.statusFrom}),      1,ci.status >= :#{#search.statusFrom}) " +
                    "    AND IF(ISNULL(:#{#search.statusTo}),        1,ci.status <= :#{#search.statusTo}) " +
                    "    AND IF(ISNULL(:#{#search.userId}),          1,ci.user_id = :#{#search.userId}) " +
                    "    AND IF(ISNULL(:#{#search.userCode}),        1,ui.user_code = :#{#search.userCode}) " +
                    "    AND IF(ISNULL(:#{#search.userName}),        1,ui.user_name LIKE :#{#search.userName}) " +
                    "    AND IF(ISNULL(:#{#search.userGender}),      1,ui.user_gender = :#{#search.userGender}) " +
                    "    AND IF(ISNULL(:#{#search.campaignType}),    1,ci.campaign_type = :#{#search.campaignType}) " +
                    "    AND IF(ISNULL(:#{#search.campaignName}),    1,ci.campaign_name LIKE :#{#search.campaignName}) " +
                    "    AND IF(ISNULL(:#{#search.instructor}),      1,ci.instructor = :#{#search.instructor}) " +
                    "    AND IF(ISNULL(:#{#search.creditTimeFrom}),  1,ci.create_time >= :#{#search.creditTimeFrom}) " +
                    "    AND IF(ISNULL(:#{#search.creditTimeTo}),    1,ci.create_time <= :#{#search.creditTimeTo}) " +
                    "    AND IF(ISNULL(:#{#search.createBy}),        1,ci.create_by = :#{#search.createBy}) " +
                    "    AND IF(:#{#search.ignoreOrganizationIds},   1,ui.organization_id IN :#{#search.organizationIds}) " +
                    "  GROUP BY ci.user_id" +
                    ") a")
    Page<CreditReport> findByUserGroupPageable(@Param("search") SearchCreditRequest search, Pageable pageable);

    @Query(nativeQuery = true,
            value = "SELECT a.* FROM ( " +
                    "  SELECT UUID() AS guid, " +
                    "         COUNT(ci.credit_id) AS total, " +
                    "         SUM(IF(ci.status<20,0,1)) AS submit, " +
                    "         SUM(IF(ci.status<40,0,1)) AS approve, " +
                    "         GROUP_CONCAT(ci.credit_id) AS creditIds, " +
                    "         SUM(IF(ci.atr5 IS NULL,0,ci.atr5)) AS certificate, " +
                    "         NULL AS creditId, " +
                    "         NULL AS campaignName, " +
                    "         NULL AS campaignType, " +
                    "         MIN(ci.user_id) AS userId, " +
                    "         MIN(ui.user_code) AS userCode, " +
                    "         MIN(ui.user_name) AS userName, " +
                    "         MIN(ui.user_gender) AS userGender, " +
                    "         MIN(ui.user_phone) AS userPhone, " +
                    "         MIN(ui.organization_id) AS organizationId, " +
                    "         MIN(ui.atr1) AS userOrganizationLink, " +
                    "         SUM(ci.credit) AS credit, " +
                    "         NULL  AS creditTime, " +
                    "         NULL AS instructor, " +
                    "         NULL AS rejectReason, " +
                    "         NULL AS rejectBy, " +
                    "         NULL AS rejectUserType, " +
                    "         NULL AS remark, " +
                    "         NULL AS status, " +
                    "         NULL AS version, " +
                    "         NULL AS createByUserCode, " +
                    "         NULL AS createBy, " +
                    "         NULL AS createUserOrganizationLink, " +
                    "         NULL AS createTime" +
                    "  FROM credit_info ci " +
                    "    INNER JOIN user_info ui ON ci.user_id = ui.user_id " +
                    "    INNER JOIN user_info uc ON ci.create_by = uc.user_code " +
                    "  WHERE IF(ISNULL(:#{#search.status}),          1,ci.status = :#{#search.status}) " +
                    "    AND IF(ISNULL(:#{#search.statusFrom}),      1,ci.status >= :#{#search.statusFrom}) " +
                    "    AND IF(ISNULL(:#{#search.statusTo}),        1,ci.status <= :#{#search.statusTo}) " +
                    "    AND IF(ISNULL(:#{#search.userId}),          1,ci.user_id = :#{#search.userId}) " +
                    "    AND IF(ISNULL(:#{#search.userCode}),        1,ui.user_code = :#{#search.userCode}) " +
                    "    AND IF(ISNULL(:#{#search.userName}),        1,ui.user_name LIKE :#{#search.userName}) " +
                    "    AND IF(ISNULL(:#{#search.userGender}),      1,ui.user_gender = :#{#search.userGender}) " +
                    "    AND IF(ISNULL(:#{#search.campaignType}),    1,ci.campaign_type = :#{#search.campaignType}) " +
                    "    AND IF(ISNULL(:#{#search.campaignName}),    1,ci.campaign_name LIKE :#{#search.campaignName}) " +
                    "    AND IF(ISNULL(:#{#search.instructor}),      1,ci.instructor = :#{#search.instructor}) " +
                    "    AND IF(ISNULL(:#{#search.creditTimeFrom}),  1,ci.create_time >= :#{#search.creditTimeFrom}) " +
                    "    AND IF(ISNULL(:#{#search.creditTimeTo}),    1,ci.create_time <= :#{#search.creditTimeTo}) " +
                    "    AND IF(ISNULL(:#{#search.createBy}),        1,ci.create_by = :#{#search.createBy}) " +
                    "    AND IF(:#{#search.ignoreOrganizationIds},   1,ui.organization_id IN :#{#search.organizationIds}) " +
                    "  GROUP BY ci.user_id" +
                    ") a " +
                    "ORDER BY ?#{#sort}")
    List<CreditReport> findByUserGroup(@Param("search") SearchCreditRequest search, @Param("sort") Sort sort);

    @Query(nativeQuery = true,
            value = "SELECT a.* FROM ( " +
                    "  SELECT UUID() AS guid, " +
                    "         COUNT(ci.credit_id) AS total, " +
                    "         SUM(IF(ci.status<20,0,1)) AS submit, " +
                    "         SUM(IF(ci.status<40,0,1)) AS approve, " +
                    "         GROUP_CONCAT(ci.credit_id) AS creditIds, " +
                    "         SUM(IF(ci.atr5 IS NULL,0,ci.atr5)) AS certificate, " +
                    "         NULL AS creditId, " +
                    "         ci.campaign_name AS campaignName, " +
                    "         ci.campaign_type AS campaignType, " +
                    "         MIN(ui.user_id) AS userId, " +
                    "         MIN(ui.user_code) AS userCode, " +
                    "         MIN(ui.user_name) AS userName, " +
                    "         MIN(ui.user_gender) AS userGender, " +
                    "         MIN(ui.user_phone) AS userPhone, " +
                    "         MIN(ui.organization_id) AS organizationId, " +
                    "         MIN(ui.atr1) AS userOrganizationLink, " +
                    "         SUM(ci.credit) AS credit, " +
                    "         NULL  AS creditTime, " +
                    "         NULL AS instructor, " +
                    "         NULL AS rejectReason, " +
                    "         NULL AS rejectBy, " +
                    "         NULL AS rejectUserType, " +
                    "         NULL AS remark, " +
                    "         NULL AS status, " +
                    "         NULL AS version, " +
                    "         NULL AS createByUserCode, " +
                    "         NULL AS createBy, " +
                    "         NULL AS createUserOrganizationLink, " +
                    "         NULL AS createTime" +
                    "  FROM credit_info ci " +
                    "    INNER JOIN user_info ui ON ci.user_id = ui.user_id " +
                    "    INNER JOIN user_info uc ON ci.create_by = uc.user_code " +
                    "  WHERE IF(ISNULL(:#{#search.status}),          1,ci.status = :#{#search.status}) " +
                    "    AND IF(ISNULL(:#{#search.statusFrom}),      1,ci.status >= :#{#search.statusFrom}) " +
                    "    AND IF(ISNULL(:#{#search.statusTo}),        1,ci.status <= :#{#search.statusTo}) " +
                    "    AND IF(ISNULL(:#{#search.userId}),          1,ci.user_id = :#{#search.userId}) " +
                    "    AND IF(ISNULL(:#{#search.userCode}),        1,ui.user_code = :#{#search.userCode}) " +
                    "    AND IF(ISNULL(:#{#search.userName}),        1,ui.user_name LIKE :#{#search.userName}) " +
                    "    AND IF(ISNULL(:#{#search.userGender}),      1,ui.user_gender = :#{#search.userGender}) " +
                    "    AND IF(ISNULL(:#{#search.campaignType}),    1,ci.campaign_type = :#{#search.campaignType}) " +
                    "    AND IF(ISNULL(:#{#search.campaignName}),    1,ci.campaign_name LIKE :#{#search.campaignName}) " +
                    "    AND IF(ISNULL(:#{#search.instructor}),      1,ci.instructor = :#{#search.instructor}) " +
                    "    AND IF(ISNULL(:#{#search.creditTimeFrom}),  1,ci.create_time >= :#{#search.creditTimeFrom}) " +
                    "    AND IF(ISNULL(:#{#search.creditTimeTo}),    1,ci.create_time <= :#{#search.creditTimeTo}) " +
                    "    AND IF(ISNULL(:#{#search.createBy}),        1,ci.create_by = :#{#search.createBy}) " +
                    "    AND IF(:#{#search.ignoreOrganizationIds},   1,ui.organization_id IN :#{#search.organizationIds}) " +
                    "  GROUP BY ci.campaign_type,ci.campaign_name, ci.user_id" +
                    ") a",
            countQuery = "SELECT COUNT(a.creditId) FROM ( " +
                    "  SELECT 0 AS creditId " +
                    "  FROM credit_info ci " +
                    "    INNER JOIN user_info ui ON ci.user_id = ui.user_id " +
                    "    INNER JOIN user_info uc ON ci.create_by = uc.user_code " +
                    "  WHERE IF(ISNULL(:#{#search.status}),          1,ci.status = :#{#search.status}) " +
                    "    AND IF(ISNULL(:#{#search.statusFrom}),      1,ci.status >= :#{#search.statusFrom}) " +
                    "    AND IF(ISNULL(:#{#search.statusTo}),        1,ci.status <= :#{#search.statusTo}) " +
                    "    AND IF(ISNULL(:#{#search.userId}),          1,ci.user_id = :#{#search.userId}) " +
                    "    AND IF(ISNULL(:#{#search.userCode}),        1,ui.user_code = :#{#search.userCode}) " +
                    "    AND IF(ISNULL(:#{#search.userName}),        1,ui.user_name LIKE :#{#search.userName}) " +
                    "    AND IF(ISNULL(:#{#search.userGender}),      1,ui.user_gender = :#{#search.userGender}) " +
                    "    AND IF(ISNULL(:#{#search.campaignType}),    1,ci.campaign_type = :#{#search.campaignType}) " +
                    "    AND IF(ISNULL(:#{#search.campaignName}),    1,ci.campaign_name LIKE :#{#search.campaignName}) " +
                    "    AND IF(ISNULL(:#{#search.instructor}),      1,ci.instructor = :#{#search.instructor}) " +
                    "    AND IF(ISNULL(:#{#search.creditTimeFrom}),  1,ci.create_time >= :#{#search.creditTimeFrom}) " +
                    "    AND IF(ISNULL(:#{#search.creditTimeTo}),    1,ci.create_time <= :#{#search.creditTimeTo}) " +
                    "    AND IF(ISNULL(:#{#search.createBy}),        1,ci.create_by = :#{#search.createBy}) " +
                    "    AND IF(:#{#search.ignoreOrganizationIds},   1,ui.organization_id IN :#{#search.organizationIds}) " +
                    "  GROUP BY ci.campaign_type,ci.campaign_name, ci.user_id" +
                    ") a")
    Page<CreditReport> findByCampaignAndUserGroupPageable(@Param("search") SearchCreditRequest search, Pageable pageable);

    @Query(nativeQuery = true,
            value = "SELECT a.* FROM ( " +
                    "  SELECT UUID() AS guid, " +
                    "         COUNT(ci.credit_id) AS total, " +
                    "         SUM(IF(ci.status<20,0,1)) AS submit, " +
                    "         SUM(IF(ci.status<40,0,1)) AS approve, " +
                    "         GROUP_CONCAT(ci.credit_id) AS creditIds, " +
                    "         SUM(IF(ci.atr5 IS NULL,0,ci.atr5)) AS certificate, " +
                    "         NULL AS creditId, " +
                    "         ci.campaign_name AS campaignName, " +
                    "         ci.campaign_type AS campaignType, " +
                    "         MIN(ui.user_id) AS userId, " +
                    "         MIN(ui.user_code) AS userCode, " +
                    "         MIN(ui.user_name) AS userName, " +
                    "         MIN(ui.user_gender) AS userGender, " +
                    "         MIN(ui.user_phone) AS userPhone, " +
                    "         MIN(ui.organization_id) AS organizationId, " +
                    "         MIN(ui.atr1) AS userOrganizationLink, " +
                    "         SUM(ci.credit) AS credit, " +
                    "         NULL  AS creditTime, " +
                    "         NULL AS instructor, " +
                    "         NULL AS rejectReason, " +
                    "         NULL AS rejectBy, " +
                    "         NULL AS rejectUserType, " +
                    "         NULL AS remark, " +
                    "         NULL AS status, " +
                    "         NULL AS version, " +
                    "         NULL AS createByUserCode, " +
                    "         NULL AS createBy, " +
                    "         NULL AS createUserOrganizationLink, " +
                    "         NULL AS createTime" +
                    "  FROM credit_info ci " +
                    "    INNER JOIN user_info ui ON ci.user_id = ui.user_id " +
                    "    INNER JOIN user_info uc ON ci.create_by = uc.user_code " +
                    "  WHERE IF(ISNULL(:#{#search.status}),          1,ci.status = :#{#search.status}) " +
                    "    AND IF(ISNULL(:#{#search.statusFrom}),      1,ci.status >= :#{#search.statusFrom}) " +
                    "    AND IF(ISNULL(:#{#search.statusTo}),        1,ci.status <= :#{#search.statusTo}) " +
                    "    AND IF(ISNULL(:#{#search.userId}),          1,ci.user_id = :#{#search.userId}) " +
                    "    AND IF(ISNULL(:#{#search.userCode}),        1,ui.user_code = :#{#search.userCode}) " +
                    "    AND IF(ISNULL(:#{#search.userName}),        1,ui.user_name LIKE :#{#search.userName}) " +
                    "    AND IF(ISNULL(:#{#search.userGender}),      1,ui.user_gender = :#{#search.userGender}) " +
                    "    AND IF(ISNULL(:#{#search.campaignType}),    1,ci.campaign_type = :#{#search.campaignType}) " +
                    "    AND IF(ISNULL(:#{#search.campaignName}),    1,ci.campaign_name LIKE :#{#search.campaignName}) " +
                    "    AND IF(ISNULL(:#{#search.instructor}),      1,ci.instructor = :#{#search.instructor}) " +
                    "    AND IF(ISNULL(:#{#search.creditTimeFrom}),  1,ci.create_time >= :#{#search.creditTimeFrom}) " +
                    "    AND IF(ISNULL(:#{#search.creditTimeTo}),    1,ci.create_time <= :#{#search.creditTimeTo}) " +
                    "    AND IF(ISNULL(:#{#search.createBy}),        1,ci.create_by = :#{#search.createBy}) " +
                    "    AND IF(:#{#search.ignoreOrganizationIds},   1,ui.organization_id IN :#{#search.organizationIds}) " +
                    "  GROUP BY ci.campaign_type,ci.campaign_name, ci.user_id" +
                    ") a " +
                    "ORDER BY ?#{#sort}")
    List<CreditReport> findByCampaignAndUserGroup(@Param("search") SearchCreditRequest search, @Param("sort") Sort sort);

}