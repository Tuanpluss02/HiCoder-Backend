package com.stormx.hicoder.repositories;


import com.stormx.hicoder.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository  extends JpaRepository<User, Long> {
}
