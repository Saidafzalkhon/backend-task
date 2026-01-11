package uz.java.backendtask.entity;

import jakarta.persistence.*;
import lombok.*;
import uz.java.backendtask.enumeration.TokenType;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity @Table(name = "tokens")
public class Token extends BaseEntity {

    @Id
    @Column(name = "id", nullable = false, updatable = false, unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 255)
    public String token;

    @Enumerated(EnumType.STRING)
    @Column(name = "token_type", nullable = false, length = 50)
    public TokenType tokenType = TokenType.ACCESS;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id",nullable = false)
    public User user;

}