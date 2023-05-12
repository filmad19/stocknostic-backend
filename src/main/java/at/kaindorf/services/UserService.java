package at.kaindorf.services;

import at.kaindorf.models.Token;
import at.kaindorf.persistence.repository.UserRepository;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import java.util.HexFormat;
import java.util.Random;

@RequestScoped
public class UserService {
    private static final Random RANDOM = new Random();

    @Inject
    UserRepository userRepository;

    public Token createAccessToken(){
        String generatedToken;
        byte[] bytes = new byte[36];

        do{
            RANDOM.nextBytes(bytes);
            generatedToken = HexFormat.of().formatHex(bytes);

        } while(userRepository.findUserByAccessToken(generatedToken) != null);

        return new Token(userRepository.addUser(generatedToken));
    }
}
