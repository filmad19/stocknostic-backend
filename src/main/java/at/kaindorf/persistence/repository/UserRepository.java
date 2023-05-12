package at.kaindorf.persistence.repository;

import at.kaindorf.persistence.entity.User;
import io.quarkus.mongodb.panache.PanacheMongoRepository;

import javax.enterprise.context.RequestScoped;
import java.util.List;

@RequestScoped
public class UserRepository implements PanacheMongoRepository<User> {

    public User findUserByAccessToken(String userAccessToken){
        return find("accessToken", userAccessToken).firstResult();
    }

    public String addUser(String generatedToken) {
        User user = new User(generatedToken);

        persist(user);
        return user.getAccessToken();
    }

    public Boolean isLiked(String symbol, String userAccessToken){
        User user = findUserByAccessToken(userAccessToken);

        return user.getLikedSymbolsList().contains(symbol);
    }

    public List<String> getLikedSymbolsList(String userAccessToken){
        User user = findUserByAccessToken(userAccessToken);

        return user.getLikedSymbolsList();
    }

    public void addStockToFavourite(String symbol, String token){
        User user = findUserByAccessToken(token);
        user.addSymbol(symbol);
        update(user);
    }

    public void removeStockFromFavourite(String symbol, String token){
        User user = findUserByAccessToken(token);
        user.removeSymbol(symbol);
        update(user);
    }
}
