package com.hero.pwd;

import io.jsonwebtoken.*;
import org.junit.jupiter.api.Test;

import java.util.Base64;
import java.util.Date;

/*
 * @Author yaxiongliu
 **/
public class TestJwt {
    //签发JWT
    @Test
    public void createJwt(){
        //密钥
        byte[] secretKey = Base64.getEncoder().encode("hero".getBytes());

        Date date = new Date();
        JwtBuilder builder= Jwts.builder()
                .setId("888")   //设置唯一编号
                .setSubject("{'id':1,'name':'itxiongge'}")//设置主题  可以是JSON数据
                .setIssuedAt(date)//设置签发日期
//                .setExpiration(date)//用于设置过期时间 ，参数为Date类型数据
                .claim("roles","admin")//设置角色
                .signWith(SignatureAlgorithm.HS256, secretKey);//设置签名 使用HS256算法，并设置SecretKey(字符串)

        //构建 并返回一个字符串
        String jwt = builder.compact();
        System.out.println(jwt);//
        // eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiI4ODgiLCJzdWIiOiJ7J2lkJzoxLCduYW1lJzonaXR4aW9uZ2dlJ30iLCJpYXQiOjE2ODE1NjI1MzV9.fHj2PKU9gKnwsoK2lTMX3nKYvtOSm9Eh0usFIDyb0F0

    }
    //解析JWT
    @Test
    public void parserJwt(){
        //JWT的Token
        String compactJwt="eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiI4ODgiLCJzdWIiOiJ7J2lkJzoxLCduYW1lJzonaXR4aW9uZ2dlJ30iLCJpYXQiOjE2ODE1NjI4ODUsInJvbGVzIjoiYWRtaW4ifQ.hAFr6nTlAg_5CpnwBXxXz4NQqpEm4QiC4xYmPj-1MZ8";
        //获取JWT解析器
        JwtParser parser = Jwts.parser();
        //通过Secret解密JWT
        byte[] secretKey = Base64.getEncoder().encode("hero".getBytes());
        Claims claims = parser.setSigningKey(secretKey).parseClaimsJws(compactJwt).getBody();
        System.out.println(claims);//{'id':1,'name':'itxiongge'}
    }
    //校验JWT的有效期
    @Test
    public void createJwtExp(){
        //获取当前的时间时间戳
        long timeMillis = System.currentTimeMillis();
        System.out.println("timeMillis = " + timeMillis);//
        timeMillis += 30000;
        Date data = new Date(timeMillis);
        //密钥
        byte[] secretKey = Base64.getEncoder().encode("hero".getBytes());
        JwtBuilder builder= Jwts.builder()
                .setId("888")   //设置唯一编号
                .setSubject("{'id':1,'name':'itxiongge'}")//设置主题  可以是JSON数据
                .setIssuedAt(new Date())//设置签发日期
                .setExpiration(data)//设置有效期，是当前时间过3s
                .signWith(SignatureAlgorithm.HS256, secretKey);//设置签名 使用HS256算法，并设置SecretKey(字符串)
        //构建 并返回一个字符串
        String jwt = builder.compact();
        System.out.println(jwt);//
        //
    }
}
