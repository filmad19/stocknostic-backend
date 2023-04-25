package at.kaindorf.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Stock {
    private String symbol;
    private String companyName;
    private Boolean liked;
    private String currency;
    private Double previousClosePrice;
    private List<PricePoint> pricePointList;

    public Stock(String symbol, String currency, Double previousClosePrice, List<PricePoint> pricePointList) {
        this.symbol = symbol;
        this.currency = currency;
        this.previousClosePrice = previousClosePrice;
        this.pricePointList = pricePointList;
    }


    public Stock setCompanyName(String name){
        this.companyName = name;
        return this;
    }
}
