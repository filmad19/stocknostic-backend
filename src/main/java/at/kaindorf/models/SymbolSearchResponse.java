package at.kaindorf.models;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SymbolSearchResponse {
    @JsonAlias("bestMatches")
    public ArrayList<StockListItem> stocks;
}
