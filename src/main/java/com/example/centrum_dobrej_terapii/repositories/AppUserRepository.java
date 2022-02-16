package com.example.centrum_dobrej_terapii.repositories;

import com.example.centrum_dobrej_terapii.entities.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional()
public interface AppUserRepository extends JpaRepository<AppUser, Long>{

    Optional<AppUser> findByEmail(String email);
    Optional<AppUser> findByPesel(String pesel);
    @Query("select u from AppUser u where u.phone_number= :phone_number")
    Optional<AppUser> findByPhone_number(@Param("phone_number") String phone_number);
    List<AppUser> findByUserRole(String userRole);
}


