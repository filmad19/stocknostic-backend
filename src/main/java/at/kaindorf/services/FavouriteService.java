package at.kaindorf.services;

import at.kaindorf.models.Stock;
import at.kaindorf.persistence.repository.UserRepository;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import java.util.List;

@RequestScoped
public class FavouriteService {
    @Inject
    UserRepository userRepository;

    @Inject
    StockDataService stockDataService;

    public List<Stock> getFavouriteStockList(String token){
        List<String> symbolList = userRepository.getLikedSymbolsList(token);

        List<Stock> stockList = symbolList.stream()
                .map(symbol -> stockDataService.getStockPriceHistoryAndMeta(symbol, "5m", "1d", token))
                .toList();

        if (stockList.size() == 0){
            stockList = stockDataService.searchStocks("a", token);
        }

        return stockList;
    }

    public void addStockToFavourite(String symbol, String token){
        userRepository.addStockToFavourite(symbol, token);
    }

    public void removeStockFromFavourite(String symbol, String token){
        userRepository.removeStockFromFavourite(symbol, token);
    }
}
