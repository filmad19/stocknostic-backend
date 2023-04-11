package at.kaindorf.persistence.repository;

import at.kaindorf.persistence.entity.Stock;
import io.quarkus.mongodb.panache.PanacheMongoRepository;
import org.bson.types.ObjectId;

import javax.enterprise.context.ApplicationScoped;
import java.util.Optional;

@ApplicationScoped
public class StockRepository implements PanacheMongoRepository<Stock> {

    public Stock findByName(String name){
        return find("name", name).firstResult();
    }

    public Optional<Stock> findStockById(ObjectId id){
        return findByIdOptional(id);
    }

    public void addStock(Stock stock) {
        persist(stock);
    }
}
