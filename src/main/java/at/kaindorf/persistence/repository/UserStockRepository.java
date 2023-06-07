package at.kaindorf.persistence.repository;

import at.kaindorf.models.StockDto;
import at.kaindorf.persistence.entity.StockEntity;
import at.kaindorf.persistence.entity.UserEntity;
import at.kaindorf.persistence.entity.UserStockEntity;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;

@RequestScoped
public class UserStockRepository implements PanacheRepository<UserStockEntity> {

    @Inject
    UserRepository userRepository;

    @Inject
    StockRepository stockRepository;

    @Inject
    @ConfigProperty(name = "rsi.oversold")
    Integer rsiOversoldDefault;

    @Inject
    @ConfigProperty(name = "rsi.overbought")
    Integer rsiOverboughtDefault;

    public List<StockDto> getFavouriteStockList(String token){
        return find("access_token", token).stream()
                .map(StockDto::new)
                .toList();
    }

    public Boolean isLiked(StockDto stockDto, String token){
        return getFavouriteStockList(token).contains(stockDto);
    }

    @Transactional
    public void addStockToFavourite(StockDto stockDto, String token){
        UserEntity userEntity = userRepository.findUserByAccessToken(token);
        StockEntity stockEntity = stockRepository.addStock(stockDto);

        UserStockEntity userStockEntity = new UserStockEntity(userEntity, stockEntity, rsiOversoldDefault, rsiOverboughtDefault);
        persist(userStockEntity);
    }

    @Transactional
    public void removeStockFromFavourite(String symbol, String token){
        UserStockEntity userStockEntity = find("symbol = ?1 and access_token = ?2", symbol, token).firstResult();
        delete(userStockEntity);
    }
}