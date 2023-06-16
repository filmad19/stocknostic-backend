package at.kaindorf.persistence.entity;

import at.kaindorf.models.StockDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/*
 * Matthias Filzmaier
 * 24.03.2023
 * stocknostic
 */

@Data
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "stock")
// Database Entity to store symbol and company name, because only the search YahooFinance endpoints sends the companyName
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
