package at.kaindorf.persistence.entity;

import io.quarkus.mongodb.panache.PanacheMongoEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.codecs.pojo.annotations.BsonProperty;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User extends PanacheMongoEntity {
    private String accessToken;

    @BsonProperty("likedSymbolsList")
    private List<String> likedSymbolsList;

    public User(String accessToken) {
        this.accessToken = accessToken;
        this.likedSymbolsList = new ArrayList<>();
    }

    public void addSymbol(String symbol) {
        likedSymbolsList.add(symbol);
    }

    public void removeSymbol(String symbol) {
        likedSymbolsList.remove(symbol);
    }

    public List<String> getLikedSymbolsList() {
        return likedSymbolsList.stream()
                .map(line -> line.replace("{\"stockSymbol\":\"", "").replace("\"}", ""))
                .toList();
    }
}
