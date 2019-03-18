package com.ljz.demo.web.async;

import org.springframework.stereotype.Component;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 李建珍
 * @date 2019/3/18
 */
@Component
public class DeferredResultHolder {

    /**
     * 订单号和处理结果
     */
    private Map<String, DeferredResult<String>> map = new HashMap<>();


    public Map<String, DeferredResult<String>> getMap() {
        return map;
    }

    public void setMap(Map<String, DeferredResult<String>> map) {
        this.map = map;
    }
}
