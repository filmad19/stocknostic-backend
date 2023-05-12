package at.kaindorf.services;

import at.kaindorf.models.PricePoint;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import java.util.List;

@RequestScoped
public class IndicatorService {

    @Inject
    StockDataService stockDataService;

    public List<PricePoint> calculateRSI(String symbol) {

        List<PricePoint> pricePointList = stockDataService.getStockPriceHistory(symbol, "1d", "34d");

        int windowSize = 14;

        // Calculate RSI for each PricePoint
        for (int i = windowSize; i < pricePointList.size(); i++) {
            double sumGain = 0;
            double sumLoss = 0;

            // Calculate average gain and loss over the last 14 PricePoints
            for (int j = i - windowSize + 1; j <= i; j++) {
                double priceDifference = pricePointList.get(j).getClose() - pricePointList.get(j - 1).getClose();

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
            pricePointList.get(i).setRsi(rsi);
        }

        return pricePointList;
    }


    public String getRecommendation(String symbol){
        List<PricePoint> pricePointList = calculateRSI(symbol);
        PricePoint pricePoint = pricePointList.get(pricePointList.size()-1);

        double rsi = pricePoint.getRsi();

        if (rsi > 80) {
            return "SELL";
        } else if (rsi < 20) {
            return "BUY";
        }

        return "HOLD";
    }
}
