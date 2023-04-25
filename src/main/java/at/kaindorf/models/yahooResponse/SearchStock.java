package at.kaindorf.models.yahooResponse;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchStock {
    private String symbol;
    @JsonAlias("shortname")
    private String companyName;
}
