package com.ljz.demo.web.async;

import org.springframework.stereotype.Component;

/**
 * @author 李建珍
 * @date 2019/3/18
 */
@Component
public class MockQueue {

    private String putOrder;
    private String compeletOrder;


    public String getPutOrder() {
        return putOrder;
    }

    public void setPutOrder(String putOrder) throws InterruptedException {
        new Thread(() -> {
            System.out.println("接到下单请求 " + putOrder);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            this.compeletOrder = putOrder;
            System.out.println("下单请求处理完成 " + putOrder);
        }).start();

    }

    public String getCompeletOrder() {
        return compeletOrder;
    }

    public void setCompeletOrder(String compeletOrder) {
        this.compeletOrder = compeletOrder;
    }
}
