package com.hero.gateway.filter;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/*
 * @Author yaxiongliu
 **/
@Component
public class UrlFilter implements GlobalFilter, Ordered {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        System.out.println("经过第2个过滤器UrlFilter");
        ServerHttpRequest request = exchange.getRequest();
        String url = request.getURI().getPath();
        System.out.println("url:"+url);
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return 2;
    }
}
