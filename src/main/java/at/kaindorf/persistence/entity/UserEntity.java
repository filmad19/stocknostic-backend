package at.kaindorf.persistence.entity;

import lombok.*;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "app_user")
public class UserEntity {
    @Id
    @Column(name = "access_token")
    private String accessToken;
}
