package com.ddf.microservicecloud.consumer.api;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * @author DDf on 2018/5/15
 */
@Component
@PropertySource(value = "classpath:API.properties")
public class Api {

    /** 访问服务提供者的用户相关接口的前缀地址 */
    @Value("${provider-user}")
    private String providerUser;


    public String getProviderUser() {
        return providerUser;
    }

    public void setProviderUser(String providerUser) {
        this.providerUser = providerUser;
    }

}
