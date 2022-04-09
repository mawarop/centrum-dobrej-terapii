package com.example.centrum_dobrej_terapii.repositories;

import com.example.centrum_dobrej_terapii.entities.AppUser;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional()
public interface AppUserRepository extends JpaRepository<AppUser, Long> {

    Optional<AppUser> findByEmail(String email);

    Optional<AppUser> findByPesel(String pesel);

    @Query("select u from AppUser u where u.phone_number= :phone_number")
    Optional<AppUser> findByPhone_number(@Param("phone_number") String phone_number);

    List<AppUser> findByUserRole(String userRole);

    @Query("select u from AppUser u")
    List<AppUser> findAllAppUsers(Pageable pageable);

    @Transactional
    @Modifying
    @Query("update AppUser a " +
            "set a.enabled = TRUE " +
            "WHERE a.email = ?1")
    int enableAppUser(String email);


    @Query("SELECT  c from AppUser c WHERE " +
            "(LOWER(c.firstname) LIKE LOWER(CONCAT('%', :input, '%'))) " +
            "OR (LOWER(c.lastname) LIKE LOWER(CONCAT('%', :input, '%'))) " +
            "OR (LOWER(c.username) LIKE LOWER(CONCAT('%', :input, '%'))) " +
            "OR (LOWER(c.email) LIKE LOWER(CONCAT('%', :input, '%'))) " +
            "OR (LOWER(c.phone_number) LIKE LOWER(CONCAT('%', :input, '%')))"
    )
    List<AppUser> findAppUsersByInput(@Param("input") String input, Pageable pageable);


    @Query("SELECT  c from AppUser c WHERE " +
            "((LOWER(c.firstname) LIKE LOWER(CONCAT('%', :nameNumberOne, '%'))) " +
            "AND (LOWER(c.lastname) LIKE LOWER(CONCAT('%', :nameNumberTwo, '%'))))" +
            "OR ((LOWER(c.firstname) LIKE LOWER(CONCAT('%', :nameNumberTwo, '%'))) " +
            "AND (LOWER(c.lastname) LIKE LOWER(CONCAT('%', :nameNumberOne, '%')))) "

    )
    List<AppUser> findAppUsersByInput(@Param("nameNumberOne") String nameNumberOne, @Param("nameNumberTwo") String nameNumberTwo, Pageable pageable);

    @Query("SELECT count(c) from AppUser c WHERE " +
            "(LOWER(c.firstname) LIKE LOWER(CONCAT('%', :input, '%'))) " +
            "OR (LOWER(c.lastname) LIKE LOWER(CONCAT('%', :input, '%'))) " +
            "OR (LOWER(c.username) LIKE LOWER(CONCAT('%', :input, '%'))) " +
            "OR (LOWER(c.email) LIKE LOWER(CONCAT('%', :input, '%'))) " +
            "OR (LOWER(c.phone_number) LIKE LOWER(CONCAT('%', :input, '%')))"
    )
    long countAppUsersByInput(@Param("input") String input);

    @Query("SELECT count(c) from AppUser c WHERE " +
            "((LOWER(c.firstname) LIKE LOWER(CONCAT('%', :nameNumberOne, '%'))) " +
            "AND (LOWER(c.lastname) LIKE LOWER(CONCAT('%', :nameNumberTwo, '%'))))" +
            "OR ((LOWER(c.firstname) LIKE LOWER(CONCAT('%', :nameNumberTwo, '%'))) " +
            "AND (LOWER(c.lastname) LIKE LOWER(CONCAT('%', :nameNumberOne, '%')))) "
    )
    long countAppUsersByInput(@Param("nameNumberOne") String nameNumberOne, @Param("nameNumberTwo") String nameNumberTwo);
}


