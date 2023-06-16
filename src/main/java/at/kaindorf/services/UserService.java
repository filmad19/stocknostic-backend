package at.kaindorf.services;

import at.kaindorf.models.Token;
import at.kaindorf.persistence.repository.UserRepository;
import io.quarkus.security.UnauthorizedException;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import java.util.HexFormat;
import java.util.Random;

@RequestScoped
public class UserService {
    private static final Random RANDOM = new Random();

    @Inject
    UserRepository userRepository;

    public Token login(String access_token){

        if(userExists(access_token)){
            return new Token(access_token);
        } else {
            return createAccessToken();
        }
    }

    private Token createAccessToken(){
        String generatedToken;
        byte[] bytes = new byte[36];

        do{
            RANDOM.nextBytes(bytes);
            generatedToken = HexFormat.of().formatHex(bytes);

        } while(userExists(generatedToken));

        return new Token(userRepository.addUser(generatedToken));
    }

    private Boolean userExists(String generatedToken){
        try{
            userRepository.findUserByAccessToken(generatedToken);
            return true;
        }catch (UnauthorizedException e){
            return false;
        }
    }
}
