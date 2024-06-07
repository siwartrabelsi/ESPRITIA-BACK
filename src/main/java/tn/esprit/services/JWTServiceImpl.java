package tn.esprit.services;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.xml.bind.DatatypeConverter;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Date;
import java.util.function.Function;

@Service
public class JWTServiceImpl implements JWTService {
    @Override
    public String generateToken(UserDetails userDetails){
        return Jwts.builder().setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+1000*60*24))
                .signWith(SignatureAlgorithm.HS256,getSiginKey())
                .compact();
    }
    @Override
    public String extractUserName(String token){
        return extractClaim(token, Claims::getSubject);
    }
    private <T> T extractClaim(String token, Function<Claims,T> claimsResolvers){
        final Claims claims = extractAllClaims(token);
        return claimsResolvers.apply(claims);
    }
    private Key getSiginKey(){
        byte[] key = DatatypeConverter.parseBase64Binary("RXNwcml0aWE=");
        return new SecretKeySpec(key, "HmacSHA256");
    }

    public Claims extractAllClaims(String token) {
        return Jwts.parser()
                .setSigningKey(getSiginKey())
                .parseClaimsJws(token)
                .getBody();
    }

    public boolean isTokenValid(String token, UserDetails userDetails){
        final String username = extractUserName(token);
        return (username.equals(userDetails.getUsername())&& !isTokenExpired(token));
    }
    private boolean isTokenExpired(String token){
        return extractClaim(token, Claims::getExpiration).before(new Date());
    }
}

