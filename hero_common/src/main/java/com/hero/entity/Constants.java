package com.hero.entity;

/**
 * 常量
 *
 */
public class Constants {
    //购物车
    public static final String REDIS_CART = "cart_";

    //待支付订单key
    public final static String REDIS_ORDER_PAY = "order_pay_";
    //秒杀商品key
    public static final String SECKILL_GOODS_KEY="seckill_goods_";
    //秒杀商品库存数key
    public static final String SECKILL_GOODS_STOCK_COUNT_KEY="seckill_goods_stock_count_";
    //秒杀用户key
    public static final String SECKILL_USER_KEY = "seckill_user_";
}
