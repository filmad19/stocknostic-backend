package at.kaindorf.models;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class StockListItem {
    @JsonAlias("1. symbol")
    public String symbol;
    @JsonAlias("2. name")
    public String companyName;

//    @JsonProperty("3. type")
//    public String type;
//    @JsonProperty("4. region")
//    public String region;
//    @JsonProperty("5. marketOpen")
//    public String marketOpen;
//    @JsonProperty("6. marketClose")
//    public String marketClose;
//    @JsonProperty("7. timezone")
//    public String timezone;

    @JsonAlias("8. currency")
    public String currency;
}