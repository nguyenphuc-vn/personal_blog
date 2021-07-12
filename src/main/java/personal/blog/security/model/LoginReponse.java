package personal.blog.security.model;

public class LoginReponse {

    private String jwt;

    public LoginReponse(String jwt) {
        this.jwt = jwt;
    }

    public String getJwt() {
        return jwt;
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;
    }
}
