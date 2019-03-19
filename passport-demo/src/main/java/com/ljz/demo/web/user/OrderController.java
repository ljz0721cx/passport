package com.ljz.demo.web.user;

import com.ljz.demo.web.async.DeferredResultHolder;
import com.ljz.demo.web.async.MockQueue;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.concurrent.Callable;

/**
 * @author 李建珍
 * @date 2019/3/18
 */
@RestController
public class OrderController {
    static String folder = "E:\\myworkspace\\passport";

    @PostMapping("/file")
    public String upload(MultipartFile file) throws IOException {
        System.out.println(file.getName());
        System.out.println(file.getOriginalFilename());
        System.out.println(file.getSize());

        File localFile = new File(folder, System.currentTimeMillis() + ".txt");
        file.transferTo(localFile);
        return "success";
    }

    /**
     * 通过图片id下载
     *
     * @param id
     * @param request
     * @param response
     * @throws IOException
     */
    @GetMapping("/file/{id}")
    public void download(@PathVariable String id,
                         HttpServletRequest request, HttpServletResponse response) throws IOException {

        try (InputStream inputStream = new FileInputStream(new File(folder, id + ".txt"));
             OutputStream outputStream = response.getOutputStream();) {
            response.setContentType("application/x-download");
            response.addHeader("Content-Disposition", "attachment;filename=test.txt");

            IOUtils.copy(inputStream, outputStream);
            outputStream.flush();
        }
    }


    /**
     * 异步返回
     *
     * @return
     */
    @RequestMapping("/async")
    public Callable<String> callable() {
        Callable<String> callable = new Callable<String>() {
            @Override
            public String call() throws Exception {
                Thread.sleep(1000);
                return "success";
            }
        };
        return callable;
    }


    @Autowired
    private MockQueue mockQueue;
    @Autowired
    private DeferredResultHolder deferredResultHolder;
    private static final Logger logger = LoggerFactory.getLogger(OrderController.class);

    /**
     * 下单异步返回
     * https://blog.csdn.net/xiao_jun_0820/article/details/82956593
     *
     * @return
     */
    @RequestMapping("/order")
    public DeferredResult<String> order() throws InterruptedException {

        String orderNum = RandomStringUtils.random(8);
        mockQueue.setPutOrder(orderNum);

        DeferredResult<String> result = new DeferredResult<>();
        //将订单对应的数据翻入获得数据的result
        deferredResultHolder.getMap().put(orderNum, result);
        return result;
    }


}
