package com.stormx.hicoder.repositories;

import com.stormx.hicoder.common.TokenType;
import com.stormx.hicoder.entities.User;
import com.stormx.hicoder.entities.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TokenRepository extends JpaRepository<Token, Long> {
    List<Token> findAllByUserAndType(User user, TokenType type);

}
