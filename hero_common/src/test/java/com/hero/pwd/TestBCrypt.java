package com.hero.pwd;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCrypt;

/*
 * @Author yaxiongliu
 **/
public class TestBCrypt {
    @Test
    public void testCreate(){
        //测试加密
        //1.定义密码
        String pwd = "123456";
        //2.调用BCrypt接口，对密码加密
        //获取盐
        String gensalt = BCrypt.gensalt();

        //String hashpw = BCrypt.hashpw(pwd, gensalt);
        //System.out.println("hashpw = " + hashpw);//加密后的
        //第1次  $2a$10$OBslQe16l7CWM2KfTYGbR.s2Xb/5VbZqcjbhXC1yN2cH9E4NrJSmW
        String hashpwd1 = "$2a$10$OBslQe16l7CWM2KfTYGbR.s2Xb/5VbZqcjbhXC1yN2cH9E4NrJSmW";
        //第2次  $2a$10$d0U8fh7Pj6uclhtoZWyqCOi/DZwagZI6UDySLactGGAh6PgkIT3Zu
        String hashpwd2 = "$2a$10$d0U8fh7Pj6uclhtoZWyqCOi/DZwagZI6UDySLactGGAh6PgkIT3Zu";
        //第3次  $2a$10$qnMBL95.ln.vnfkd1yFPueDKxsZ0kQCdgGBz.5xb4hvIfKUgRQnZy
        String hashpwd3 = "$2a$10$qnMBL95.ln.vnfkd1yFPueDKxsZ0kQCdgGBz.5xb4hvIfKUgRQnZy";

        /*
            密码忘记：重置就行
         */
        //测试解密：
        //1.拷贝上面加密后的密文，校验密码
        boolean checkpw = BCrypt.checkpw("123456", hashpwd2);
        System.out.println("checkpw = " + checkpw);//true
    }
}
