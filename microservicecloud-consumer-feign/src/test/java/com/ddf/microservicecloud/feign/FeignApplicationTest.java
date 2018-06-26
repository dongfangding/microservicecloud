package com.ddf.microservicecloud.feign;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author DDf on 2018/6/26
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class FeignApplicationTest {

    @Before
    public void before() {
        System.out.println("before.................................................");
    }
}
