package personal.blog.service;


import personal.blog.entity.User;
import personal.blog.security.model.LoginRequest;

public interface UserService {
    public boolean signup(User user);

    public boolean auth(LoginRequest loginRequest);
}
