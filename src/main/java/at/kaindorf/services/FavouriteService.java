package at.kaindorf.services;

import at.kaindorf.models.StockDto;
import at.kaindorf.persistence.repository.UserStockRepository;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import java.util.Comparator;
import java.util.List;

/*
 * Matthias Filzmaier
 * 21.04.2023
 * stocknostic
 */

@RequestScoped
public class FavouriteService {

    @Inject
    UserStockRepository userStockRepository;

    @Inject
    StockDataService stockDataService;

    public List<StockDto> getFavouriteStockList(String token){
        return userStockRepository.getFavouriteStockList(token).stream()
                .map(stock -> stockDataService.getStockPriceHistoryAndMeta(stock, "5m", "1d", token)) //add price points and meta data
                .sorted(Comparator.comparing(StockDto::getSymbol))
                .toList();
    }

    public void addStockToFavourite(StockDto stock, String token){
        userStockRepository.addStockToFavourite(stock, token);
    }

    public void removeStockFromFavourite(String symbol, String token){
        userStockRepository.removeStockFromFavourite(symbol, token);
    }
}
