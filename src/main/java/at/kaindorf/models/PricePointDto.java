package at.kaindorf.models;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/*
 * Matthias Filzmaier
 * 14.03.2023
 * stocknostic
 */

@Data
@NoArgsConstructor
// single price at a certain time
public class PricePointDto {
    private LocalDateTime timestamp;
    private Double close;
    private Double rsi;


    public PricePointDto(LocalDateTime timestamp, Double close) {
        this.timestamp = timestamp;
        this.close = close;
    }
}
