package service;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.RSASSASigner;
import com.nimbusds.jose.crypto.RSASSAVerifier;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.gen.RSAKeyGenerator;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import context.Component;
import dao.UserDao;
import dao.UserDaoImpl;
import model.User;

import java.text.ParseException;

public class TokenService implements Component {
    private UserDao userDao;

    public String getToken(Integer id, String role) throws JOSEException {
        RSAKey rsaKey = new RSAKeyGenerator(2048).keyID(role + id).generate();
        RSAKey rsaKeyPublic = rsaKey.toPublicJWK();
        userDao.updateVerifier(rsaKeyPublic.toString(), id);
        JWSSigner signer = new RSASSASigner(rsaKey);
        //jackson token data
        JWTClaimsSet claimsSet = new JWTClaimsSet.Builder().claim("id", id).claim("role", role).build();
        SignedJWT jwsObject = new SignedJWT(new JWSHeader.Builder(JWSAlgorithm.RS256).keyID(rsaKey.getKeyID()).build(), claimsSet);
        jwsObject.sign(signer);
        return jwsObject.serialize();
    }

    public User parse(String userToken) {
        User user = null;
        try {
            SignedJWT signedJWT = SignedJWT.parse(userToken);
            JWTClaimsSet claimsSet = signedJWT.getJWTClaimsSet();
            Integer id = claimsSet.getIntegerClaim("id");
            String jsonKey = userDao.findVerifierById(id);
            RSAKey rsaKey = RSAKey.parse(jsonKey);
            JWSVerifier verifier = null;
            try {
                verifier = new RSASSAVerifier(rsaKey);
                if (signedJWT.verify(verifier)) {
                    user = userDao.findUserById(id);
                }
            } catch (JOSEException e) {
                //ignored
            }
            return user;
        } catch (ParseException e) {
            return null;
        }
    }

    public UserDao getUserDao() {
        return userDao;
    }

    public void setUserDao(UserDaoImpl userDao) {
        this.userDao = userDao;
    }

    @Override
    public String getComponentName() {
        return null;
    }
}
