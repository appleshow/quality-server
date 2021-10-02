package com.aps.quality.repository;

import com.aps.quality.entity.CreditInfo;
import com.aps.quality.model.credit.CreditReport;
import com.aps.quality.model.credit.SearchCreditRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CreditInfoRepository extends JpaRepository<CreditInfo, Integer>,
        JpaSpecificationExecutor<CreditInfo>,
        PagingAndSortingRepository<CreditInfo, Integer> {

    @Query(nativeQuery = true,
            value = "SELECT a.* FROM ( " +
                    "  SELECT UUID() AS guid, " +
                    "         1 AS total, " +
                    "         IF(ci.status<20,0,1) AS submit, " +
                    "         ci.atr5 AS certificate, " +
                    "         '' AS creditIds, " +
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
                    "         ci.remark AS remark, " +
                    "         ci.status AS status, " +
                    "         ci.create_by AS createBy, " +
                    "         ci.create_time AS createTime" +
                    "  FROM credit_info ci " +
                    "    INNER JOIN user_info ui ON ci.user_id = ui.user_id " +
                    "    INNER JOIN organization_mapping_info omi ON omi.father_organization_id = :#{#search.organizationId} AND ui.organization_id = omi.child_organization_id " +
                    "  WHERE IF(ISNULL(:#{#search.status}),          1,ci.status = :#{#search.status}) " +
                    "    AND IF(ISNULL(:#{#search.userCode}),        1,ui.user_code = :#{#search.userCode}) " +
                    "    AND IF(ISNULL(:#{#search.userName}),        1,ui.user_name LIKE :#{#search.userName}) " +
                    "    AND IF(ISNULL(:#{#search.userGender}),      1,ui.user_gender = :#{#search.userGender}) " +
                    "    AND IF(ISNULL(:#{#search.campaignType}),    1,ci.campaign_type = :#{#search.campaignType}) " +
                    "    AND IF(ISNULL(:#{#search.campaignName}),    1,ci.campaign_name LIKE :#{#search.campaignName}) " +
                    "    AND IF(ISNULL(:#{#search.instructor}),      1,ci.instructor = :#{#search.instructor}) " +
                    "    AND IF(ISNULL(:#{#search.creditTimeFrom}),  1,ci.create_time >= :#{#search.creditTimeFrom}) " +
                    "    AND IF(ISNULL(:#{#search.creditTimeTo}),    1,ci.create_time <= :#{#search.creditTimeTo}) " +
                    "    AND IF(ISNULL(:#{#search.createBy}),        1,ci.create_by = :#{#search.createBy}) " +
                    ") a")
    List<CreditReport> find(@Param("search") SearchCreditRequest search);

    @Query(nativeQuery = true,
            value = "SELECT a.* FROM ( " +
                    "  SELECT UUID() AS guid, " +
                    "         1 AS total, " +
                    "         IF(ci.status<20,0,1) AS submit, " +
                    "         ci.atr5 AS certificate, " +
                    "         '' AS creditIds, " +
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
                    "         ci.remark AS remark, " +
                    "         ci.status AS status, " +
                    "         ci.create_by AS createBy, " +
                    "         ci.create_time AS createTime" +
                    "  FROM credit_info ci " +
                    "    INNER JOIN user_info ui ON ci.user_id = ui.user_id " +
                    "    INNER JOIN organization_mapping_info omi ON omi.father_organization_id = :#{#search.organizationId} AND ui.organization_id = omi.child_organization_id " +
                    "  WHERE IF(ISNULL(:#{#search.status}),          1,ci.status = :#{#search.status}) " +
                    "    AND IF(ISNULL(:#{#search.userId}),          1,ui.user_id = :#{#search.userId}) " +
                    "    AND IF(ISNULL(:#{#search.campaignTypeFix}), 1,ci.campaign_type = :#{#search.campaignTypeFix}) " +
                    "    AND IF(ISNULL(:#{#search.campaignNameFix}), 1,ci.campaign_name = :#{#search.campaignNameFix}) " +
                    "    AND IF(ISNULL(:#{#search.userCode}),        1,ui.user_code = :#{#search.userCode}) " +
                    "    AND IF(ISNULL(:#{#search.userName}),        1,ui.user_name LIKE :#{#search.userName}) " +
                    "    AND IF(ISNULL(:#{#search.userGender}),      1,ui.user_gender = :#{#search.userGender}) " +
                    "    AND IF(ISNULL(:#{#search.campaignType}),    1,ci.campaign_type = :#{#search.campaignType}) " +
                    "    AND IF(ISNULL(:#{#search.campaignName}),    1,ci.campaign_name LIKE :#{#search.campaignName}) " +
                    "    AND IF(ISNULL(:#{#search.instructor}),      1,ci.instructor = :#{#search.instructor}) " +
                    "    AND IF(ISNULL(:#{#search.creditTimeFrom}),  1,ci.create_time >= :#{#search.creditTimeFrom}) " +
                    "    AND IF(ISNULL(:#{#search.creditTimeTo}),    1,ci.create_time <= :#{#search.creditTimeTo}) " +
                    "    AND IF(ISNULL(:#{#search.createBy}),        1,ci.create_by = :#{#search.createBy}) " +
                    ") a")
    List<CreditReport> findSub(@Param("search") SearchCreditRequest search);

    @Query(nativeQuery = true,
            value = "SELECT a.* FROM ( " +
                    "  SELECT UUID() AS guid, " +
                    "         1 AS total, " +
                    "         IF(ci.status<20,0,1) AS submit, " +
                    "         ci.atr5 AS certificate, " +
                    "         '' AS creditIds, " +
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
                    "         ci.remark AS remark, " +
                    "         ci.status AS status, " +
                    "         ci.create_by AS createBy, " +
                    "         ci.create_time AS createTime" +
                    "  FROM credit_info ci " +
                    "    INNER JOIN user_info ui ON ci.user_id = ui.user_id " +
                    "    INNER JOIN organization_mapping_info omi ON omi.father_organization_id = :#{#search.organizationId} AND ui.organization_id = omi.child_organization_id " +
                    "  WHERE IF(ISNULL(:#{#search.status}),          1,ci.status = :#{#search.status}) " +
                    "    AND IF(ISNULL(:#{#search.userCode}),        1,ui.user_code = :#{#search.userCode}) " +
                    "    AND IF(ISNULL(:#{#search.userName}),        1,ui.user_name LIKE :#{#search.userName}) " +
                    "    AND IF(ISNULL(:#{#search.userGender}),      1,ui.user_gender = :#{#search.userGender}) " +
                    "    AND IF(ISNULL(:#{#search.campaignType}),    1,ci.campaign_type = :#{#search.campaignType}) " +
                    "    AND IF(ISNULL(:#{#search.campaignName}),    1,ci.campaign_name LIKE :#{#search.campaignName}) " +
                    "    AND IF(ISNULL(:#{#search.instructor}),      1,ci.instructor = :#{#search.instructor}) " +
                    "    AND IF(ISNULL(:#{#search.creditTimeFrom}),  1,ci.create_time >= :#{#search.creditTimeFrom}) " +
                    "    AND IF(ISNULL(:#{#search.creditTimeTo}),    1,ci.create_time <= :#{#search.creditTimeTo}) " +
                    "    AND IF(ISNULL(:#{#search.createBy}),        1,ci.create_by = :#{#search.createBy}) " +
                    ") a",
            countQuery = "SELECT COUNT(a.creditId) FROM ( " +
                    "  SELECT ci.credit_id AS creditId " +
                    "  FROM credit_info ci " +
                    "    INNER JOIN user_info ui ON ci.user_id = ui.user_id " +
                    "    INNER JOIN organization_mapping_info omi ON omi.father_organization_id = :#{#search.organizationId} AND ui.organization_id = omi.child_organization_id " +
                    "  WHERE IF(ISNULL(:#{#search.status}),          1,ci.status = :#{#search.status}) " +
                    "    AND IF(ISNULL(:#{#search.userCode}),        1,ui.user_code = :#{#search.userCode}) " +
                    "    AND IF(ISNULL(:#{#search.userName}),        1,ui.user_name LIKE :#{#search.userName}) " +
                    "    AND IF(ISNULL(:#{#search.userGender}),      1,ui.user_gender = :#{#search.userGender}) " +
                    "    AND IF(ISNULL(:#{#search.campaignType}),    1,ci.campaign_type = :#{#search.campaignType}) " +
                    "    AND IF(ISNULL(:#{#search.campaignName}),    1,ci.campaign_name LIKE :#{#search.campaignName}) " +
                    "    AND IF(ISNULL(:#{#search.instructor}),      1,ci.instructor = :#{#search.instructor}) " +
                    "    AND IF(ISNULL(:#{#search.creditTimeFrom}),  1,ci.create_time >= :#{#search.creditTimeFrom}) " +
                    "    AND IF(ISNULL(:#{#search.creditTimeTo}),    1,ci.create_time <= :#{#search.creditTimeTo}) " +
                    "    AND IF(ISNULL(:#{#search.createBy}),        1,ci.create_by = :#{#search.createBy}) " +
                    ") a")
    Page<CreditReport> findPageable(@Param("search") SearchCreditRequest search, Pageable pageable);

    @Query(nativeQuery = true,
            value = "SELECT a.* FROM ( " +
                    "  SELECT UUID() AS guid, " +
                    "         COUNT(ci.credit_id) AS total, " +
                    "         SUM(IF(ci.status<20,0,1)) AS submit, " +
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
                    "         NULL AS remark, " +
                    "         NULL AS status, " +
                    "         NULL AS createBy, " +
                    "         NULL AS createTime" +
                    "  FROM credit_info ci " +
                    "    INNER JOIN user_info ui ON ci.user_id = ui.user_id " +
                    "    INNER JOIN organization_mapping_info omi ON omi.father_organization_id = :#{#search.organizationId} AND ui.organization_id = omi.child_organization_id " +
                    "  WHERE IF(ISNULL(:#{#search.status}),          1,ci.status = :#{#search.status}) " +
                    "    AND IF(ISNULL(:#{#search.userCode}),        1,ui.user_code = :#{#search.userCode}) " +
                    "    AND IF(ISNULL(:#{#search.userName}),        1,ui.user_name LIKE :#{#search.userName}) " +
                    "    AND IF(ISNULL(:#{#search.userGender}),      1,ui.user_gender = :#{#search.userGender}) " +
                    "    AND IF(ISNULL(:#{#search.campaignType}),    1,ci.campaign_type = :#{#search.campaignType}) " +
                    "    AND IF(ISNULL(:#{#search.campaignName}),    1,ci.campaign_name LIKE :#{#search.campaignName}) " +
                    "    AND IF(ISNULL(:#{#search.instructor}),      1,ci.instructor = :#{#search.instructor}) " +
                    "    AND IF(ISNULL(:#{#search.creditTimeFrom}),  1,ci.create_time >= :#{#search.creditTimeFrom}) " +
                    "    AND IF(ISNULL(:#{#search.creditTimeTo}),    1,ci.create_time <= :#{#search.creditTimeTo}) " +
                    "    AND IF(ISNULL(:#{#search.createBy}),        1,ci.create_by = :#{#search.createBy}) " +
                    "  GROUP BY ci.campaign_type, ci.campaign_name" +
                    ") a",
            countQuery = "SELECT COUNT(a.creditId) FROM ( " +
                    "  SELECT 0 AS creditId " +
                    "  FROM credit_info ci " +
                    "    INNER JOIN user_info ui ON ci.user_id = ui.user_id " +
                    "    INNER JOIN organization_mapping_info omi ON omi.father_organization_id = :#{#search.organizationId} AND ui.organization_id = omi.child_organization_id " +
                    "  WHERE IF(ISNULL(:#{#search.status}),          1,ci.status = :#{#search.status}) " +
                    "    AND IF(ISNULL(:#{#search.userCode}),        1,ui.user_code = :#{#search.userCode}) " +
                    "    AND IF(ISNULL(:#{#search.userName}),        1,ui.user_name LIKE :#{#search.userName}) " +
                    "    AND IF(ISNULL(:#{#search.userGender}),      1,ui.user_gender = :#{#search.userGender}) " +
                    "    AND IF(ISNULL(:#{#search.campaignType}),    1,ci.campaign_type = :#{#search.campaignType}) " +
                    "    AND IF(ISNULL(:#{#search.campaignName}),    1,ci.campaign_name LIKE :#{#search.campaignName}) " +
                    "    AND IF(ISNULL(:#{#search.instructor}),      1,ci.instructor = :#{#search.instructor}) " +
                    "    AND IF(ISNULL(:#{#search.creditTimeFrom}),  1,ci.create_time >= :#{#search.creditTimeFrom}) " +
                    "    AND IF(ISNULL(:#{#search.creditTimeTo}),    1,ci.create_time <= :#{#search.creditTimeTo}) " +
                    "    AND IF(ISNULL(:#{#search.createBy}),        1,ci.create_by = :#{#search.createBy}) " +
                    "  GROUP BY ci.campaign_type, ci.campaign_name" +
                    ") a")
    Page<CreditReport> findByCampaignGroupPageable(@Param("search") SearchCreditRequest search, Pageable pageable);

    @Query(nativeQuery = true,
            value = "SELECT a.* FROM ( " +
                    "  SELECT UUID() AS guid, " +
                    "         COUNT(ci.credit_id) AS total, " +
                    "         SUM(IF(ci.status<20,0,1)) AS submit, " +
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
                    "         NULL AS remark, " +
                    "         NULL AS status, " +
                    "         NULL AS createBy, " +
                    "         NULL AS createTime" +
                    "  FROM credit_info ci " +
                    "    INNER JOIN user_info ui ON ci.user_id = ui.user_id " +
                    "    INNER JOIN organization_mapping_info omi ON omi.father_organization_id = :#{#search.organizationId} AND ui.organization_id = omi.child_organization_id " +
                    "  WHERE IF(ISNULL(:#{#search.status}),          1,ci.status = :#{#search.status}) " +
                    "    AND IF(ISNULL(:#{#search.userCode}),        1,ui.user_code = :#{#search.userCode}) " +
                    "    AND IF(ISNULL(:#{#search.userName}),        1,ui.user_name LIKE :#{#search.userName}) " +
                    "    AND IF(ISNULL(:#{#search.userGender}),      1,ui.user_gender = :#{#search.userGender}) " +
                    "    AND IF(ISNULL(:#{#search.campaignType}),    1,ci.campaign_type = :#{#search.campaignType}) " +
                    "    AND IF(ISNULL(:#{#search.campaignName}),    1,ci.campaign_name LIKE :#{#search.campaignName}) " +
                    "    AND IF(ISNULL(:#{#search.instructor}),      1,ci.instructor = :#{#search.instructor}) " +
                    "    AND IF(ISNULL(:#{#search.creditTimeFrom}),  1,ci.create_time >= :#{#search.creditTimeFrom}) " +
                    "    AND IF(ISNULL(:#{#search.creditTimeTo}),    1,ci.create_time <= :#{#search.creditTimeTo}) " +
                    "    AND IF(ISNULL(:#{#search.createBy}),        1,ci.create_by = :#{#search.createBy}) " +
                    "  GROUP BY ci.user_id" +
                    ") a",
            countQuery = "SELECT COUNT(a.creditId) FROM ( " +
                    "  SELECT 0 AS creditId " +
                    "  FROM credit_info ci " +
                    "    INNER JOIN user_info ui ON ci.user_id = ui.user_id " +
                    "    INNER JOIN organization_mapping_info omi ON omi.father_organization_id = :#{#search.organizationId} AND ui.organization_id = omi.child_organization_id " +
                    "  WHERE IF(ISNULL(:#{#search.status}),          1,ci.status = :#{#search.status}) " +
                    "    AND IF(ISNULL(:#{#search.userCode}),        1,ui.user_code = :#{#search.userCode}) " +
                    "    AND IF(ISNULL(:#{#search.userName}),        1,ui.user_name LIKE :#{#search.userName}) " +
                    "    AND IF(ISNULL(:#{#search.userGender}),      1,ui.user_gender = :#{#search.userGender}) " +
                    "    AND IF(ISNULL(:#{#search.campaignType}),    1,ci.campaign_type = :#{#search.campaignType}) " +
                    "    AND IF(ISNULL(:#{#search.campaignName}),    1,ci.campaign_name LIKE :#{#search.campaignName}) " +
                    "    AND IF(ISNULL(:#{#search.instructor}),      1,ci.instructor = :#{#search.instructor}) " +
                    "    AND IF(ISNULL(:#{#search.creditTimeFrom}),  1,ci.create_time >= :#{#search.creditTimeFrom}) " +
                    "    AND IF(ISNULL(:#{#search.creditTimeTo}),    1,ci.create_time <= :#{#search.creditTimeTo}) " +
                    "    AND IF(ISNULL(:#{#search.createBy}),        1,ci.create_by = :#{#search.createBy}) " +
                    "  GROUP BY ci.user_id" +
                    ") a")
    Page<CreditReport> findByUserGroupPageable(@Param("search") SearchCreditRequest search, Pageable pageable);

    @Query(nativeQuery = true,
            value = "SELECT a.* FROM ( " +
                    "  SELECT UUID() AS guid, " +
                    "         COUNT(ci.credit_id) AS total, " +
                    "         SUM(IF(ci.status<20,0,1)) AS submit, " +
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
                    "         NULL AS remark, " +
                    "         NULL AS status, " +
                    "         NULL AS createBy, " +
                    "         NULL AS createTime" +
                    "  FROM credit_info ci " +
                    "    INNER JOIN user_info ui ON ci.user_id = ui.user_id " +
                    "    INNER JOIN organization_mapping_info omi ON omi.father_organization_id = :#{#search.organizationId} AND ui.organization_id = omi.child_organization_id " +
                    "  WHERE IF(ISNULL(:#{#search.status}),          1,ci.status = :#{#search.status}) " +
                    "    AND IF(ISNULL(:#{#search.userCode}),        1,ui.user_code = :#{#search.userCode}) " +
                    "    AND IF(ISNULL(:#{#search.userName}),        1,ui.user_name LIKE :#{#search.userName}) " +
                    "    AND IF(ISNULL(:#{#search.userGender}),      1,ui.user_gender = :#{#search.userGender}) " +
                    "    AND IF(ISNULL(:#{#search.campaignType}),    1,ci.campaign_type = :#{#search.campaignType}) " +
                    "    AND IF(ISNULL(:#{#search.campaignName}),    1,ci.campaign_name LIKE :#{#search.campaignName}) " +
                    "    AND IF(ISNULL(:#{#search.instructor}),      1,ci.instructor = :#{#search.instructor}) " +
                    "    AND IF(ISNULL(:#{#search.creditTimeFrom}),  1,ci.create_time >= :#{#search.creditTimeFrom}) " +
                    "    AND IF(ISNULL(:#{#search.creditTimeTo}),    1,ci.create_time <= :#{#search.creditTimeTo}) " +
                    "    AND IF(ISNULL(:#{#search.createBy}),        1,ci.create_by = :#{#search.createBy}) " +
                    "  GROUP BY ci.campaign_type,ci.campaign_name, ci.user_id" +
                    ") a",
            countQuery = "SELECT COUNT(a.creditId) FROM ( " +
                    "  SELECT 0 AS creditId " +
                    "  FROM credit_info ci " +
                    "    INNER JOIN user_info ui ON ci.user_id = ui.user_id " +
                    "    INNER JOIN organization_mapping_info omi ON omi.father_organization_id = :#{#search.organizationId} AND ui.organization_id = omi.child_organization_id " +
                    "  WHERE IF(ISNULL(:#{#search.status}),          1,ci.status = :#{#search.status}) " +
                    "    AND IF(ISNULL(:#{#search.userCode}),        1,ui.user_code = :#{#search.userCode}) " +
                    "    AND IF(ISNULL(:#{#search.userName}),        1,ui.user_name LIKE :#{#search.userName}) " +
                    "    AND IF(ISNULL(:#{#search.userGender}),      1,ui.user_gender = :#{#search.userGender}) " +
                    "    AND IF(ISNULL(:#{#search.campaignType}),    1,ci.campaign_type = :#{#search.campaignType}) " +
                    "    AND IF(ISNULL(:#{#search.campaignName}),    1,ci.campaign_name LIKE :#{#search.campaignName}) " +
                    "    AND IF(ISNULL(:#{#search.instructor}),      1,ci.instructor = :#{#search.instructor}) " +
                    "    AND IF(ISNULL(:#{#search.creditTimeFrom}),  1,ci.create_time >= :#{#search.creditTimeFrom}) " +
                    "    AND IF(ISNULL(:#{#search.creditTimeTo}),    1,ci.create_time <= :#{#search.creditTimeTo}) " +
                    "    AND IF(ISNULL(:#{#search.createBy}),        1,ci.create_by = :#{#search.createBy}) " +
                    "  GROUP BY ci.campaign_type,ci.campaign_name, ci.user_id" +
                    ") a")
    Page<CreditReport> findByCampaignAndUserGroupPageable(@Param("search") SearchCreditRequest search, Pageable pageable);

}