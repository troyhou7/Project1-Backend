package dev.houston.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

public class JwtUtil {

    private static final String secret = System.getenv("JWT_DETAILS");
    private static final Algorithm algorithm = Algorithm.HMAC256(secret);


    public static String generate(Boolean foreman, int minerId, String fname){
        String token = JWT.create()
                .withClaim("foreman",foreman) // add data to payload
                .withClaim("minerId",minerId)
                .withClaim("fname",fname)
                .sign(algorithm); // this will generate a signature based off of those claims
        return token;
    }

    public static DecodedJWT isValidJwt(String token){
        DecodedJWT jwt = JWT.require(algorithm).build().verify(token);
        return jwt;
    }


}


