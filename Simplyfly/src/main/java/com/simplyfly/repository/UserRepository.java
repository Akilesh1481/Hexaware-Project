package com.simplyfly.repository;


import com.simplyfly.model.User;
import com.simplyfly.enums.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    Optional<User> findByEmail(String email);
    Boolean existsByEmail(String email);
    Boolean existsByPhone(String phone);
    List<User> findByRole(Role role);
    List<User> findByIsActiveTrue();
    List<User> findByIsActiveFalse();
    List<User> findByRoleAndIsActiveTrue(Role role);
    Optional<User> findByEmailAndIsActiveTrue(String email);
    List<User> findByFullNameContainingIgnoreCase(String name);

    @Query("SELECT COUNT(u) FROM User u WHERE " +
            "u.role = :role AND u.isActive = true")
    Long countActiveUsersByRole(@Param("role") Role role);

    @Query("SELECT u FROM User u WHERE " +
            "LOWER(u.fullName) LIKE LOWER(CONCAT('%',:keyword,'%')) OR " +
            "LOWER(u.email) LIKE LOWER(CONCAT('%',:keyword,'%'))")
    List<User> searchByNameOrEmail(@Param("keyword") String keyword);
    // Add these two methods:
    Page<User> findAll(Pageable pageable);
    Page<User> findByIsActiveTrue(Pageable pageable);
}


