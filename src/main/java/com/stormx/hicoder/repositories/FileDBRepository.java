package com.stormx.hicoder.repositories;

import com.stormx.hicoder.entities.FileDB;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface FileDBRepository extends JpaRepository<FileDB, String> {

    Optional<FileDB> findByName(String filename);
}