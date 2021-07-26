package personal.blog.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.logging.Logger;

@Component
public class JwtUtil {
    private static final int JWT_TOKEN_VALIDITY = 5;
    private static final Logger logger = Logger.getLogger(JwtUtil.class.getName());
    private static Date exp;
    @Value("${jwt.signing.key}")
    private String signature;

    //retrieve username from jwt token
    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    public Date getExpiration(String token) {
        return getClaimFromToken(token, Claims::getExpiration);

    }

    private Boolean isTokenExpired(String token) {
        logger.info("token " + token + "   " + getExpiration(token));
        return getExpiration(token).after(exp);
    }


    /**
     * @param token
     * @param claimsResolver
     * @param <T>            Get info from Bearer and extract to claims
     * @return T
     */
    private <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        Claims claims = getAllClaimsFromToken(token);
        logger.info("get claim : " + claims);
        return claimsResolver.apply(claims);
    }


    /**
     * @param token extract info like this payload  {sub=*, iat=*, exp=*} and get the body info
     * @return
     */
    private Claims getAllClaimsFromToken(String token) {
        logger.info("allclaims : " + token);
        return Jwts.parserBuilder()
                .setSigningKey(getSignature())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }


    /**
     * @param userDetails Empty map for any specific payload to add
     *                    Create token base on UD username,
     * @return String
     */
    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, userDetails.getUsername());
    }


    /**
     * @param claims
     * @param username setClaims : set that empty map
     *                 setSubject : the person who successfully authenticated
     * @return Jwts
     */
    private String createToken(Map<String, Object> claims, String username) {
        exp = expiredTime();
        return Jwts.builder()
                .setClaims(claims).setSubject(username).setIssuedAt(new Date())
                .setExpiration(exp)
                .signWith(getSignature()).compact();
    }

    /*    private KeyPair getKey(){
            return Keys.keyPairFor(SignatureAlgorithm.RS512);
        }*/
    private SecretKey getSignature() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(signature));
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        String username = getUsernameFromToken(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    private Date expiredTime() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        //System.err.println(calendar.getTime());
        calendar.add(Calendar.HOUR, JWT_TOKEN_VALIDITY);
        //System.err.println(calendar.getTime());
        return calendar.getTime();
    }

}
