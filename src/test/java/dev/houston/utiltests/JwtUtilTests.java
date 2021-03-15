package dev.houston.utiltests;

import com.auth0.jwt.interfaces.DecodedJWT;
import dev.houston.utils.JwtUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class JwtUtilTests {

    @Test
    void creates_jwt(){
        String jwt = JwtUtil.generate(false,1,"Guy");
        System.out.println(jwt);
    }
    @Test
    void creates_jwt_manager(){
        String jwt = JwtUtil.generate(true,2,"Grimly");
        System.out.println(jwt);
    }

    @Test
    void decode_jwt(){
        DecodedJWT jwt = JwtUtil.isValidJwt("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJmbmFtZSI6Ikd1eSIsImZvcmVtYW4iOmZhbHNlLCJtaW5lcklkIjoxfQ.D015YyNQpz1Eyc_d-ngWTkUc6tcpWjcg7rK2i-CP0xA");
        String name = jwt.getClaim("fname").asString();
        System.out.println(name);
    }

    @Test
    void edited_jwt(){ //should throw exception then pass. fails if no exception is thrown
        try {
            DecodedJWT jwt = JwtUtil.isValidJwt("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJmbmFtZSI6Ikd1eSIsImZvcmVtYW4iOnRydWUsIm1pbmVySWQiOjF9.23t3cuUDtNOPH8ejd9Qm_M-8B92pX5SDaAPkIVgA8CM");
            boolean foreman = jwt.getClaim("foreman").asBoolean();
            System.out.println(foreman);
            Assertions.fail();
        }catch(Exception e){
            Assertions.assertTrue(true);
        }

    }

}
