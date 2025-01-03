package com.abas.springJWT.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.time.LocalDate;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtService {

    final String SECRET_KEY = "iDG50QG4XTl37IpHViZRtaAEVb9tyquwCZSAQf1YZQgoG4GToi8Xr+v3oWgsCpUZ";
    public String extractUserNames(String jwt) {
        /**
         * alternative
         *    Claims claim = extractAllClaims(jwt);
         *    return claims.getSubject();
         *    however extract claim function makes it easier to return different things in the claims
         */
        return extractClaim(jwt, Claims::getSubject);
    }

    //what if we want to generate

    /**
     *
     * @param extraClaims - Allows us to add an extra key,value pair to our Claims (ex role:Role.getname()
     * @param userDetails - extract the username from the client and set it as the subject
     * @return compact is what allows us to return and generate the token
     */
    public String generateToken(Map<String,Object> extraClaims, UserDetails userDetails){
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 24 * 60))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();

    }

    //this allows us to extract all the claims from the jwt, which will be decoded as the payload
    private Claims extractAllClaims(String jwt){
        return Jwts.
                parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(jwt)
                .getBody();
    }

    public <T> T extractClaim(String token, Function<Claims,T>claimResolver){
        /**
         * this allows us to fetch a single claim or a single key value pair from the claims
         * the Function parameter is a method reference that allows us to pass in any function and perform the lamba
         * essentially we can have extractClaim(token, Claim::getSubject)
         * this function will now be applied to the claim something like
         * claim -> claim.getSubject()
         */

        Claims claim = extractAllClaims(token);
        //for the claim object apply the function within claimResolver, example Claim::getSubject
        return claimResolver.apply(claim);
    }



    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }


}
