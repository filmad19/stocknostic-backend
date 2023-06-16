package at.kaindorf.persistence.entity;

import lombok.*;

import javax.persistence.*;

/*
 * Matthias Filzmaier
 * 24.03.2023
 * stocknostic
 */

@Data
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "app_user")
// store the user access token
public class UserEntity {
    @Id
    @Column(name = "access_token")
    private String accessToken;
}
