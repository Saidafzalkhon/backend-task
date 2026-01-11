package uz.java.backendtask.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.java.backendtask.entity.Token;
import uz.java.backendtask.enumeration.TokenType;

import java.util.List;
import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, Integer> {

    @Query(value = """
            select t from Token t inner join User u\s
            on t.user.id = u.id\s
            where u.id = :id\s
            """)
    List<Token> findAllValidTokenByUser(Long id);

    @Query(value = """
            select t from Token t inner join User u\s
            on t.user.id = u.id\s
            where u.id = :id and t.tokenType = :type\s
            """)
    List<Token> findAllValidTokenByUserByType(Long id,TokenType type);

    Optional<Token> findFirstByTokenAndTokenType(String token, TokenType type);
}