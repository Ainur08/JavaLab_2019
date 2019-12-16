package service;

import com.nimbusds.jose.JOSEException;
import context.Component;
import dao.UserDao;
import dao.UserDaoImpl;
import model.User;
import protocol.Request;

public class LoginService implements Component {
    private UserDao userDao;
    private TokenService tokenService;

    public User login(Request request) {
        try {
            String login = request.getParameter("login");
            User user = userDao.findOneByUsername(login);
            if (user != null) {
                user.setToken(tokenService.getToken(user.getId(), user.getRole()));
            }
            return user;
        } catch (JOSEException e) {
            throw new IllegalStateException(e);
        }
    }

    public UserDao getUserDao() {
        return userDao;
    }

    public void setUserDao(UserDaoImpl userDao) {
        this.userDao = userDao;
    }

    public TokenService getTokenService() {
        return tokenService;
    }

    public void setTokenService(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @Override
    public String getComponentName() {
        return null;
    }
}



