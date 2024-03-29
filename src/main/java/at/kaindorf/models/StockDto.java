package at.kaindorf.models;

import at.kaindorf.models.yahooResponse.SearchStock;
import at.kaindorf.persistence.entity.UserStockEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Objects;

/*
 * Matthias Filzmaier
 * 14.03.2023
 * stocknostic
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
// stock to be send to the frontend
public class StockDto {
    private String symbol;
    private String companyName;
    private Boolean liked;
    private String currency;
    private Double previousClosePrice;
    private List<PricePointDto> pricePointDtoList;

    public StockDto(SearchStock searchStock) {
        this.symbol = searchStock.getSymbol();
        this.companyName = searchStock.getCompanyName();
    }

    public StockDto(UserStockEntity userStockEntity) {
        this.symbol = userStockEntity.getStock().getSymbol();
        this.companyName = userStockEntity.getStock().getCompanyName();
    }

    //set values and return resulting object
    public StockDto withValues(Boolean isLiked, String currency, Double previousClosePrice, List<PricePointDto> pricePointDtoList){
        this.liked = isLiked;
        this.currency = currency;
        this.previousClosePrice = previousClosePrice;
        this.pricePointDtoList = pricePointDtoList;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        StockDto stockDto = (StockDto) o;

        if (!Objects.equals(symbol, stockDto.symbol)) return false;
        return Objects.equals(companyName, stockDto.companyName);
    }

    @Override
    public int hashCode() {
        int result = symbol != null ? symbol.hashCode() : 0;
        result = 31 * result + (companyName != null ? companyName.hashCode() : 0);
        return result;
    }
}
