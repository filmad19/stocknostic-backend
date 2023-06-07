package at.kaindorf.persistence.repository;

import at.kaindorf.persistence.entity.UserEntity;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.security.UnauthorizedException;

import javax.enterprise.context.RequestScoped;
import javax.transaction.Transactional;

@RequestScoped
public class UserRepository implements PanacheRepository<UserEntity> {

    public UserEntity findUserByAccessToken(String userAccessToken){
        UserEntity userEntity = find("accessToken", userAccessToken).firstResult();

        if(userEntity == null){
            throw new UnauthorizedException();
        }

        return userEntity;
    }


    @Transactional
    public String addUser(String generatedToken) {
        UserEntity userEntity = new UserEntity(generatedToken);

        persist(userEntity);
        return userEntity.getAccessToken();
    }
}
