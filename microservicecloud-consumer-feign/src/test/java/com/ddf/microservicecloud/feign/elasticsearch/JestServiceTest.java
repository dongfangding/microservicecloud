package com.ddf.microservicecloud.feign.elasticsearch;

import com.ddf.microservicecloud.api.entity.User;
import com.ddf.microservicecloud.feign.FeignApplicationTest;
import io.searchbox.client.JestClient;
import io.searchbox.core.Index;
import io.searchbox.indices.CreateIndex;
import io.searchbox.indices.mapping.PutMapping;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author DDf on 2018/6/26
 */
public class JestServiceTest extends FeignApplicationTest {

    @Autowired
    private JestService jestService;
    @Autowired
    private JestClient jestClient;

    @Test
    public void indexAllUserTest() throws IOException {
        jestService.indexAllUser();
    }

    @Test
    public void createIndexMapping() throws IOException {
        jestService.createIndexMapping();
    }

    @Test
    public void test() throws IOException {
        String source = "{\"user\":\"kimchy11\"}";
        Index index = new Index.Builder(source).index("twitter").type("tweet").id("2").build();
        jestClient.execute(index);
    }

    @Test
    public void queryMatch() {
        System.out.println(jestService.queryMatch());
    }


}
