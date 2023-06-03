package at.kaindorf.services;

import at.kaindorf.models.PricePointDto;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import java.util.List;

@RequestScoped
public class IndicatorService {

    @Inject
    StockDataService stockDataService;

    public PricePointDto calculateRSI(String symbol) {

        List<PricePointDto> pricePointDtoList = stockDataService.getStockPriceHistory(symbol, "1d", "34d");

        int windowSize = 14;

        // Calculate RSI for each PricePoint
        for (int i = windowSize; i < pricePointDtoList.size(); i++) {
            double sumGain = 0;
            double sumLoss = 0;

            // Calculate average gain and loss over the last 14 PricePoints
            for (int j = i - windowSize + 1; j <= i; j++) {
                double priceDifference = pricePointDtoList.get(j).getClose() - pricePointDtoList.get(j - 1).getClose();

                if (priceDifference >= 0) {
                    sumGain += priceDifference;
                } else {
                    sumLoss += Math.abs(priceDifference);
                }
            }

            double averageGain = sumGain / windowSize;
            double averageLoss = sumLoss / windowSize;

            // Calculate relative strength (RS) and relative strength index (RSI)
            double rs = averageGain / averageLoss;
            double rsi = 100 - (100 / (1 + rs));

            // Set the calculated RSI value for the current PricePoint
            pricePointDtoList.get(i).setRsi(rsi);
        }

        return pricePointDtoList.get(pricePointDtoList.size()-1);
    }
}
