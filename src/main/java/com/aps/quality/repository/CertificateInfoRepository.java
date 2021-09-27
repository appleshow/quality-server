package com.aps.quality.repository;

import com.aps.quality.entity.CertificateInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CertificateInfoRepository extends JpaRepository<CertificateInfo, Integer>,
        JpaSpecificationExecutor<CertificateInfo>,
        PagingAndSortingRepository<CertificateInfo, Integer> {

    Optional<List<CertificateInfo>> findByCreditId(Integer creditId);
}