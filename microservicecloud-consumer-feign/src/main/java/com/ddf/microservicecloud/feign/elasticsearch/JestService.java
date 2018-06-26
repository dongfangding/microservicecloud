package com.ddf.microservicecloud.feign.elasticsearch;

import com.ddf.microservicecloud.api.entity.User;
import com.ddf.microservicecloud.feign.feignservice.UserFeignService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.searchbox.client.JestClient;
import io.searchbox.core.Index;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;
import io.searchbox.indices.CreateIndex;
import io.searchbox.indices.mapping.PutMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

/**
 * @author DDf on 2018/6/25
 * Jest is a Java HTTP Rest client for ElasticSearch.
 * 参考文档https://github.com/searchbox-io/Jest/tree/master/jest
 */
@Service
public class JestService {

    @Autowired
    private JestClient jestClient;
    @Autowired
    private UserFeignService userFeignService;

    /**
     * 索引一个文档
     * 将所有用户索引到Index为common，type为user索引中去
     */
    public void indexAllUser() throws IOException {
        // List<User> userList = userFeignService.queryAll();
        User user1 = new User();
        user1.setUserName("hehe");
        User user2 = new User();
        user2.setUserName("hegege");
        List<User> userList = new ArrayList<>();
        userList.add(user1);
        userList.add(user2);
        for (User user : userList) {
            jestClient.execute(new Index.Builder(user).index("common").type("user").build());
        }
    }

    public void createIndexMapping() throws IOException {
        PutMapping putMapping = new PutMapping.Builder(
                "my_index",
                "my_type",
                "{ \"my_type\" : { \"properties\" : { \"message\" : {\"type\" : \"string\", \"store\" : \"yes\"} } } }"
        ).build();
        jestClient.execute(putMapping);
    }

    public String queryMatch() {
        String searchJson = "{\n" +
                "  \"query\": {\n" +
                "    \"match\": {\n" +
                "      \"userName\": \"hehe\"\n" +
                "    }\n" +
                "  }\n" +
                "}";
        Search search = new Search.Builder(searchJson).addIndex("common").addType("user").build();
        try {
            return jestClient.execute(search).getJsonString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
