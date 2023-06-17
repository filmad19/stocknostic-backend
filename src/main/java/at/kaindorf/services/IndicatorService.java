package at.kaindorf.services;

import at.kaindorf.models.PricePointDto;
import at.kaindorf.models.RsiSettingsDto;
import at.kaindorf.persistence.repository.UserStockRepository;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import java.util.List;

/*
 * Matthias Filzmaier
 * 06.05.2023
 * stocknostic
 */

@RequestScoped
public class IndicatorService {

    @Inject
    StockDataService stockDataService;

    @Inject
    UserStockRepository userStockRepository;

//    calculate rsi for every pricepoint
//    original plan was to display the rsi in the graph
    public PricePointDto calculateRSI(String symbol) {

        List<PricePointDto> pricePointDtoList = stockDataService.getStockPriceHistory(symbol, "1d", "20d"); //

        int windowSize = 14; //default window size of 14 days

        // Calculate RSI for each PricePoint
        for (int i = windowSize; i < pricePointDtoList.size(); i++) {
            double sumGain = 0;
            double sumLoss = 0;

            // Calculate average gain and loss over the last 14 PricePoints
            for (int j = i - windowSize + 1; j <= i; j++) {
                double priceDifference = pricePointDtoList.get(j).getClose() - pricePointDtoList.get(j - 1).getClose(); //get price difference between two days

                if (priceDifference >= 0) {
                    sumGain += priceDifference; // sum up gains
                } else {
                    sumLoss += Math.abs(priceDifference); // sum up losses
                }
            }

            double averageGain = sumGain / windowSize; //calculate average gain
            double averageLoss = sumLoss / windowSize; //calculate average loss

            double rs = averageGain / averageLoss;// Calculate relative strength
            double rsi = 100 - (100 / (1 + rs)); // relative strength index

            pricePointDtoList.get(i).setRsi(rsi); //set value for current price point
        }

        return pricePointDtoList.get(pricePointDtoList.size()-1); //only return the rsi from the last day
    }


    public void setRsiSettings(String token, RsiSettingsDto settingsDto, String symbol){
        userStockRepository.setRsiSettings(token, settingsDto, symbol);
    }

    public RsiSettingsDto getRsiSettings(String token, String symbol){
        return userStockRepository.getRsiSettings(token, symbol);
    }
}
