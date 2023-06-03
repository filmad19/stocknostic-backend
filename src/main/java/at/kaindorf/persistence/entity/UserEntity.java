package at.kaindorf.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "app_user")
public class UserEntity {
    @Id
    @Column(name = "access_token", unique = true)
    private String accessToken;

    @ManyToMany
    @Column(name = "liked_stocks")
    private Set<StockEntity> likedStocksList;

    public UserEntity(String accessToken) {
        this.accessToken = accessToken;
        this.likedStocksList = new HashSet<>();
    }
}
