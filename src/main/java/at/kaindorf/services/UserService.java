package at.kaindorf.services;

import at.kaindorf.models.Token;
import at.kaindorf.persistence.repository.UserRepository;
import io.quarkus.security.UnauthorizedException;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import java.util.HexFormat;
import java.util.Random;

/*
 * Matthias Filzmaier
 * 21.04.2023
 * stocknostic
 */

@RequestScoped
public class UserService {
    private static final Random RANDOM = new Random();

    @Inject
    UserRepository userRepository;

    public Token login(String access_token){

        if(userExists(access_token)){
            return new Token(access_token); //if user exists return the existing token
        } else {
            return createAccessToken(); //if user does not exist create a new User
        }
    }

    private Token createAccessToken(){
        String generatedToken;
        byte[] bytes = new byte[36];

        do{
            RANDOM.nextBytes(bytes);
            //generate random string
            generatedToken = HexFormat.of().formatHex(bytes);

        } while(userExists(generatedToken));//make sure the token is unique

        return new Token(userRepository.addUser(generatedToken)); //add the user
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
