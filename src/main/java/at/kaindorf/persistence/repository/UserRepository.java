package at.kaindorf.persistence.repository;

import at.kaindorf.persistence.entity.UserEntity;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.security.UnauthorizedException;

import javax.enterprise.context.RequestScoped;
import javax.transaction.Transactional;

/*
 * Matthias Filzmaier
 * 14.03.2023
 * stocknostic
 */

@RequestScoped
public class UserRepository implements PanacheRepository<UserEntity> {

    public UserEntity findUserByAccessToken(String userAccessToken){
        UserEntity userEntity = find("accessToken", userAccessToken).firstResult();

        if(userEntity == null){
            throw new UnauthorizedException(); //if there is no user with this accessToken
        }

        return userEntity;
    }


    @Transactional
    public String addUser(String generatedToken) {
        UserEntity userEntity = new UserEntity(generatedToken);

        persist(userEntity); //add new User into database
        return userEntity.getAccessToken();
    }
}
