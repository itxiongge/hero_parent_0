package com.hero.business.listener;

import okhttp3.*;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RabbitListener(queues = "ad_update_queue")
public class AdListener {

    /**
     * 获取更新广告通知
     * @param message
     */
    @RabbitHandler
    public void updateAd(String message){
        System.out.println("接收到消息："+message);
        String url = "http://192.168.200.128/ad_update?position="+message;
        OkHttpClient okHttpClient = new OkHttpClient();
        final Request request = new Request.Builder()
                .url(url)
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();//显示错误信息
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                System.out.println("调用成功"+response.message());
            }
        });
    }
}
