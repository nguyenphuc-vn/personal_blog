package personal.blog.service;


import personal.blog.entity.User;
import personal.blog.security.model.LoginRequest;

public interface UserService {
    boolean signup(User user);

    boolean auth(LoginRequest loginRequest);
}
