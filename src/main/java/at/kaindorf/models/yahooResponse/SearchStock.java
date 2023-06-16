package at.kaindorf.models.yahooResponse;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
 * Matthias Filzmaier
 * 14.03.2023
 * stocknostic
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
// response from the yahoo finance api
public class SearchStock {
    private String symbol;
    @JsonAlias("shortname")
    private String companyName;
}
