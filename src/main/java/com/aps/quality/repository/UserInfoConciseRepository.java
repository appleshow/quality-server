package com.aps.quality.repository;

import com.aps.quality.entity.UserConcise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserInfoConciseRepository extends JpaRepository<UserConcise, Integer>,
        JpaSpecificationExecutor<UserConcise>,
        PagingAndSortingRepository<UserConcise, Integer> {

    @Query("SELECT a FROM UserConcise a " +
            "WHERE a.userId IN (SELECT b.userId FROM UserRoleInfo b WHERE b.roleId IN (4,5))")
    List<UserConcise> findApproval();

}