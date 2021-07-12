package personal.blog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import personal.blog.entity.User;
import personal.blog.security.CustomUserDetailsService;
import personal.blog.security.jwt.JwtUtil;
import personal.blog.security.model.LoginReponse;
import personal.blog.security.model.LoginRequest;
import personal.blog.service.UserService;

import javax.validation.Valid;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api/auth/")
public class UserController {
    private static final Logger logger = Logger.getLogger(UserController.class.getName());
    @Autowired
    private UserService userService;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody User user) {
        if (!userService.signup(user)) {
            return ResponseEntity.badRequest().body("username already exists");
        }
        userService.signup(user);
        return ResponseEntity.ok("registered successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<?> createToken(@RequestBody LoginRequest loginRequest) {

        if (userService.auth(loginRequest)) {
            try {
                authenticationManager.authenticate
                        (new UsernamePasswordAuthenticationToken(
                                loginRequest.getUsername(),
                                loginRequest.getPassword()));
            } catch (BadCredentialsException e) {
                throw new BadCredentialsException("Incorrect username or password");
            }
        } else {
            throw new BadCredentialsException("Incorrect username or password");
        }

        UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequest.getUsername());
        String jwt = jwtUtil.generateToken(userDetails);
        logger.info(jwt);
        return ResponseEntity.ok(new LoginReponse(jwt));
    }

}
