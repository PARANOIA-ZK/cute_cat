package com.paranoia.upupup.jwt;


import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author ZHANGKAI
 * @date 2019/1/21
 * @description
 */
public class Test {


    public static void main(String[] args) {
        verifyToken(createToken());
    }

    public static String createToken() {
        String token = "";
        try {
            Algorithm algorithm = Algorithm.HMAC256("secret");
            Map<String, Object> headerClaims = new HashMap();
            headerClaims.put("payload", "auth0");
            headerClaims.put("exp", "123123");
             token= JWT.create()
                    .withHeader(headerClaims)
                    .sign(algorithm);
        } catch (JWTCreationException exception) {
            //Invalid Signing configuration / Couldn't convert Claims.
            exception.printStackTrace();
        }
        return token;
    }


    public static void verifyToken(String token){
        System.out.println("token = " + token);
        try {
            Algorithm algorithm = Algorithm.HMAC256("secret");
            JWTVerifier verifier = JWT.require(algorithm)
                    .build(); //Reusable verifier instance
            DecodedJWT jwt = verifier.verify(token);

            System.out.println("jwt = " + jwt.getIssuedAt());
            Date expiresAt = jwt.getExpiresAt();
            System.out.println("expiresAt = " + expiresAt);


        } catch (JWTVerificationException exception){
            exception.printStackTrace();
            //Invalid signature/claims
        }
    }

}
