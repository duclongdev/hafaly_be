package com.project.hafaly_be.domain.repository;

import com.project.hafaly_be.domain.enums.StatusRequest;
import com.project.hafaly_be.domain.model.Family;
import com.project.hafaly_be.domain.model.JoinFamily;
import com.project.hafaly_be.domain.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface JoinFamilyRepository extends JpaRepository<JoinFamily, UUID> {

    List<JoinFamily> findAllByFamily(Family family);

    boolean existsJoinFamilyByUserAndFamily(User user, Family family);
    @Modifying
    @Query("UPDATE JoinFamily jf SET jf.statusRequest = :statusRequest WHERE jf.user.id = :userId")
    void updateStatusRequestByUserId(@Param("statusRequest") StatusRequest statusRequest, @Param("userId") UUID userId);

}
