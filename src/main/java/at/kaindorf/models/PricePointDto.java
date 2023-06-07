package at.kaindorf.models;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
//@AllArgsConstructor
@NoArgsConstructor
public class PricePointDto {
    private LocalDateTime timestamp;
//    private Double open;
//    private Double high;
//    private Double low;
    private Double close;
//    private Long volume;
    private Double rsi;


    public PricePointDto(LocalDateTime timestamp, Double close) {
        this.timestamp = timestamp;
        this.close = close;
    }
}