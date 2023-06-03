package at.kaindorf.persistence.repository;

import at.kaindorf.models.StockDto;
import at.kaindorf.persistence.entity.StockEntity;
import at.kaindorf.persistence.entity.UserEntity;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.security.UnauthorizedException;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@RequestScoped
public class UserRepository implements PanacheRepository<UserEntity> {

    @Inject
    StockRepository stockRepository;

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


    public Boolean isLiked(StockDto stockDto, String userAccessToken){
        UserEntity userEntity = findUserByAccessToken(userAccessToken);

        return userEntity.getLikedStocksList().stream()
                .map(StockDto::new)
                .toList()
                .contains(stockDto);
    }


    public List<StockDto> getLikedStocksList(String userAccessToken){
        UserEntity userEntity = findUserByAccessToken(userAccessToken);

        return userEntity.getLikedStocksList().stream()
                .map(StockDto::new)
                .toList();
    }


    @Transactional
    public void addStockToFavourite(StockDto stockDto, String token){
        UserEntity userEntity = findUserByAccessToken(token);

        StockEntity stockEntity = stockRepository.addStock(stockDto);
        userEntity.getLikedStocksList().add(stockEntity);

        persist(userEntity);
    }

    @Transactional
    public void removeStockFromFavourite(String symbol, String token){
        UserEntity userEntity = findUserByAccessToken(token);

        userEntity.getLikedStocksList().removeIf(stockEntity -> stockEntity.getSymbol().equals(symbol));
        persist(userEntity);
    }
}
