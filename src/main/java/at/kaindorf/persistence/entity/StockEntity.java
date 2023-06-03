package at.kaindorf.persistence.entity;

import at.kaindorf.models.StockDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "stock")
public class StockEntity {
    @Id
    @Column(name = "symbol")
    private String symbol;

    @Column(name = "company_name", nullable = false)
    private String companyName;

    public StockEntity(StockDto stockDto) {
        this.symbol = stockDto.getSymbol();
        this.companyName = stockDto.getCompanyName();
    }
}
