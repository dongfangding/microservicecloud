package com.ddf.microservicecloud.feignservice;

import com.ddf.microservicecloud.entity.User;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author DDf on 2018/5/29
 */
@Component
public class HystrixFallFactory implements FallbackFactory<HystrixFeignService> {
    @Override
    public HystrixFeignService create(Throwable throwable) {
        return new HystrixFeignService() {
            @Override
            public List<User> hystrixList() {
                return error();
            }

            @Override
            public List<User> userList() {
                return error();
            }
        };
    }

    public List<User> error() {
        List<User> errorList = new ArrayList<>();
        User user = new User();
        user.setErrorMessage("当前服务已进行降级，暂时不可访问！");
        errorList.add(user);
        return errorList;
    }
}
