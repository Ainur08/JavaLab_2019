package service;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import model.Payload;
import net.Session;

import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;

public class TokenClientService {

    public Session saveToken(String token) {
        try (FileWriter writer = new FileWriter("token.txt", false)) {
            // сохраняет в файле на стороне пользователя токен
            writer.write(token);
            writer.flush();
            // возвращает сессию с айди и ролью пользователя
            SignedJWT signedJWT = SignedJWT.parse(token);
            JWTClaimsSet tokenSet = signedJWT.getJWTClaimsSet();
            String role = tokenSet.getStringClaim("role");
            Integer id = tokenSet.getIntegerClaim("id");

            Session session = new Session();
            session.setId(id);
            session.setRole(role);
            return session;
        } catch (ParseException | IOException e) {
            throw new IllegalStateException(e);
        }
    }
}
