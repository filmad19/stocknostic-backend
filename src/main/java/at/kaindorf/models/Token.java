package at.kaindorf.models;

/*
 * Matthias Filzmaier
 * 21.04.2023
 * stocknostic
 */

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
// authentification to store settings and likes
public class Token {
    private String value;
}
