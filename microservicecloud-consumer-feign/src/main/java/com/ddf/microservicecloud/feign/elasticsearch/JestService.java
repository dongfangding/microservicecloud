package com.ddf.microservicecloud.feign.elasticsearch;

import com.ddf.microservicecloud.api.entity.User;
import com.ddf.microservicecloud.feign.feignservice.UserFeignService;
import io.searchbox.client.JestClient;
import io.searchbox.core.Index;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.util.List;

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
    public void indexAllUser() {
        List<User> userList = userFeignService.queryAll();
        Index index = new Index.Builder(userList).index("common").type("user").build();
        try {
            jestClient.execute(index);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String searchDemo1() {
        String searchJson = "{\n" +
                "  \"query\": {\n" +
                "    \"match\": {\n" +
                "      \"userName\": \"f\"\n" +
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
