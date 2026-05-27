package com.rosariodev.medik.util;

import java.util.Calendar;

import org.springframework.beans.factory.annotation.Value;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

import lombok.extern.slf4j.Slf4j;

/*
 *  Class used for the Bearer Token
 */
@Slf4j
public class JWTUtil {
    
    private static final String SECRET_KEY  = "CHIAVE SEGRETA";

    public static String generateToken(String email){
        try{
            int dayToExpire = 1;
            Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY);
            JWTCreator.Builder builder = JWT.create().withIssuer("medik.com").withSubject(email);
            Calendar c = Calendar.getInstance();
            c.add(Calendar.DAY_OF_YEAR, dayToExpire);
            builder.withExpiresAt(c.getTime());
            return builder.sign(algorithm);
        }catch(JWTCreationException ex){
            log.warn("Token not valid {}", ex.getMessage(), ex);
            throw new IllegalArgumentException("Token not valid:" + ex);
        }
    }
    
    public static String verifyToken(String token){
        try{
            Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY);
            JWTVerifier verifier = JWT.require(algorithm).withIssuer("medik.com").build();
            DecodedJWT jwt = verifier.verify(token);
            return jwt.getSubject();
        }catch(JWTVerificationException ex){
            log.warn("Token not valid {}", ex.getMessage(), ex);
            throw new IllegalArgumentException("Token not valid:" + ex);
        }
    } 

}

