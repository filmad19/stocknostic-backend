package at.kaindorf.persistence.repository;

import at.kaindorf.models.StockDto;
import at.kaindorf.persistence.entity.StockEntity;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.RequestScoped;
import javax.transaction.Transactional;

@RequestScoped
public class StockRepository implements PanacheRepository<StockEntity> {

    public StockEntity getStockBySymbol(String symbol){
        return find("symbol", symbol).firstResult();
    }

    @Transactional
    public StockEntity addStock(StockDto stockDto){
        StockEntity existingStock = getStockBySymbol(stockDto.getSymbol());

        if(existingStock != null){
            return existingStock;
        }

        StockEntity stockEntity = new StockEntity(stockDto);
        persist(stockEntity);

        return stockEntity;
    }
}
