package com.ddf.microservicecloud.provider;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author DDf on 2018/5/14
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class ProviderApplicationTest {

    @Before
    public void before() {
        System.out.println("before.......................");
    }

    @After
    public void after() {
        System.out.println("after.........................");
    }

    @Test
    public void test() {
        System.out.println("test..........................");
    }

}
