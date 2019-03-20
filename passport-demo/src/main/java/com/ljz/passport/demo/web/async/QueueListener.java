package com.ljz.passport.demo.web.async;

import com.ljz.passport.demo.web.user.OrderController;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

/**
 * @author 李建珍
 * @date 2019/3/18
 */
//@Component
public class QueueListener implements ApplicationListener<ContextRefreshedEvent> {
    @Autowired
    private MockQueue mockQueue;
    @Autowired
    private DeferredResultHolder deferredResultHolder;
    private static final Logger logger = LoggerFactory.getLogger(OrderController.class);

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        /**
         * 设置启动后监听到数据后返回
         */
        new Thread(() -> {
            while (true) {
                if (StringUtils.isNotBlank(mockQueue.getCompeletOrder())) {
                    String orderNum = mockQueue.getCompeletOrder();
                    logger.info("返回订单处理结果" + orderNum);
                    deferredResultHolder.getMap().get(orderNum).setResult("order success");
                    mockQueue.getCompeletOrder();
                } else {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }
}
