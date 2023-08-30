package token;


import io.jsonwebtoken.*;
import org.apache.commons.lang.StringUtils;

import java.util.Date;

public class JwtHelper {
    private static long tokenExpiration = 24*60*60*1000;
    private static String tokenSignKey = "123456";

    public static String createToken(Integer id, String name) {
        String token = Jwts.builder()
                .setSubject("Person")
                .setExpiration(new Date(System.currentTimeMillis() + tokenExpiration))
                .claim("id", id)
                .claim("name", name)
                .signWith(SignatureAlgorithm.HS512, tokenSignKey)
                .compressWith(CompressionCodecs.GZIP)
                .compact();
        return token;
    }
    public static Long getId(String token) {
        if(StringUtils.isEmpty(token)) return null;
        Jws<Claims> claimsJws = Jwts.parser().setSigningKey(tokenSignKey).parseClaimsJws(token);
        Claims claims = claimsJws.getBody();
        Integer userId = (Integer)claims.get("id");
        return userId.longValue();
    }
    public static String getName(String token) {
        if(StringUtils.isEmpty(token)) return "";
        Jws<Claims> claimsJws
                = Jwts.parser().setSigningKey(tokenSignKey).parseClaimsJws(token);
        Claims claims = claimsJws.getBody();
        return (String)claims.get("name");
    }

}

