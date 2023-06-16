package at.kaindorf.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/*
 * Matthias Filzmaier
 * 06.05.2023
 * stocknostic
*/

@Data
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "user_stock",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"access_token", "symbol"}, name = "user_stock_uk")})
// store the liked stocks and the rsi settings
public class UserStockEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "access_token")
    @EqualsAndHashCode.Exclude
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name = "symbol")
    @EqualsAndHashCode.Exclude
    private StockEntity stock;

    @Column(name = "rsi_oversold")
    private Integer rsiOversold;

    @Column(name = "rsi_overbought")
    private Integer rsiOverbought;


    public UserStockEntity(UserEntity user, StockEntity stock) {
        this.user = user;
        this.stock = stock;
    }

    public UserStockEntity(UserEntity user, StockEntity stock, Integer rsiOversold, Integer rsiOverbought) {
        this.user = user;
        this.stock = stock;
        this.rsiOversold = rsiOversold;
        this.rsiOverbought = rsiOverbought;
    }

    @Override
    public String toString() {
        return "UserStockEntity{" +
                "id=" + id +
                ", user=" + user.getAccessToken() +
                ", stock=" + stock.getSymbol() +
                ", rsiOversold=" + rsiOversold +
                ", rsiOverbought=" + rsiOverbought +
                '}';
    }
}
