package personal.blog.service.logic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import personal.blog.entity.User;
import personal.blog.repository.UserRepository;
import personal.blog.security.model.LoginRequest;
import personal.blog.service.UserService;

import java.util.Optional;
import java.util.logging.Logger;

@Service
public class MyUser implements UserService {
    private static final Logger logger = Logger.getLogger(UserService.class.getName());
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder encoder;

    @Override
    public boolean signup(User user) {
        Optional<User> temp = userRepository.findByUsername(user.getUsername());
        if (temp.isPresent()) {
            return false;
        }
        user.setPassword(encoder.encode(user.getPassword()));
        user.setAuthority("ROLE_USER");
        userRepository.save(user);
        return true;
    }

    @Override
    public boolean auth(LoginRequest loginRequest) {
        Optional<User> user = userRepository.findByUsername(loginRequest.getUsername());
        return user.filter(value -> encoder.matches(loginRequest.getPassword(), value.getPassword())).isPresent();
    }
}
