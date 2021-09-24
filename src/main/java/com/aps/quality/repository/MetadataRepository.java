package com.aps.quality.repository;

import com.aps.quality.entity.Metadata;
import com.aps.quality.model.basic.MetadataSearch;
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
public interface MetadataRepository extends JpaRepository<Metadata, Integer>,
        JpaSpecificationExecutor<Metadata>,
        PagingAndSortingRepository<Metadata, Integer> {

    @Query("SELECT a FROM Metadata a " +
            "WHERE (:#{#search.type} IS NULL OR a.type = :#{#search.type}) " +
            "AND   (:#{#search.subtype} IS NULL OR a.subtype = :#{#search.subtype}) " +
            "AND   (:#{#search.category} IS NULL OR a.category = :#{#search.category}) " +
            "AND   (:#{#search.subcategory} IS NULL OR a.subcategory = :#{#search.subcategory}) " +
            "AND   (:#{#search.code} IS NULL OR a.code = :#{#search.code}) " +
            "AND   (:#{#search.subcode} IS NULL OR a.subcode = :#{#search.subcode}) " +
            "AND   (:#{#search.name} IS NULL OR lower(a.name) LIKE :#{#search.name})")
    Page<Metadata> findPageable(@Param("search") MetadataSearch search, Pageable pageable);

    @Query("SELECT a FROM Metadata a " +
            "WHERE (:#{#search.type} IS NULL OR a.type = :#{#search.type}) " +
            "AND   (:#{#search.subtype} IS NULL OR a.subtype = :#{#search.subtype}) " +
            "AND   (:#{#search.category} IS NULL OR a.category = :#{#search.category}) " +
            "AND   (:#{#search.subcategory} IS NULL OR a.subcategory = :#{#search.subcategory}) " +
            "AND   (:#{#search.code} IS NULL OR a.code = :#{#search.code}) " +
            "AND   (:#{#search.subcode} IS NULL OR a.subcode = :#{#search.subcode}) " +
            "AND   (:#{#search.name} IS NULL OR lower(a.name) LIKE :#{#search.name})")
    List<Metadata> findAll(@Param("search") MetadataSearch search);
}