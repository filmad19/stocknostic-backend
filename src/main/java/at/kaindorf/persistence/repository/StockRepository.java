package at.kaindorf.persistence.repository;

import at.kaindorf.models.StockDto;
import at.kaindorf.persistence.entity.StockEntity;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.RequestScoped;
import javax.transaction.Transactional;

/*
 * Matthias Filzmaier
 * 14.03.2023
 * stocknostic
 */

@RequestScoped
public class StockRepository implements PanacheRepository<StockEntity> {

    public StockEntity getStockBySymbol(String symbol){
        return find("symbol", symbol).firstResult();
    }

    @Transactional
    public StockEntity addStock(StockDto stockDto){
        StockEntity existingStock = getStockBySymbol(stockDto.getSymbol());

        if(existingStock != null){
            return existingStock; //return the stock if it is already saved
        }

        StockEntity stockEntity = new StockEntity(stockDto);
        persist(stockEntity);//add new stock to database

        return stockEntity;
    }
}
