package at.kaindorf.models;

import lombok.Data;
import lombok.NoArgsConstructor;

/*
 * Matthias Filzmaier
 * 16.06.2023
 * stocknostic
 */

@Data
@NoArgsConstructor
public class RsiSettingsDto {
    private Integer oversold;
    private Integer overbought;

    public RsiSettingsDto(Integer overbought, Integer oversold) {
        this.overbought = overbought;
        this.oversold = oversold;
    }
}
