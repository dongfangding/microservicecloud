
[TOC]

升级备注：

```
从1.5.9升级到2.0.3
eureka-server spring-cloud-starter-eureka   --- spring-cloud-starter-netflix-eureka-client
eureka-client
api
feign = ============  spring-cloud-starter-feign ==== spring-cloud-starter-openfeign
HystrixFeignService
import org.springframework.cloud.netflix.feign.FeignClient============= import org.springframework.cloud.openfeign.FeignClient;
UserClientService
import org.springframework.cloud.netflix.feign.FeignClient============= import org.springframework.cloud.openfeign.FeignClient;

consumer
ConsumerApplication
import org.springframework.cloud.netflix.feign.EnableFeignClients;=======import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;=========import org.springframework.cloud.openfeign.EnableFeignClients;

spring-cloud-starter-eureka =========spring-cloud-starter-netflix-eureka-client
feign"
FeignApplication
import org.springframework.cloud.netflix.feign.EnableFeignClients;=======import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;=========import org.springframework.cloud.openfeign.EnableFeignClients;
feignservice包下的所有service
import org.springframework.cloud.netflix.feign.FeignClient; ========== import org.springframework.cloud.openfeign.FeignClient;

pom
spring-cloud-starter-eureka =====  spring-cloud-starter-netflix-eureka-client
spring-cloud-starter-feign=========pring-cloud-starter-openfeign


eureka
pom
spring-cloud-starter-eureka-server =========spring-cloud-starter-netflix-eureka-server
parent
直接覆盖
provider:
HystrixController
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand; ========import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
pom
spring-cloud-starter-eureka ========spring-cloud-starter-netflix-eureka-client
spring-cloud-starter-hystrix========spring-cloud-starter-netflix-hystrix



zuul
spring-cloud-starter-eureka ===============spring-cloud-starter-netflix-eureka-client
spring-cloud-starter-zuul=================spring-cloud-starter-netflix-zuul


全部替换
<dependency>
            <groupId>org.springframework</groupId>
            <artifactId>springloaded</artifactId>
        </dependency>
========换成=================
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-loader</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-loader-tools</artifactId>
        </dependency>
```
# SpringCloud的使用

> 配合食用，效果更佳https://springcloud.cc/spring-cloud-dalston.html

如果需要查看该笔记完整版源码内容，请[移步](https://github.com/dongfangding/microservicecloud)
**写在前面的话**
* 项目最初是使用了`spring-boot`的`1.5.9.RELEASE`版本，`spring-cloud`的`Dalston.SR1`版本，后来虽然没有使用到新功能和特性，但还是尝试了一下新的版本，因此把`spring-boot`升级到了`2.0.3.RELEASE`版本，`spring-cloud`也升级到了最新的`Finchley.RELEASE`版本，升级之后才发现及时没有使用新特性，也有很多以前使用的类在新的版本已经过时。于是开始在项目中对过时的类进行替换操作，但是因为笔记在之前已经写好，替换的工作可能会有遗漏，所以有可能会出现对应的类找不到的问题，当然这种问题，只要在编译器里能够发现，就很容易解决了。

## 一、空项目的建立
> 因为需要使用到在IDEA中建立maven多module模块的功能，相关文档请参考IDEA新建maven多module模块项目.md
因为需要使用maven来构建多module模块项目，因此需要先建立一个空的项目，然后在空的项目里建立需要的模块，如父模块、子模块公共资源类，子模块业务类





## 二、父模块的建立
> 在上一步建立的空项目中新增一个模块，用作我们的父项目

### 1. 新增`microservicecloud-parent`
> `File->Project Structure->Modules` 新增maven module









### 2. 删除`src`目录
> 父工程主要提供统一的`jar`包管理， 不需要任何代码，因此可以把相关源码目录结构删掉




### 3. `pom.xml`文件处理
> 先来看一下当前的`pom.xml`文件的内容，可以看到就是一个正常的`pom`文件

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    <groupId>com.ddf.microserviecloud</groupId>
    <artifactId>microservicecloud-parent</artifactId>
    <version>1.0-SNAPSHOT</version>
</project>
```

> `dependencyManagement`的使用,使用`dependencyManagement`来统一管理所有子类依赖的`jar`包，这样所有子类继承了父模块之后不需要指定版本，只需要`groupId`和`artifactId`两个属性即可，父模块的`pom.xml`文件加入`jar`包之后如下，注意`<packaging>pom</packaging>`

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    <groupId>com.ddf.microserviecloud</groupId>
    <artifactId>microservicecloud-parent</artifactId>
    <version>1.0-SNAPSHOT</version>
    <packaging>pom</packaging>
    <properties>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
        <maven.compiler.source>1.8</maven.compiler.source>
        <maven.compiler.target>1.8</maven.compiler.target>
        <junit.version>4.12</junit.version>
        <log4j.version>1.2.17</log4j.version>
        <lombok.version>1.16.18</lombok.version>
    </properties>
    <dependencyManagement>
        <dependencies>
            <dependency>
                <groupId>org.springframework.cloud</groupId>
                <artifactId>spring-cloud-dependencies</artifactId>
                <version>Dalston.SR1</version>
                <type>pom</type>
                <scope>import</scope>
            </dependency>
            <dependency>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-dependencies</artifactId>
                <version>1.5.9.RELEASE</version>
                <type>pom</type>
                <scope>import</scope>
            </dependency>
            <dependency>
                <groupId>mysql</groupId>
                <artifactId>mysql-connector-java</artifactId>
                <version>5.0.4</version>
            </dependency>
            <dependency>
                <groupId>com.alibaba</groupId>
                <artifactId>druid</artifactId>
                <version>1.0.31</version>
            </dependency>
            <dependency>
                <groupId>org.mybatis.spring.boot</groupId>
                <artifactId>mybatis-spring-boot-starter</artifactId>
                <version>1.3.0</version>
            </dependency>
            <dependency>
                <groupId>ch.qos.logback</groupId>
                <artifactId>logback-core</artifactId>
                <version>1.2.3</version>
            </dependency>
            <dependency>
                <groupId>junit</groupId>
                <artifactId>junit</artifactId>
                <version>${junit.version}</version>
                <scope>test</scope>
            </dependency>
            <dependency>
                <groupId>log4j</groupId>
                <artifactId>log4j</artifactId>
                <version>${log4j.version}</version>
            </dependency>
        </dependencies>
    </dependencyManagement>
    
    <build>
        <finalName>microservicecloud-parent</finalName>
        <resources>
            <resource>
                <directory>src/main/resources</directory>
                <filtering>true</filtering>
            </resource>
        </resources>
        <plugins>
            <!-- 该插件的意思是能够使用$符号来获取各个模块位于src/main/resources里的资源文件的属性 -->
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-resources-plugin</artifactId>
                <configuration>
                    <delimiters>
                        <delimit>$</delimit>
                    </delimiters>
                </configuration>
            </plugin>
            <!-- 打包插件 -->
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
                <configuration>
                    <fork>true</fork>
                    <classifier>exec</classifier>
                </configuration>
                <executions>
                    <execution>
                        <goals>
                            <goal>repackage</goal>
                        </goals>
                    </execution>
                </executions>
            </plugin>
        </plugins>
    </build>
</project>
```

## 三、 建立公共资源子模块
> 公共资源子模块用来为我们其他所有的子模块提供统一的`POJO`类，资源文件类等共同使用的资源

### 1. 建立子模块`microservicecloud-api`
> 步骤省略，参考上面建立子模块的步骤，只是在确定`GAV`坐标的界面，有两个属性需要注意，`Add as module to`选择`none`和`Parent`选择之前我们的父项目`microservicecloud-parent`











### 2. 设置module的`Resources`和Sources`等
> `src/main/java` --> 设置为`Sources`， `src/main/resources->Resources`， `src/main/test ->Tests`





### 3. `pom.xml`文件的处理
> 通过`parent`继承了`microservicecloud-parent`，看实际情况是否需要引入在`microservicecloud-parent`中定义的`jar`包，根据需要实际引入，不需要指定版本号，因为之后使用到jdbcTemplate来查询对象，下面暂时导入了`spring-jdbc`包

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <parent>
        <artifactId>microservicecloud-parent</artifactId>
        <groupId>com.ddf.microserviecloud</groupId>
        <version>1.0-SNAPSHOT</version>
        <relativePath>../microservicecloud-parent/pom.xml</relativePath>
    </parent>
    <modelVersion>4.0.0</modelVersion>
    <artifactId>microservicecloud-api</artifactId>
    <dependencies>
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-jdbc</artifactId>
        </dependency>
    </dependencies>

     <!-- 打包插件 -->
    <build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
                <configuration>
                    <fork>true</fork>
                    <classifier>exec</classifier>
                </configuration>
                <executions>
                    <execution>
                        <goals>
                            <goal>repackage</goal>
                        </goals>
                    </execution>
                </executions>
            </plugin>
        </plugins>
    </build>
</project>
```

### 4. 建立`POJO`类`User.java`
> 因为使用到了JdbcTemplate查询对象的功能，所以实现了`RowMapper`接口

```java
package com.ddf.microservicecloud.api.entity;
import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;
/**
 * @author DDf on 2018/5/14
 */
public class User implements RowMapper {
    private Integer id;
    private String userName;
    private String password;
    private String gender;
    private String tel;
    private String dbSource;
    private Integer removed;
    private String errorMessage;
    public String getErrorMessage() {
        return errorMessage;
    }
    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
    public User() {
    }
    public User(Integer id, String userName, String password, String gender, String tel) {
        this.id = id;
        this.userName = userName;
        this.password = password;
        this.gender = gender;
        this.tel = tel;
        this.dbSource = "dbSource-01";
        this.removed = 0;
    }
    public User(Integer id, String userName, String password, String gender, String tel, String dbSource, Integer removed) {
        this.id = id;
        this.userName = userName;
        this.password = password;
        this.gender = gender;
        this.tel = tel;
        this.dbSource = dbSource;
        this.removed = removed;
    }
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getGender() {
        return gender;
    }
    public void setGender(String gender) {
        this.gender = gender;
    }
    public String getTel() {
        return tel;
    }
    public void setTel(String tel) {
        this.tel = tel;
    }
    public String getDbSource() {
        return dbSource;
    }
    public void setDbSource(String dbSource) {
        this.dbSource = dbSource;
    }
    public Integer getRemoved() {
        return removed;
    }
    public void setRemoved(Integer removed) {
        this.removed = removed;
    }
    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", gender='" + gender + '\'' +
                ", tel='" + tel + '\'' +
                ", dbSource='" + dbSource + '\'' +
                ", removed=" + removed +
                '}';
    }
    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
        User user = new User();
        user.setId(rs.getInt("id"));
        user.setUserName(rs.getString("userName"));
        user.setPassword(rs.getString("password"));
        user.setGender(rs.getString("gender"));
        user.setTel(rs.getString("tel"));
        user.setDbSource(rs.getString("dbSource"));
        user.setRemoved(rs.getInt("removed"));
        return user;
    }
}


```

## 四、建立用户服务提供者子模块
### 1. 模块建立
> 详细步骤省略，参考以上模块建立的步骤， 注意`Resources`和`Sources`等的设置




### 2. `pom.xml`文件的处理
> 当前`pom.xml`文件内容查看，已经继承父类`microservicecloud-parent`

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <parent>
        <artifactId>microservicecloud-parent</artifactId>
        <groupId>com.ddf.microserviecloud</groupId>
        <version>1.0-SNAPSHOT</version>
        <relativePath>../microservicecloud-parent/pom.xml</relativePath>
    </parent>
    <modelVersion>4.0.0</modelVersion>
    <artifactId>microservicecloud-provider</artifactId>
</project>
```

> 添加当前模块依赖`jar`包，因为当前模块需要使用到`microservicecloud-api`，因此需要把这个模块引入进来

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <parent>
        <artifactId>microservicecloud-parent</artifactId>
        <groupId>com.ddf.microserviecloud</groupId>
        <version>1.0-SNAPSHOT</version>
        <relativePath>../microservicecloud-parent/pom.xml</relativePath>
    </parent>
    <modelVersion>4.0.0</modelVersion>
    <artifactId>microservicecloud-provider</artifactId>
    <dependencies>
        <dependency>
            <groupId>com.ddf.microserviecloud</groupId>
            <artifactId>microservicecloud-api</artifactId>
            <version>1.0-SNAPSHOT</version>
        </dependency>
        <!-- actuator监控信息完善 -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-actuator</artifactId>
        </dependency>
        <!-- 将微服务provider侧注册进eureka -->
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
        </dependency>
        <dependency>
            <groupId>junit</groupId>
            <artifactId>junit</artifactId>
        </dependency>
        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
        </dependency>
        <dependency>
            <groupId>com.alibaba</groupId>
            <artifactId>druid</artifactId>
        </dependency>
        <dependency>
            <groupId>ch.qos.logback</groupId>
            <artifactId>logback-core</artifactId>
        </dependency>
        <dependency>
            <groupId>org.mybatis.spring.boot</groupId>
            <artifactId>mybatis-spring-boot-starter</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
        </dependency>
        <!-- 修改后立即生效，热部署 -->     
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-loader</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-loader-tools</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-devtools</artifactId>
        </dependency>
    </dependencies>

     <!-- 打包插件 -->
    <build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
                <configuration>
                    <fork>true</fork>
                    <classifier>exec</classifier>
                </configuration>
                <executions>
                    <execution>
                        <goals>
                            <goal>repackage</goal>
                        </goals>
                    </execution>
                </executions>
            </plugin>
        </plugins>
    </build>
</project>
```

### 3. 初步测试
> 目前我们先做一个初步的测试，用来验证各个模块的包是否能正常访问，是否能接收到浏览器的请求以及返回数据

#### 3.1. 配置`spring-boot`的`application.yml`或`application.xml`文件
> 在`src/main/resources`文件夹下新建`application.yml`文件，开启`debug`模式



> 
```properties
server:
  port: 8000 # 配置端口号
  context-path: /provider # 配置当前模块的context-path，即每个请求前都必须附加上这个路径
debug: true # 开启debug

```


#### 3.2 添加当前模块主启动类`ProviderApplication.java`
> 因为我们第一步是测试当前模块能不能正常启动，所以先不配置数据源，如果在启动的时候报找不到datasource，那是因为导入了相关的操作数据库的jar包开启了boot相关的自动配置功能，
那么只要在主启动类上加入`exclude `即可。这里做下记录功能，如果导包的时候在这里不导入相关jar包，则不需要用到这个功能。

```java
/**
 * @author DDf on 2018/5/14
 */
package com.ddf.microservicecloud.provider;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class, HibernateJpaAutoConfiguration.class})
public class ProviderApplication {
    public static void main(String[] args) {
        SpringApplication.run(ProviderApplication.class, args);
    }
}

```

> 启动后控制台部分效果如下
Exclusions:
 org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration
    org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration




#### 3.3. 编写一个用户的控制器来测试是否可以正常访问
> 测试项目是否可以正常访问，是否可以访问到我们自定义的`microservicecloud-poi`模块中定义的类

```java
package com.ddf.microservicecloud.provider.controller;
import com.ddf.microservicecloud.entity.User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
/**
 * @author DDf on 2018/5/14
 */
@RestController
@RequestMapping("user")
public class UserController {
    @RequestMapping("/test")
    public User test() {
        User user = new User(1, "ddf", "123456", "男", "18300001111");
        return user;
    }
}
```

#### 3.4. 访问请求
> 访问如下请求http://localhost:8000/provider/user/test
浏览器响应如下，证明我们的测试是通过的


```json
{
    id: 1,
    userName: "ddf",
    password: "123456",
    gender: "男",
    tel: "18300001111"，
    dbSource: "dbSource-01"，
    removed: 0
}
```

### 4. 配置数据源
> 这里我们使用alibaba的`durid`来连接数据库

> 新建一个`mysql`的本地数据库`microservicecloud`，创建一个`TABLE User`






> 配置`application.yml`加入`datasource`相关的配置
```
server:
  port: 8000 # 配置端口号
  context-path: /provider # 配置当前模块的context-path，即每个请求前都必须附加上这个路径
debug: true # 开启debug

spring:
  datasource:
    type: com.alibaba.druid.pool.DruidDataSource    # 当前数据源操作类型
    driver-class-name: com.mysql.cj.jdbc.Driver
    url: jdbc:mysql://localhost:3306/microservicecloud?serverTimezone=UTC # 数据库名称,暂时我这个版本不加serverTimezone会报一个错误
    username: root
    password: 123456
    dbcp2:
      min-idle: 5                  # 数据库连接池的最小维持连接数
      initial-size: 5            # 初始化连接数
      max-total: 5                # 最大连接数
      max-wait-millis: 200      # 等待连接获取的最大超时时间
```

> <font color="red">注意，配置好数据源之后一定要去掉我们之前在主启动类上配置的exclude </font>,现在完整版主启动类如下

```java
/**
* @author DDf on 2018/5/14
*/
package com.ddf.microservicecloud.provider;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;

@SpringBootApplication
public class ProviderApplication {
    public static void main(String[] args) {
        SpringApplication.run(ProviderApplication.class, args);
    }
}

```

### 5. 建立用户相关的业务操作类
#### 5.1 新建`User`相关的`DAO`类
> 建立UserDao接口

```java
package com.ddf.microservicecloud.provider.dao;
import com.ddf.microservicecloud.entity.User;
import java.util.List;
import java.util.Map;
/**
 * @author DDf on 2018/5/14
 */
public interface UserDao {
    List<User> findAll();
    User getOne(Integer id);
    List<Map<String, Object>> findAllMap();
}


```
> 实现`UserDao`接口类，建立`UserDaoImpl.java`

```java
package com.ddf.microservicecloud.provider.dao.impl;
import com.ddf.microservicecloud.dao.UserDao;
import com.ddf.microservicecloud.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
/**
 * @author DDf on 2018/5/14
 */
@Repository
public class UserDaoImpl implements UserDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Override
    public List<User> findAll() {
        String sql = "SELECT * FROM USER  WHERE REMOVED = 0";
        return jdbcTemplate.query(sql, new User());
    }
    @Override
    public User getOne(Integer id) {
        String sql = "SELECT * FROM USER WHERE REMOVED = 0 AND ID = " + id;
        return (User) jdbcTemplate.queryForObject(sql, new User());
    }
    @Override
    public List<Map<String, Object>> findAllMap() {
        String sql = "SELECT * FROM USER WHERE REMOVED = 0 ";
        List<Map<String, Object>> mapList = new ArrayList<>();
        mapList = jdbcTemplate.queryForList(sql, mapList);
        return mapList;
    }
}
```

#### 5.2 新建`User`的`service`层
> 新建`UserService`接口类

```java
package com.ddf.microservicecloud.provider.service;
import com.ddf.microservicecloud.entity.User;
import java.util.List;
import java.util.Map;
/**
 * @author DDf on 2018/5/14
 */
public interface UserService {
    List<User> queryAll();
    User queryOne(Integer id);
    List<Map<String, Object>> queryAllMap();
}


```

> 实现`UserService`的接口类，`UserServiceImpl.java`

```java
package com.ddf.microservicecloud.provider.service.impl;
import com.ddf.microservicecloud.dao.UserDao;
import com.ddf.microservicecloud.entity.User;
import com.ddf.microservicecloud.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;
/**
 * @author DDf on 2018/5/14
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;
    @Override
    public List<User> queryAll() {
        return userDao.findAll();
    }
    @Override
    public User queryOne(Integer id) {
        return userDao.getOne(id);
    }
    @Override
    public List<Map<String, Object>> queryAllMap() {
        return userDao.findAllMap();
    }
}

```

#### 5.3 创建用户的测试类
> 测试类的所有测试类一定要保持和主启动类相同的包路径，否则运行会报错，先建立测试类的基类

```java
package com.ddf.microservicecloud.provider;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
/**
 * @author DDf on 2018/5/14
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class ProviderApplicationTest {
}
```

> 建立`UserService`的测试类`UserServiceTest`，注意要保持和`UserService`相同的包路径

```java
package com.ddf.microservicecloud.provider.service;
import com.ddf.microservicecloud.ApplicationTest;
import com.ddf.microservicecloud.entity.User;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
/**
 * @author DDf on 2018/5/14
 */
public class UserServiceTest extends ProviderApplicationTest {
    @Autowired
    private UserService userService;
    @Test
    public void testQueryAll() {
        List<User> users = userService.queryAll();
        if (!users.isEmpty()) {
            users.forEach(user -> System.out.println(user.toString()));
        }
    }
    @Test
    public void testQueryOne() {
        System.out.println(userService.queryOne(1));
    }
}
```

> 运行测试类






#### 5.4 创建用户的控制层类
> 之前在我们的基础测试中已经建立了这个类，现在我们只需要添加我们在`service`中新加入的方法，完整版如下

```java
package com.ddf.microservicecloud.provider.controller;
import com.ddf.microservicecloud.entity.User;
import com.ddf.microservicecloud.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import java.util.Map;
/**
 * @author DDf on 2018/5/14
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
    @RequestMapping("/test")
    public User test() {
        User user = new User(1, "ddf", "123456", "男", "18300001111");
        return user;
    }
    @RequestMapping("/list")
    public List<User> userList() {
        return userService.queryAll();
    }
    @RequestMapping("/user/{id}")
    public User user(@PathVariable Integer id) {
        return userService.queryOne(id);
    }
    @RequestMapping("maplist")
    public List<Map<String, Object>> userMapList() {
        return userService.queryAllMap();
    }
}
```

#### 5.5 测试`UserController`相关功能

> 访问如下请求 http://localhost:8000/provider/user/list 获得所有的用户列表




> 获取指定用户 http://localhost:8000/provider/user/user/1





#### 服务端第一阶段完成



## 五、建立用户服务消费者子模块
### 1. 模块建立
> 详细步骤省略，参考以上模块建立的步骤， 注意`Resources`和`Sources`等的设置






### 2. `pom.xml`文件配置
> 引入父模块`microservicecloud-parent`和公共api模块`microservicelcoud-api`

```java
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <parent>
        <artifactId>microservicecloud-parent</artifactId>
        <groupId>com.ddf.microserviecloud</groupId>
        <version>1.0-SNAPSHOT</version>
        <relativePath>../microservicecloud-parent/pom.xml</relativePath>
    </parent>
    <modelVersion>4.0.0</modelVersion>

    <artifactId>microservicecloud-consumer</artifactId>
    <description>微服务消费者client端</description>

    <dependencies>
        <!-- 自己定义的api -->
        <dependency>
            <groupId>com.ddf.microserviecloud</groupId>
            <artifactId>microservicecloud-api</artifactId>
            <version>${project.version}</version>
        </dependency>

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>

        <!-- 修改后立即生效，热部署 -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-loader</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-loader-tools</artifactId>
        </dependency>


        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-devtools</artifactId>
        </dependency>
    </dependencies>

     <!-- 打包插件 -->
    <build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
                <configuration>
                    <fork>true</fork>
                    <classifier>exec</classifier>
                </configuration>
                <executions>
                    <execution>
                        <goals>
                            <goal>repackage</goal>
                        </goals>
                    </execution>
                </executions>
            </plugin>
        </plugins>
    </build>

</project>
```

### 3. 配置`application.yml`文件
> 在`src/main/resources`下建立配置文件`application.yml`文件



```
server:
  port: 8001
  context-path: /consumer
```

### 4. 编写客户端的主启动类`ConsumerApplication.java`


```java
package com.ddf.microservicecloud.consumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author DDf on 2018/5/15
 */
@SpringBootApplication
public class ConsumerApplication {
    public static void main(String[] args) {
        SpringApplication.run(ConsumerApplication.class, args);
    }
}
```

### 5. 编写配置类`ConsumerConfiguration`，注册`RestTemplate`
> `spring-boot`建议使用注解形式来替换原来繁杂的配置文件，使用`@Configuration`标注在类上，即可标名这是一个配置类

```java
package com.ddf.microservicecloud.consumer.configuration;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

/**
 * @author DDf on 2018/5/15
 * 配置类
 */
@Configuration
public class ConsumerConfiguration {

    /**
     * @Bean表明这是要向spring上下文注册一个bean，bean的名称为方法名
     * RestTemplate为spring封装的一个专门用于Restful风格的客户端请求工具类
     *
     * @LoadBalanced 这个注解是基于ribbon的一个负载均衡的类，使用这个注解之后向服务端的请求则只能使用服务名，默认
     * 负载均衡的实现算法是轮循，即向这个服务名下所有可用的地址轮流发送请求
     *
     * @Primary 因为配置了多个相同类型的RestTemplate的Bean，如果在注入Bean未明确指定名称，会报错，因此使用这个注解
     * 来表明这个是默认注入
     *
     * 默认注入的RestTemplate的connectionTimeOut和readTimeOut都是-1
     *
     * @return
     */
    @Bean
    @LoadBalanced
    @Primary
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    /**
     * 对于某些外部系统的调用，如果不能因为长时间的错误挂起而影响主流程请求时间，可以定义超时时间
     * @return
     */
    @Bean
    @LoadBalanced
    public RestTemplate timeOutRestTemplate() {
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        requestFactory.setConnectTimeout(10000);
        requestFactory.setReadTimeout(30000);
        return new RestTemplate(requestFactory);
    }

    /**
     * 对于某些非Eureka服务的请求，可以去除@LoadBalanced注解
     * @return
     */
    @Bean
    public RestTemplate urlFactoryRestTemplate() {
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        requestFactory.setConnectTimeout(10000);
        requestFactory.setReadTimeout(30000);
        return new RestTemplate(requestFactory);
    }
}

```

### 6. 配置属性文件，增加一个调用`microservicecloud-provider`的`url`地址
> 因为现在客户端需要调用之前服务端编写的接口，所以我们需要编写一个配置文件用来专门保存这些地址
在`src/main/resources`下新建`API.properties`文件



```
# 服务端provider用户的接口
provider-user=http://localhost:8000/provider/user
```

### 7. 解析配置文件`API.properties`内容
> 新建一个`java`类来对应解析`API.properties`文件的内容，代码如下

```java
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

```

### 8. 编写客户端的`UserController`来测试接口
> 客户端现在来调用我们之前在`microservicecloud-provider`中定义的接口，因此我们需要先启动该服务
客户端`UserController.java`代码如下
```java
package com.ddf.microservicecloud.consumer.controller;
import com.ddf.microservicecloud.api.Api;
import com.ddf.microservicecloud.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import java.util.List;
/**
 * @author DDf on 2018/5/15
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private Api api;
    @RequestMapping("/list")
    public List<User> userList() {
        List<User> userList = restTemplate.postForObject(api.getProviderUser() + "/list", null, List.class);
        return userList;
    }
}
```

### 9. 运行测试结果
> 确保服务端`microservicecloud-provider`已启动，然后启动客户端的服务，按照`UserController`的代码，我们需要调用服务端的查询所有用户的列表的功能
http://localhost:8001/consumer/user/list ，结果如下，可以看到我们的客户端可以正确访问到服务端的接口服务。



## 六、建立`Eureka`服务端子模块
> 上面五个章节简单讲述了多个模块是如何构建来引用以及我们的客户端是如何来调用我们服务端的微服务的，这个章节开始引用`Eureka`。
关于`Eureka`的详细信息，[请参考文档](https://springcloud.cc/spring-cloud-dalston.html#spring-cloud-eureka-server)

### 1. 新建模块`microservicecloud-eureka`,同样继承父类`microservicecloud-parent`
> 详细步骤参考前面






### 2. `pom.xml`文件中引入`Eureka`服务端依赖
```
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <parent>
        <artifactId>microservicecloud-parent</artifactId>
        <groupId>com.ddf.microserviecloud</groupId>
        <version>1.0-SNAPSHOT</version>
        <relativePath>../microservicecloud-parent/pom.xml</relativePath>
    </parent>
    <modelVersion>4.0.0</modelVersion>
    <artifactId>microservicecloud-eureka</artifactId>
    <dependencies>
        <!--eureka-server服务端 -->
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-netflix-eureka-server</artifactId>
        </dependency>
        <!-- 修改后立即生效，热部署 -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-loader</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-loader-tools</artifactId>
        </dependency>

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-devtools</artifactId>
        </dependency>
    </dependencies>
    
     <!-- 打包插件 -->
    <build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
                <configuration>
                    <fork>true</fork>
                    <classifier>exec</classifier>
                </configuration>
                <executions>
                    <execution>
                        <goals>
                            <goal>repackage</goal>
                        </goals>
                    </execution>
                </executions>
            </plugin>
        </plugins>
    </build>
</project>
```

### 3. 编写并配置`application.yml`文件
> 在`src/main/resources`下建立配置文件`application.yml`



```
server:
  port: 7000 # 端口号
eureka:
  instance:
    hostname: eureka7000.com   #eureka服务端的实例名称,如果本地测试，可以通过修改host文件来增加一个对本机ip映射的主机名
  client:
    register-with-eureka: false #是否将当前模块注册到Eureka服务端上，因为当前模块就是Eureka服务端，所以不需要注册
    fetch-registry: false #false表示自己端就是注册中心，我的职责就是维护服务实例，并不需要去检索服务
    service-url:
      defaultZone: http://${eureka.instance.hostname}:${server.port}/eureka/ #设置与Eureka Server交互的地址查询服务和注册服务都需要依赖这个地址（单机）。
```
> 修改本机hosts文件





### 4. 编写主启动类`EurekaApplication.java`
```java
package com.ddf.microservicecloud.eureka;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
/**
 * @author DDf on 2018/5/15
 * Eureka服务端的主启动类
 * @SpringBootApplication 这是一个springboot的启动类
 * @EnableEurekaServer EurekaServer服务器端启动类,接受其它微服务注册进来
 */
@SpringBootApplication
@EnableEurekaServer
public class EurekaApplication {
    public static void main(String[] args) {
        SpringApplication.run(EurekaApplication.class, args);
    }
}
```

### 5. 访问`Eureka`管理界面
> 我这里测试出现了一个问题，访问之后没有出现管理界面而是一段XML。 如下图,这个问题的原因最终没有明确是为什么，有网上说需要删除`freemarker-2.3.25-incubating.jar`，
但是试了没行，最后又试了maven clean install 。最后就可以了，最终不确定是哪个步骤修复了问题。




> 如下访问http://eureka7000.com:7000/，管理界面就出来了，说明我们的`Eureka`是成功的。只不过还没有服务注册进来。






## 七、注册`Eureka`
### 1. 在`microservice-provider`服务提供者中引入`Eurake`
> 我们首先来解决服务端来使用`Eurake`的问题，只有先把服务端的微服务注册到了`Eureka`上，我们的客户端才能通过`Eureka`功能来调用服务
[参考文档](https://springcloud.cc/spring-cloud-dalston.html#_service_discovery_eureka_clients)

#### 1.1 `pom.xml`文件中引入`Eureka`依赖，此时完整版`pom`如下
```
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <parent>
        <artifactId>microservicecloud-parent</artifactId>
        <groupId>com.ddf.microserviecloud</groupId>
        <version>1.0-SNAPSHOT</version>
        <relativePath>../microservicecloud-parent/pom.xml</relativePath>
    </parent>
    <modelVersion>4.0.0</modelVersion>
    <artifactId>microservicecloud-provider</artifactId>
    <description>微服务提供者Server端</description>
    <dependencies>
        <!-- 引入我们自定义的模块 -->
        <dependency>
            <groupId>com.ddf.microserviecloud</groupId>
            <artifactId>microservicecloud-api</artifactId>
            <version>1.0-SNAPSHOT</version>
        </dependency>
        <dependency>
            <groupId>junit</groupId>
            <artifactId>junit</artifactId>
        </dependency>
        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
        </dependency>
        <dependency>
            <groupId>com.alibaba</groupId>
            <artifactId>druid</artifactId>
        </dependency>
        <dependency>
            <groupId>ch.qos.logback</groupId>
            <artifactId>logback-core</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
        </dependency>
        <!-- 修改后立即生效，热部署 -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-loader</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-loader-tools</artifactId>
        </dependency>

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-devtools</artifactId>
        </dependency>
        <!-- 引入Eureka -->
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
        </dependency>
    </dependencies>

     <!-- 打包插件 -->
    <build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
                <configuration>
                    <fork>true</fork>
                    <classifier>exec</classifier>
                </configuration>
                <executions>
                    <execution>
                        <goals>
                            <goal>repackage</goal>
                        </goals>
                    </execution>
                </executions>
            </plugin>
        </plugins>
    </build>
</project>
```

#### 1.2 修改配置文件`application.yml`
> 加入`Eureka`相关的配置，把当前微服务注册到我们自己的`Eureka`服务中

```
server:
  port: 8000 # 配置端口号
  context-path: /provider # 配置当前模块的context-path，即每个请求前都必须附加上这个路径
debug: true # 开启debug
spring:
  application:
    name: microservicecloud-provider # 在使用eurake功能的时候非常重要，这个就是该服务的实例名，eurake可以通过该实例名获得这个服务的其中一个实例（服务端集群）
  datasource:
    type: com.alibaba.druid.pool.DruidDataSource # 当前数据源操作类型
    driver-class-name: com.mysql.cj.jdbc.Driver
    url: jdbc:mysql://localhost:3306/microservicecloud?serverTimezone=UTC # 数据库名称,暂时我这个版本不加serverTimezone会报一个错误
    username: root
    password: 123456
    dbcp2:
      min-idle: 5 # 数据库连接池的最小维持连接数
      initial-size: 5 # 初始化连接数
      max-total: 5 # 最大连接数
      max-wait-millis: 200 # 等待连接获取的最大超时时间
eureka:
  client: # 当前微服务只是eureka的客户端
    service-url:
      defaultZone: http://eureka7000.com:7000/eureka # 将当前微服务注册到指定的eureka服务端中
```

#### 1.3 修改主启动类`ProviderApplication.java`
> 使用注解`@EnableEurekaClient`来实现客户端向服务端的服务注册，对应服务端的`@EnableEurekaServer`来允许服务注册

```java
package com.ddf.microservicecloud.provider;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
/**
 * @author DDf on 2018/5/14
 * @EnableEurekaClient 表明当前微服务是Eureka的客户端，将当前微服务注册到Eureka服务端中
 */
@SpringBootApplication
@EnableEurekaClient
public class ProviderApplication {
    public static void main(String[] args) {
        SpringApplication.run(ProviderApplication.class, args);
    }
}
```

#### 1.4 验证`microservice-provider`是否成功注册进`Eureka`
> 首先启动 `Eureka`服务端`microservicecloud-eureka`， 然后启动服务端`microservicecloud-provider`。再进入`Eureka`的管理界面来看一下，可以看到下面的服务列表里已经有了我们刚刚注册的`microservicecloud-provider`，同时为什么下面还有一个UNKNOWN以及上面的一串红色的警告,这个就是`Eureka`的自我保护机制。




#### 1.5 `Eureka`的自我保护机制
> 参考上图就是自我保护机制的效果，`Eureka`会每隔一段时间，默认15分钟就会向所有的注册服务发送心跳测试，如果这个服务能够在指定时间内保持心跳，则这个服务就会是一个健康的服务，然而并不是所有的服务都能够与服务端保持长时间的连接操作，而这段时间的无操作,`Eureka`就会认为这个服务是不健康的，就会中断集群之间的数据同步，但是`Eureka`并不会删除这个认为不健康的连接，因为不健康的连接并不总是一个down掉的服务，如果网络恢复后，那么这个服务就会恢复正常，`Eureka`又会把这个服务当作一个健康的服务，通过这种机制，可以最大限度的保证不会因为网络问题而导致的注册卡顿和服务误删的操作，同时开启同步多个Eureka之间的注册服务。(纯手写，详细介绍请参考官方文档)
<font color="red">注意：经过测试，这种自我保护的机制在集群时会出现一个严重的问题，即在eureka集群了三个相同的服务，当其中两个挂掉之后，客户端依然会请求到这个已经down掉的服务，负载均衡算法会在请求失败之后，根据算法优化以后避免这个请求也好，还是别的也好，但开始的一段时间内，如果请求到这个服务，会出现错误。</font>

### 2. `microservicecloud-consumer`中服务消费者客户端来使用`Ribbon`来完成负载均衡
> 之前我们已经完成了客户端来调用服务端提供的服务来正确返回数据，但那个时候的测试我们是通过`url`这种固定的方式来硬编码，而且不适用于如果服务端做了集群的情况，因此在这个章节开始在服务消费者客户端使用`Ribbon`来完成我们客户端的负载均衡，`Ribbon`依赖于`Eureka`，因此我们需要在客户端引入`eureka`，来调用我们的微服务提供者的接口

#### 2.1 `pom.xml`文件加入`eureka`依赖
```
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <parent>
        <artifactId>microservicecloud-parent</artifactId>
        <groupId>com.ddf.microserviecloud</groupId>
        <version>1.0-SNAPSHOT</version>
        <relativePath>../microservicecloud-parent/pom.xml</relativePath>
    </parent>
    <modelVersion>4.0.0</modelVersion>
    <artifactId>microservicecloud-consumer</artifactId>
    <description>微服务消费者client端</description>
    <dependencies>
        <!-- 自己定义的api -->
        <dependency>
            <groupId>com.ddf.microserviecloud</groupId>
            <artifactId>microservicecloud-api</artifactId>
            <version>${project.version}</version>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        <!-- 引入Eureka -->
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
        </dependency>
        <!-- 修改后立即生效，热部署 -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-loader</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-loader-tools</artifactId>
        </dependency>

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-devtools</artifactId>
        </dependency>
    </dependencies>

     <!-- 打包插件 -->
    <build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
                <configuration>
                    <fork>true</fork>
                    <classifier>exec</classifier>
                </configuration>
                <executions>
                    <execution>
                        <goals>
                            <goal>repackage</goal>
                        </goals>
                    </execution>
                </executions>
            </plugin>
        </plugins>
    </build>
</project>
```
#### 2.2 修改配置文件`application.yml`
> 加入eureka相关配置如下

```
server:
  port: 8001 # 当前微服务的端口号
  context-path: /consumer #每个请求都必须加上这个路径
eureka:
  client:
    register-with-eureka: false #当前模块是服务消费者，只需要调用服务提供者的接口接口，而服务提供者已经注册，所以这里不需要注册到eureka
    service-url:
      defaultZone: http://eureka7000.com:7000/eureka
```
#### 2.3 修改主启动类`ConsumerApplication.java`
> 加入注解`@EnableEurekaClient `

```java
package com.ddf.microservicecloud.consumer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
/**
 * @author DDf on 2018/5/15
 * @EnableEurekaClient 表明当前模块是eureka的客户端
 */
@SpringBootApplication
@EnableEurekaClient
public class ConsumerApplication {
    public static void main(String[] args) {
        SpringApplication.run(ConsumerApplication.class, args);
    }
}
```

#### 2.4 修改配置类`ConsumerConfiguration`
> 之前注入的`RestTemplate`需要加入了一个注解`@LoadBalanced`。这个注解是基于ribbon的一个负载均衡的类，使用这个注解之后向服务端的请求则只能使用服务名，默认
     * 负载均衡的实现算法是轮循，即向这个服务名下所有可用的地址轮流发送请求

```java
package com.ddf.microservicecloud.consumer.configuration;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

/**
 * @author DDf on 2018/5/15
 * 配置类
 */
@Configuration
public class ConsumerConfiguration {

    /**
     * @Bean表明这是要向spring上下文注册一个bean，bean的名称为方法名
     * RestTemplate为spring封装的一个专门用于Restful风格的客户端请求工具类
     *
     * @LoadBalanced 这个注解是基于ribbon的一个负载均衡的类，使用这个注解之后向服务端的请求则只能使用服务名，默认
     * 负载均衡的实现算法是轮循，即向这个服务名下所有可用的地址轮流发送请求
     *
     * @Primary 因为配置了多个相同类型的RestTemplate的Bean，如果在注入Bean未明确指定名称，会报错，因此使用这个注解
     * 来表明这个是默认注入
     *
     * 默认注入的RestTemplate的connectionTimeOut和readTimeOut都是-1
     *
     * @return
     */
    @Bean
    @LoadBalanced
    @Primary
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    /**
     * 对于某些外部系统的调用，如果不能因为长时间的错误挂起而影响主流程请求时间，可以定义超时时间
     * @return
     */
    @Bean
    @LoadBalanced
    public RestTemplate timeOutRestTemplate() {
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        requestFactory.setConnectTimeout(10000);
        requestFactory.setReadTimeout(30000);
        return new RestTemplate(requestFactory);
    }

    /**
     * 对于某些非Eureka服务的请求，可以去除@LoadBalanced注解
     * @return
     */
    @Bean
    public RestTemplate urlFactoryRestTemplate() {
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        requestFactory.setConnectTimeout(10000);
        requestFactory.setReadTimeout(30000);
        return new RestTemplate(requestFactory);
    }
}
```



#### 2.5 修改`API.properties`配置文件
> `src/main/resources/API.properties`，这个文件之前保留了我们通过Url调用服务端接口的请求地址，因为使用了`@LoadBalanced`注解之后调用微服务需要通过`eureka`，我们只能通过微服务名称来访问，因此这里直接覆盖原来的地址

```
provider-user=http://microservicecloud-provider/provider/user # 服务端provider用户的接口
```

#### 2.6 测试接口
> 启动`eureka`服务端`microservicecloud-eureka`，
启动微服务提供者`microservicecloud-provider`
启动微服务消费者`microservicecloud-consumer`
查看`eureka`管理界面，服务正常注册进来
访问链接： http://localhost:8001/consumer/user/list， 返回结果如下图,正常访问







### 3. `Eureka`服务发现
> 暂时忘记了内容，只记得不是特别重要的东西，以后有精力补充


#### 4. 细节优化
> 问题1， 希望能够在鼠标悬停在具体微服务的时候能够显示该微服务部署的ip地址,未修改前情况如下




> 修改`application.yml`，添加`instance`

```
server:
  port: 8000 # 配置端口号
  context-path: /provider # 配置当前模块的context-path，即每个请求前都必须附加上这个路径
debug: false
spring:
  application:
    name: microservicecloud-provider # 在使用eurake功能的时候非常重要，这个就是该服务的实例名，eurake可以通过该实例名获得这个服务的其中一个实例（服务端集群）
  datasource:
    type: com.alibaba.druid.pool.DruidDataSource # 当前数据源操作类型
    driver-class-name: com.mysql.cj.jdbc.Driver
    url: jdbc:mysql://localhost:3306/microservicecloud?serverTimezone=UTC # 数据库名称,暂时我这个版本不加serverTimezone会报一个错误
    username: root
    password: 123456
    dbcp2:
      min-idle: 5 # 数据库连接池的最小维持连接数
      initial-size: 5 # 初始化连接数
      max-total: 5 # 最大连接数
      max-wait-millis: 200 # 等待连接获取的最大超时时间
eureka:
  client: # 当前微服务只是eureka的客户端
    service-url:
      defaultZone: http://eureka7000.com:7000/eureka,http://eureka7001.com:7001/eureka,http://eureka7002.com:7002/eureka # 将当前微服务注册到指定的eureka服务端中
  instance:
      instance-id: microservicecloud-provider
      prefer-ip-address: true #访问路径可以显示IP地址
```





> 问题2：即问题1同图，点击这个连接之后，会有一个报错空白界面，我们来构建一下每个这个微服务提供者的info消息




> 修改`pom.xml`文件，增加依赖`spring-boot-starter-actuator`

```
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <parent>
        <artifactId>microservicecloud-parent</artifactId>
        <groupId>com.ddf.microserviecloud</groupId>
        <version>1.0-SNAPSHOT</version>
        <relativePath>../microservicecloud-parent/pom.xml</relativePath>
    </parent>
    <modelVersion>4.0.0</modelVersion>
    <artifactId>microservicecloud-provider</artifactId>
    <description>微服务提供者Server端</description>
    <dependencies>
        <!-- 引入我们自定义的模块 -->
        <dependency>
            <groupId>com.ddf.microserviecloud</groupId>
            <artifactId>microservicecloud-api</artifactId>
            <version>1.0-SNAPSHOT</version>
        </dependency>
        <dependency>
            <groupId>junit</groupId>
            <artifactId>junit</artifactId>
        </dependency>
        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
        </dependency>
        <dependency>
            <groupId>com.alibaba</groupId>
            <artifactId>druid</artifactId>
        </dependency>
        <dependency>
            <groupId>ch.qos.logback</groupId>
            <artifactId>logback-core</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
        </dependency>
        <!-- 修改后立即生效，热部署 -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-loader</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-loader-tools</artifactId>
        </dependency>

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-devtools</artifactId>
        </dependency>
        <!-- 引入Eureka -->
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
        </dependency>
        <!-- 用于监控信息完善，如构建Info信息 -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-actuator</artifactId>
        </dependency>
    </dependencies>
    <build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
                <configuration>
                    <fork>true</fork>
                    <classifier>exec</classifier>
                </configuration>
                <executions>
                    <execution>
                        <goals>
                            <goal>repackage</goal>
                        </goals>
                    </execution>
                </executions>
            </plugin>
        </plugins>
    </build>
</project>
```

> 修改`application.yml`文件,增加`info`信息

```
server:
  port: 8000 # 配置端口号
  context-path: /provider # 配置当前模块的context-path，即每个请求前都必须附加上这个路径
debug: false
spring:
  application:
    name: microservicecloud-provider # 在使用eurake功能的时候非常重要，这个就是该服务的实例名，eurake可以通过该实例名获得这个服务的其中一个实例（服务端集群）
  datasource:
    type: com.alibaba.druid.pool.DruidDataSource # 当前数据源操作类型
    driver-class-name: com.mysql.cj.jdbc.Driver
    url: jdbc:mysql://localhost:3306/microservicecloud?serverTimezone=UTC # 数据库名称,暂时我这个版本不加serverTimezone会报一个错误
    username: root
    password: 123456
    dbcp2:
      min-idle: 5 # 数据库连接池的最小维持连接数
      initial-size: 5 # 初始化连接数
      max-total: 5 # 最大连接数
      max-wait-millis: 200 # 等待连接获取的最大超时时间
eureka:
  client: # 当前微服务只是eureka的客户端
    service-url:
      defaultZone: http://eureka7000.com:7000/eureka,http://eureka7001.com:7001/eureka,http://eureka7002.com:7002/eureka # 将当前微服务注册到指定的eureka服务端中
  instance:
      instance-id: microservicecloud-provider
      prefer-ip-address: true #访问路径可以显示IP地址
info:
  app-name: microservicecloud-provider
  copyright: ddf
  description: "微服务提供者"
```


## 八、`Eureka`集群
> 这个章节我们开始来配置`eureka`的集群，因为集群需要牵扯到在多台服务器上操作，但因为本地测试只有localhost可用，因此我们需要修改`hosts`文件来对本机ip来增加域名映射，来模拟区分表示多台服务器.`hosts`文件位于`C:\Windows\System32\drivers\etc`，内容修改如下






### 1. 集群方式之复制项目
> 集群最麻烦的方法当然也是看起来最容易理解的方式就是复制多个项目，比如对`eureka`进行集群三个，那么就再复制两个项目，然后再三个项目中分别对配置文件进行修改
嗯，懒得写了，参考下面步骤的配置文件，复制项目分别编辑打包即可。



### 2. 集群方式之利用外部配置文件
> 这种不需要复制多个项目，只需要一个样本一样的`eureka`模块，保证可用之后，打包成`jar`，复制三个`jar`包之后，分别放到不同的文件夹,然后把基本的配置文件在目录下复制一份，再修改每个单独文件夹下的配置文件，即可完成`jar`包里的配置文件的复写,然后使用`java -jar xxx`来对jar包进行编译启动即可



> 每个文件夹下都有一个一模一样的`jar`包以及一个可以复写`jar`包里配置文件的`application.yml`文件




> 修改eureka7000的配置文件（其实这个可以当做样本，在打包之前就已经修改了里面的配置文件，然后这个默认的就不会使用外部配置文件了，其它的两个再用外部配置文件复写）
`application.yml`内容如下

```
server:
  port: 7000 # 端口号
eureka:
  instance:
    hostname: eureka7000.com #eureka服务端的实例名称,如果本地测试，可以通过修改host文件来增加一个对本机ip映射的主机名
  client:
    register-with-eureka: false #是否将当前模块注册到Eureka服务端上，因为当前模块就是Eureka服务端，所以不需要注册
    fetch-registry: false #false表示自己端就是注册中心，我的职责就是维护服务实例，并不需要去检索服务
    service-url:
      # defaultZone: http://${eureka.instance.hostname}:${server.port}/eureka/ #一个eureka使用，设置与Eureka Server交互的地址查询服务和注册服务都需要依赖这个地址（单机）。
      # 配置另外两个eureka的注册地址，多个用逗号，另外的eureka的代码与这个一样，只是当前这个配置文件不一样，用于区分端口号，
            # eureka配置地址，每个eureka服务端的配置文件这个属性都不包含自己，包含另外两个即可。
      defaultZone: http://eureka7001.com:7001/eureka,http://eureka7002.com:7002/eureka

```

> 修改eureka7001的配置文件`application.yml`，内容如下

```
server:
  port: 7001 # 端口号
eureka:
  instance:
    hostname: eureka7001.com #eureka服务端的实例名称,如果本地测试，可以通过修改host文件来增加一个对本机ip映射的主机名
  client:
    register-with-eureka: false #是否将当前模块注册到Eureka服务端上，因为当前模块就是Eureka服务端，所以不需要注册
    fetch-registry: false #false表示自己端就是注册中心，我的职责就是维护服务实例，并不需要去检索服务
    service-url:
      # defaultZone: http://${eureka.instance.hostname}:${server.port}/eureka/ #设置与Eureka Server交互的地址查询服务和注册服务都需要依赖这个地址（单机）。
        # 配置另外两个eureka的注册地址，多个用逗号，另外的eureka的代码与这个一样，只是当前这个配置文件不一样，用于区分端口号，
            # eureka配置地址，每个eureka服务端的配置文件这个属性都不包含自己，包含另外两个即可。      
        defaultZone: http://eureka7000.com:7000/eureka,http://eureka7002.com:7002/eureka
``` 

> 修改eureka7002的配置文件`application.yml`，内容如下

```
server:
  port: 7002 # 端口号
eureka:
  instance:
    hostname: eureka7002.com #eureka服务端的实例名称,如果本地测试，可以通过修改host文件来增加一个对本机ip映射的主机名
  client:
    register-with-eureka: false #是否将当前模块注册到Eureka服务端上，因为当前模块就是Eureka服务端，所以不需要注册
    fetch-registry: false #false表示自己端就是注册中心，我的职责就是维护服务实例，并不需要去检索服务
    service-url:
      #defaultZone: http://${eureka.instance.hostname}:${server.port}/eureka/ #设置与Eureka Server交互的地址查询服务和注册服务都需要依赖这个地址（单机）。
            # 配置另外两个eureka的注册地址，多个用逗号，另外的eureka的代码与这个一样，只是当前这个配置文件不一样，用于区分端口号，
            # eureka配置地址，每个eureka服务端的配置文件这个属性都不包含自己，包含另外两个即可。      
      defaultZone: http://eureka7000.com:7000/eureka,http://eureka7001.com:7001/eureka
```

### 3. 修改微服务提供者`microservicecloud-provider`的配置文件`application.yml`
> 之前是把服务注册到其中一个eureka中，现在有三个eureka，所以要修改对应的配置文件将当前服务注册到三个eureka中

```
server:
  port: 8000 # 配置端口号
  context-path: /provider # 配置当前模块的context-path，即每个请求前都必须附加上这个路径
debug: false
spring:
  application:
    name: microservicecloud-provider # 在使用eurake功能的时候非常重要，这个就是该服务的实例名，eurake可以通过该实例名获得这个服务的其中一个实例（服务端集群）
  datasource:
    type: com.alibaba.druid.pool.DruidDataSource # 当前数据源操作类型
    driver-class-name: com.mysql.cj.jdbc.Driver
    url: jdbc:mysql://localhost:3306/microservicecloud?serverTimezone=UTC # 数据库名称,暂时我这个版本不加serverTimezone会报一个错误
    username: root
    password: 123456
    dbcp2:
      min-idle: 5 # 数据库连接池的最小维持连接数
      initial-size: 5 # 初始化连接数
      max-total: 5 # 最大连接数
      max-wait-millis: 200 # 等待连接获取的最大超时时间
eureka:
  client: # 当前微服务只是eureka的客户端
    service-url:
      defaultZone: http://eureka7000.com:7000/eureka,http://eureka7001.com:7001/eureka,http://eureka7002.com:7002/eureka # 将当前微服务注册到指定的eureka服务端中
```

### 4. 修改微服务消费者`microservicecloud-consumer`的配置文件`application.yml`
```
server:
  port: 8001 # 当前微服务的端口号
  context-path: /consumer #每个请求都必须加上这个路径
eureka:
  client:
    register-with-eureka: false #当前模块是服务消费者，只需要调用服务提供者的接口接口，而服务提供者已经注册，所以这里不需要注册到eureka
    service-url:
      defaultZone: http://eureka7000.com:7000/eureka,http://eureka7001.com:7001/eureka,http://eureka7002.com:7002/eureka
```

### 5. 测试`eureka`集群注册
> 进入到每个`eureka`的目录下，启动每个服务

















> 启动微服务提供者`microservicecloud-provider`

> 启动微服务消费者`microservicecloud-consumer`
 

> 访问如下地址
http://eureka7000.com:7000/
http://eureka7001.com:7001/
http://eureka7002.com:7002/











> 测试微服务消费者`microservicecloud-consumer`是否能正常访问微服务提供者`microservicecloud-provider`
http://localhost:8001/consumer/user/list,可以正常访问



> 手动关掉其中两台eureka，再次测试,关掉之后可以看到被关掉的eureka的管理界面已经无法访问，但是保留的那一台eureka服务器依然还是保留了那两台eureka的地址
再次访问请求http://localhost:8001/consumer/user/list,可以正常访问












## 九、微服务提供者集群
> 在第八章里对Eureka的集群进行的测试，但是每个eureka都只对相同的服务注册了一次，在这个章节开始演示对相同的服务进行集群。

### 1. maven插件打包注意事项
>1.  插件似乎是可以继承的，因此只需要在父类中引入插件即可。
>2.  默认每个模块打包后的名称为父模块名，因此需要单独修改
```
<build>
   <finalName>microservicecloud-provider</finalName>
</build>
```
>3. 打包前需要依赖的模块已经安装到本地仓库才可以正确打包。如`microservicecloud-provicer`引入了模块`microservicecloud-api`，则需要先把`microservicecloud-api`执行`maven`命令 `clean install`，但是不知道什么原因，这个时候还需要先把`microservicecloud-parent`安装到本次，而如果不安装到本地，提示的是找不到`microservicecloud-api`而不是提示找不到`microservicecloud-parent`。这个坑的原因找了好久。最终就是要把这两个模块都要提前安装到本次仓库中，才能保证`microservicecloud-provicer`正确打包






### 2. 项目部署配置
> 对每个项目新建一个文件夹独立出来，每个文件夹下有相同的样板`microservicecloud-provider`，并且在文件夹下新建一个外部配置文件`application.yml`来对不同的项目进行配置修改和调整



#### 2.1 `provider`调整
> `application.yml`修改，注意`instance-id`实例名不要重复

```
server:
  port: 8000 # 配置端口号
  context-path: /provider # 配置当前模块的context-path，即每个请求前都必须附加上这个路径
debug: false

spring:
  application:
    name: microservicecloud-provider # 在使用eurake功能的时候非常重要，这个就是该服务的实例名，eurake可以通过该实例名获得这个服务的其中一个实例（服务端集群）
  datasource:
    type: com.alibaba.druid.pool.DruidDataSource # 当前数据源操作类型
    driver-class-name: com.mysql.cj.jdbc.Driver
    url: jdbc:mysql://localhost:3306/microservicecloud?serverTimezone=UTC # 数据库名称,暂时我这个版本不加serverTimezone会报一个错误
    username: root
    password: 123456
    dbcp2:
      min-idle: 5 # 数据库连接池的最小维持连接数
      initial-size: 5 # 初始化连接数
      max-total: 5 # 最大连接数
      max-wait-millis: 200 # 等待连接获取的最大超时时间

eureka:
  client: # 当前微服务只是eureka的客户端
    service-url:
      # 单机使用
      #defaultZone: http://eureka7000.com:7000/eureka
      ## eureka集群时使用
      defaultZone: http://eureka7000.com:7000/eureka,http://eureka7001.com:7001/eureka,http://eureka7002.com:7002/eureka # 将当前微服务注册到指定的eureka服务端中

  instance:
      instance-id: microservicecloud-provider
      prefer-ip-address: true #在eureka管理界面查看微服务路径可以显示IP地址

info:
  app-name: microservicecloud-provider
  copyright: ddf
  description: "微服务提供者"
```

> 增加一个批处理命令.bat来快速启动

```
d：
cd D:\dev-tools\microservicecloud-develop\provider
java -jar microservicecloud-provider-exec.jar
pause

```

#### 2.2 `provider1`调整
> `application.yml`修改，注意`instance-id`实例名不要重复

```
server:
  port: 9000 # 配置端口号
  context-path: /provider # 配置当前模块的context-path，即每个请求前都必须附加上这个路径
debug: false

spring:
  application:
    name: microservicecloud-provider # 在使用eurake功能的时候非常重要，这个就是该服务的实例名，eurake可以通过该实例名获得这个服务的其中一个实例（服务端集群）
  datasource:
    type: com.alibaba.druid.pool.DruidDataSource # 当前数据源操作类型
    driver-class-name: com.mysql.cj.jdbc.Driver
    url: jdbc:mysql://localhost:3306/microservicecloud?serverTimezone=UTC # 数据库名称,暂时我这个版本不加serverTimezone会报一个错误
    username: root
    password: 123456
    dbcp2:
      min-idle: 5 # 数据库连接池的最小维持连接数
      initial-size: 5 # 初始化连接数
      max-total: 5 # 最大连接数
      max-wait-millis: 200 # 等待连接获取的最大超时时间

eureka:
  client: # 当前微服务只是eureka的客户端
    service-url:
      # 单机使用
      #defaultZone: http://eureka7000.com:7000/eureka
      ## eureka集群时使用
      defaultZone: http://eureka7000.com:7000/eureka,http://eureka7001.com:7001/eureka,http://eureka7002.com:7002/eureka # 将当前微服务注册到指定的eureka服务端中

  instance:
      instance-id: microservicecloud-provider1
      prefer-ip-address: true #在eureka管理界面查看微服务路径可以显示IP地址

info:
  app-name: microservicecloud-provider
  copyright: ddf
  description: "微服务提供者"
```

> 增加一个批处理命令.bat来快速启动

```
d：
cd D:\dev-tools\microservicecloud-develop\provider1
java -jar microservicecloud-provider-exec.jar
pause

```

#### 2.3 `provider2`调整
> `application.yml`修改，注意`instance-id`实例名不要重复

```
server:
  port: 9001 # 配置端口号
  context-path: /provider # 配置当前模块的context-path，即每个请求前都必须附加上这个路径
debug: false

spring:
  application:
    name: microservicecloud-provider # 在使用eurake功能的时候非常重要，这个就是该服务的实例名，eurake可以通过该实例名获得这个服务的其中一个实例（服务端集群）
  datasource:
    type: com.alibaba.druid.pool.DruidDataSource # 当前数据源操作类型
    driver-class-name: com.mysql.cj.jdbc.Driver
    url: jdbc:mysql://localhost:3306/microservicecloud?serverTimezone=UTC # 数据库名称,暂时我这个版本不加serverTimezone会报一个错误
    username: root
    password: 123456
    dbcp2:
      min-idle: 5 # 数据库连接池的最小维持连接数
      initial-size: 5 # 初始化连接数
      max-total: 5 # 最大连接数
      max-wait-millis: 200 # 等待连接获取的最大超时时间

eureka:
  client: # 当前微服务只是eureka的客户端
    service-url:
      # 单机使用
      #defaultZone: http://eureka7000.com:7000/eureka
      ## eureka集群时使用
      defaultZone: http://eureka7000.com:7000/eureka,http://eureka7001.com:7001/eureka,http://eureka7002.com:7002/eureka # 将当前微服务注册到指定的eureka服务端中

  instance:
      instance-id: microservicecloud-provider2
      prefer-ip-address: true #在eureka管理界面查看微服务路径可以显示IP地址

info:
  app-name: microservicecloud-provider
  copyright: ddf
  description: "微服务提供者"
```

> 增加一个批处理命令.bat来快速启动

```
d：
cd D:\dev-tools\microservicecloud-develop\provider2
java -jar microservicecloud-provider-exec.jar
pause

```


### 3. 启动测试
> 启动之前配置的`Eureka`集群，然后依次启动`microservicecloud-provider`微服务，打开`Eureka`的管理界面，如下图所示，每个`Eureka`上都注册了三个服务











> 启动测试访问微服务`microservicecloud-consumer`
http://localhost:8001/consumer/user/list
效果如下，如果手动关闭其中一个微服务的提供者，则客户端默认负载均衡为轮询，那么在轮询到另外两个服务，则可以正常提供服务，如果轮询到down掉的服务，依然会报错，需要再次访问才可能避过这个down掉的服务，这个可能也是`eureka`自我保护机制的一个弊端吧
‘

## 十、 路由网关之`Zuul`
> `Zuul`包含了对请求的路由和过滤两个最主要的功能:
其中路由功能负责将外部请求转发到具体的微服务实例上，是实现外部访问统一入口的基础；
过滤器功能则负责对请求的处理过程进行干预，是实现请求校验，服务聚合都功能的基础；
`Zuul`和`Eureka`进行整合，将`Zuul`自身注册为`Eureka`服务治理下的应用，同时从`Eureka`中获得其它微服务的信息，也即以后的访问微服务都是通过`Zuul`跳转后获得
注意： `Zuul`服务最终还是会注册进`Eureka`
详细文档请参考https://springcloud.cc/spring-cloud-dalston.html#_router_and_filter_zuul

### 1. 新建`microservicecloud-zuul`子模块




### 2. `pom.xml`文件引入`zuul`
```
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
        xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
        xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <parent>
        <artifactId>microservicecloud-parent</artifactId>
        <groupId>com.ddf.microserviecloud</groupId>
        <version>1.0-SNAPSHOT</version>
        <relativePath>../microservicecloud-parent/pom.xml</relativePath>
    </parent>
    <modelVersion>4.0.0</modelVersion>

    <artifactId>microservicecloud-zuul</artifactId>

    <dependencies>
        <!-- zuul路由网关 -->
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-netflix-zuul</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
        </dependency>
        <!-- actuator监控 -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-actuator</artifactId>
        </dependency>
        <!-- hystrix容错 -->
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-netflix-hystrix</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-config</artifactId>
        </dependency>
        <!-- 日常标配 -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-tomcat</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
        </dependency>
        <!-- 热部署插件 -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-loader</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-loader-tools</artifactId>
        </dependency>

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-devtools</artifactId>
        </dependency>
    </dependencies>


    <build>
        <finalName>microservicecloud-zuul</finalName>
    </build>
</project>
```
### 3. 新建`application.yml`配置文件
```
server:
  port: 5000
spring:
  application:
    name: microservicecloud-zuul
eureka:
  client:
    service-url:
      # 单机版
      defaultZone: http://eureka7000.com:7000/eureka
      ## eureka集群时使用
      # defaultZone: http://eureka7000.com:7000/eureka,http://eureka7001.com:7001/eureka,http://eureka7002.com:7002/eureka  # 将当前微服务注册到指定的eureka服务端中
  instance:
      instance-id: microservicecloud-zuul
      prefer-ip-address: true

info:
  app-name: microservicecloud-zuul
  copyright: ddf
  description: "微服务网关"
```

### 4. 主启动类建立
```
package com.ddf.microservicecloud.zuul;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

/**
* @author DDf on 2018/5/22
*/
@SpringBootApplication
@EnableZuulProxy
public class ZuulApplication {
    public static void main(String[] args) {
        SpringApplication.run(ZuulApplication.class, args);
    }
}
```

### 5. 启动测试
> 启动`eureka`,启动当前微服务`microservicecloud-zuul`， 启动微服务提供者`microservicecloud-provider`，打开`eureka`管理界面如下图所示




#### 5.1 不使用路由
> 不使用路由访问原先的微服务提供者的用户列表接口
http://localhost:8000/provider/user/list
效果如下:


#### 5.2 使用路由访问
> 上面使用的是原微服务暴露的接口，现在测试通过路由来访问，依然可以正常访问
访问链接http://localhost:5000/microservicecloud-provider/provider/user/list


### 6. 路由映射
> 现在需要对原服务进行映射,即不暴露真实微服务名来访问微服务

#### 6.1 `application.yml`配置文件修改
>通过`zuul.routes.xxx`来指定映射的api，这个名称是可以随便取的一个对象，通过对象的属性`serviceId`来指定映射的微服务名，`path`来指定对应的映射路径，`stripPrefix`这个属性如果不加，则如果路由的微服务有context-path属性的话，那么在路由的时候也要加上context-path属性，
即需要hostname:5000/zuul/provider/{microservicecloud-provider微服务的context-path},加了这个属性之后则不需要context-path就可。
映射后，原来通过微服务名路由依然可以正常访问，这个时候可以使用`zuul.ignored-services`来指定通过原真实微服务名不可正常访问.
`prefix`配置的时候不要配置成zuul，这个似乎是保留路径，配置了这个之后请求会出现问题

```
server:
  port: 5000
spring:
  application:
    name: microservicecloud-zuul
eureka:
  client:
    service-url:
      # 单机版
      defaultZone: http://eureka7000.com:7000/eureka
      ## eureka集群时使用
      # defaultZone: http://eureka7000.com:7000/eureka,http://eureka7001.com:7001/eureka,http://eureka7002.com:7002/eureka  # 将当前微服务注册到指定的eureka服务端中
  instance:
      instance-id: microservicecloud-zuul
      prefer-ip-address: true

zuul:
  # ignored-services: 紧跟微服务名，则指定的微服务名不能再通过微服务名来直接使用，只能使用zuul来跳转
  prefix: /myzuul # 对所有的访问增加前缀
  ignored-services: "*" # 使所有的微服务名失去使用资格
  routes: #定义路由
    provider:
      serviceId: microservicecloud-provider
      path: /provider/**
      # 这个属性如果不加，则如果路由的微服务有context-path属性的话，那么在路由的时候也要加上context-path属性，
      # 即需要hostname:5000/zuul/provider/{microservicecloud-provider微服务的context-path},加了这个属性之后则不需要context-path就可
      stripPrefix: false

info:
  app-name: microservicecloud-zuul
  copyright: ddf
  description: "微服务网关"
```
#### 6.2 启动测试
> 配置了`zuul.ignored-services`的实效效果如下



> 使用自定义映射访问效果如下
http://localhost:5000/myzuul/provider/user/list




## 十一、 声明性REST客户端调用工具`Feign`
> 
[`Feign`文档](https://springcloud.cc/spring-cloud-dalston.html#spring-cloud-feign)
Feign是一个声明式的Web服务客户端。这使得Web服务客户端的写入更加方便 要使用Feign创建一个界面并对其进行注释。它具有可插入注释支持，包括Feign注释和JAX-RS注释。Feign还支持可插拔编码器和解码器。Spring Cloud增加了对Spring MVC注释的支持，并使用Spring Web中默认使用的HttpMessageConverters。*Spring Cloud集成Ribbon和Eureka以在使用Feign时提供负载均衡的http客户端*。
注意如下：
* `@FeignClient`中的接口中的方法如果使用`@PathVariable`，那么一定要指定PathVariable的参数名称,`@PathVariable("id") Integer id`
* 当使用Feign时，如果发送的是get请求且参数过多，那么需要在`@FeignClient`接口中的方法请求参数前加上`@RequestParam`注解修饰，Controller里面可以不加该注解修饰。另使用另`@RequestParam`之后也应当保持参数不能为空。
* 对上两点参数不能为空的补充，报错信息为第一个参数不能为空，因此是所有的都必须指定还是第一个参数必须指定未做测试校验
* 由于`Feign`是基于`ribbon`的负载均衡，经debug，但不保证准确，默认`feign`接口的connectionTimeOut和readTimeOut都是1000ms，很容易就触发`Read Time Out`的异常，因此可以修改`ribbon`的超时时间，如果同时使用了`Hystrix`，则也应该设置`Hystrix`的超时时间， 超时时间设置为`Ribbon < Hystrix`
`application.yml`
```java
ribbon:
  ReadTimeout: 20000
  ConnectTimeout: 5000
```
`application.properties`
```properties
hystrix.command.default.execution.isolation.thread.timeoutInMilliseconds=30000
```

### 1. 创建`Feign`子模块`microservicecloud-consumer-feign`
>`File -> Project Structrue -> Modules`






### 2. `pom.xml`加入`Feign`的支持
```
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
        xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
        xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <parent>
        <artifactId>microservicecloud-parent</artifactId>
        <groupId>com.ddf.microserviecloud</groupId>
        <version>1.0-SNAPSHOT</version>
        <relativePath>../microservicecloud-parent/pom.xml</relativePath>
    </parent>
    <modelVersion>4.0.0</modelVersion>

    <artifactId>microservicecloud-consumer-feign</artifactId>
    <description>微服务消费者client端</description>

    <dependencies>
        <!-- 自己定义的api -->
        <dependency>
            <groupId>com.ddf.microserviecloud</groupId>
            <artifactId>microservicecloud-api</artifactId>
            <version>${project.version}</version>
        </dependency>

        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-openfeign</artifactId>
        </dependency>

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>

        <!-- 引入Eureka -->
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
        </dependency>

        <!-- 修改后立即生效，热部署 -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-loader</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-loader-tools</artifactId>
        </dependency>


        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-devtools</artifactId>
        </dependency>
    </dependencies>

    <build>
        <finalName>microservicecloud-consumer-feign</finalName>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
                <configuration>
                    <fork>true</fork>
                    <classifier>exec</classifier>
                </configuration>
                <executions>
                    <execution>
                        <goals>
                            <goal>repackage</goal>
                        </goals>
                    </execution>
                </executions>
            </plugin>
        </plugins>
    </build>
</project>
```

### 3. 配置文件`application.yml`
```
server:
  port: 9005  # 当前微服务的端口号
  context-path: /feign #每个请求都必须加上这个路径
eureka:
  client:
    register-with-eureka: false #当前模块是服务消费者，只需要调用服务提供者的接口接口，而服务提供者已经注册，所以这里不需要注册到eureka
    service-url:
      defaultZone: http://eureka7000.com:7000/eureka
      # eureka集群时使用
      # defaultZone: http://eureka7000.com:7000/eureka,http://eureka7001.com:7001/eureka,http://eureka7002.com:7002/eureka
ribbon:
  ReadTimeout: 20000 # 如果使用feign，默认超时时间为1000ms，很容易就超时
  ConnectTimeout: 5000 # 如果使用feign，默认超时时间为1000ms，很容易就超时
```

### 4. 编写主启动类
```java
package com.ddf.microservicecloud.feign;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.ComponentScan;

/**
* @author DDf on 2018/5/15
* @EnableEurekaClient 表明当前模块是eureka的客户端
* @EnableFeignClients 启动Feign相关功能
*/
@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients
public class FeignApplication {
    public static void main(String[] args) {
        SpringApplication.run(FeignApplication.class, args);
    }
}

```

### 5. 编写`Feign`的调用接口
```java
package com.ddf.microservicecloud.feign.feignservice;

import com.ddf.microservicecloud.entity.User;
import import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;

/**
* @author DDf on 2018/5/28
* @FeignClient 指定要访问的微服务名
*/
@FeignClient(value="MICROSERVICECLOUD-PROVIDER")
public interface UserFeignService {

    /**
    * 这里的`@RequestMapping`要写成当前FeignService要访问的微服务名中对应的方法上定义的`@RequestMapping`，
    * /provider为MICROSERVICECLOUD-PROVIDER的context-path，暂时未找到在一个地方统一定义
    * @return
    */
    @RequestMapping("/provider/user/list")
    List<User> queryAll();
    
    /**
    * 这里的@PathVariable一定要写value，否则会报错
    */
    @RequestMapping("/provider/user/user/{id}")
    User queryOne(@PathVariable("id") Integer id);

    @RequestMapping("/provider/user/maplist")
    List<Map<String, Object>> queryAllMap();
}


```

### 6. 编写客户端的控制器代码
```java
package com.ddf.microservicecloud.feign.controller;

import com.ddf.microservicecloud.entity.User;
import com.ddf.microservicecloud.feignservice.UserClientService;
import com.ddf.microservicecloud.feignservice.UserFeignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
* @author DDf on 2018/5/15
* 当前控制器最终调用的是接口中指定的微服务暴露的方法，当前控制器的访问地址并不需要和微服务中对应方法的名称和`@RequestMapping`保持一致，这里只是习惯写一致
*/
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserFeignService userFeignService;
    /**
    * 调用微服务提供者的查询用户列表的接口
    * @return
    */
    @RequestMapping("/list")
    public List<User> userList() {
        return userFeignService.queryAll();
    }
   
    @RequestMapping("/user/{id}")
    public User userList(@PathVariable("id") Integer id) {
        return userFeignService.queryOne(id);
    }
}

```


### 7. 运行测试
> 依次启动`microservicecloud-eureka`，`microservicecloud-provider`,`microservicecloud-consumer-feign`
访问当前`Feign`客户端编写的控制器，客户端的控制器通过定义的`Feign`接口去访问最终提供服务的微服务暴露的方法
查询用户列表: http://localhost:9005/feign/user/list, 结果如下


查询指定用户: http://localhost:9005/feign/user/user/2


### 8. 总结与补充
> 在前面的步骤中已经完成了Feign的基本使用，Feign默认实现了`Ribbon`的基于轮询算法的负载均衡实现。
补充：
要添加的用户的`Feign`的调用接口，可以统一定义在`microservicecloud-api`中

> 在`microservicecloud-api`中新建`Feign`接口类

```java
package com.ddf.microservicecloud.api.feignservice;

import com.ddf.microservicecloud.entity.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
* @author DDf on 2018/5/28
* @FeignClient 指定当前接口需要调用的微服务名
*/
@FeignClient(value="MICROSERVICECLOUD-PROVIDER")
public interface UserClientService {
    /**
    * 这里的RequestMapping要写成当前FeignClient要访问的微服务名中定义的对应接口的访问路径，
    * /provider为MICROSERVICECLOUD-PROVIDER的context-path，暂时未找到在一个地方统一定义
    * @return
    */
    @RequestMapping("/provider/user/list")
    List<User> queryAll();

    @RequestMapping("/provider/user/user/{id}")
    User queryOne(@PathVariable("id") Integer id);
}

```

> `clean install`之后，在`microservicecloud-consumer-feign`中需要使用地方注入进来,如下，修改原`UserController.java`

```java
package com.ddf.microservicecloud.feign.controller;

import com.ddf.microservicecloud.entity.User;
import com.ddf.microservicecloud.feignservice.UserClientService;
import com.ddf.microservicecloud.feignservice.UserFeignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
* @author DDf on 2018/5/15
*/
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserFeignService userFeignService;
    @Autowired
    private UserClientService userClientService;

    /**
    * 调用微服务提供者的查询用户列表的接口
    * @return
    */
    @RequestMapping("/list1")
    public List<User> userList1() {
        return userClientService.queryAll();
    }

    @RequestMapping("/list")
    public List<User> userList() {
        return userFeignService.queryAll();
    }

    /**
    * 调用微服务提供者的查询用户列表的接口
    * @return
    */
    @RequestMapping("/user/{id}")
    public User userList(@PathVariable("id") Integer id) {
        return userFeignService.queryOne(id);
    }
}

```

> 访问测试http://localhost:9005/feign/user/list1， 结果如下正常查询




## 十二、 服务熔断与降级之`Hystrix`
> [参考文档](https://springcloud.cc/spring-cloud-dalston.html#_circuit_breaker_hystrix_clients)
简而言之，服务熔断是为了防止服务出现问题之后，客户端在不能得到具体错误的情况下无法得出服务已经不可用的明确提示，而仍然导致客户端正常访问请求，在大量的访问与错误之后耗费大量的资源之后而引起雪崩。
服务降级，是使在系统资源紧缺的情况下，关闭某些非重要的服务，而将这些资源用来保证核心业务服务不出现问题，降级的时候能够给客户端明确的消息。
经自己测试，服务熔断在出现异常的时候仍然能够保证`fallback`方法的执行，而只要设置了`HystrixCommand`注解的方法如果内部产生了异常，控制台并不会打印，最终这个响应吗仍然是200，目前这个功能觉得在两个地方有很好的用处，一个是当被调用方法出现了某些问题以后，仍然需要返回一些指定的数据；二是当这个接口暴漏给其它系统使用时，其它系统需要知道明确的报错信息，这个信息是处理业务的前提条件，假如条件不满足需要告知被调用方，单纯的不抛出异常，返回原制定格式数据的时候，自己系统的数据如果业务稍微复杂些，之前保存的数据都需要回滚。所以最好的处理方式是本系统抛出异常，而使用`fallback`方法可以在入参的时候，加入`java.lang.Throwable`这个类来获得错误消息，然后获得错误消息，返回给调用方。
而服务降级，核心概念完全不能理解，如果服务资源不够了，就关闭某些服务，感觉也不是很可取啊。唯一觉得有用的是，服务降级之后，如果没有这个处理，直接把服务down掉，访问的时候就是一片空白，而使用了服务降级之后，在服务down掉的情况下如果收到访问依然可以返回一个明确的消息，说当前服务已关闭。

### 1. 在`microservicecloud-provider`上加入`Hystrix`包依赖
```
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-netflix-hystrix</artifactId>
        </dependency>
```
完整版`pom.xml`文件内容如下：
```
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
        xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
        xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <parent>
        <artifactId>microservicecloud-parent</artifactId>
        <groupId>com.ddf.microserviecloud</groupId>
        <version>1.0-SNAPSHOT</version>
        <relativePath>../microservicecloud-parent/pom.xml</relativePath>
    </parent>
    <modelVersion>4.0.0</modelVersion>

    <artifactId>microservicecloud-provider</artifactId>
    <description>微服务提供者Server端</description>


    <dependencies>
        <!-- 引入自定义的模块 -->
        <dependency>
            <groupId>com.ddf.microserviecloud</groupId>
            <artifactId>microservicecloud-api</artifactId>
            <version>1.0-SNAPSHOT</version>
        </dependency>

        <dependency>
            <groupId>junit</groupId>
            <artifactId>junit</artifactId>
        </dependency>

        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
        </dependency>

        <dependency>
            <groupId>com.alibaba</groupId>
            <artifactId>druid</artifactId>
        </dependency>

        <dependency>
            <groupId>ch.qos.logback</groupId>
            <artifactId>logback-core</artifactId>
        </dependency>

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
        </dependency>

        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-jdbc</artifactId>
        </dependency>

        <!-- 修改后立即生效，热部署 -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-loader</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-loader-tools</artifactId>
        </dependency>


        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-devtools</artifactId>
        </dependency>

        <!-- 引入Eureka -->
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
        </dependency>

        <!-- 用于监控信息完善，如构建Info信息 -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-actuator</artifactId>
        </dependency>

        <!-- 引入hystrix -->
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-netflix-hystrix</artifactId>
        </dependency>
    </dependencies>
    <build>
        <finalName>microservicecloud-provider</finalName>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
                <configuration>
                    <fork>true</fork>
                    <classifier>exec</classifier>
                </configuration>
                <executions>
                    <execution>
                        <goals>
                            <goal>repackage</goal>
                        </goals>
                    </execution>
                </executions>
            </plugin>
        </plugins>
    </build>

</project>
```

### 2. 修改主启动类增加`@EnableCircuitBreaker`开启`Hystrix`功能
```java
package com.ddf.microservicecloud.provider;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;

/**
* @author DDf on 2018/5/14
* @EnableEurekaClient 表明当前微服务是Eureka的客户端，将当前微服务注册到Eureka服务端中
* @EnableCircuitBreaker 开启Hystrix的功能
*/
@SpringBootApplication
@EnableEurekaClient
@EnableCircuitBreaker
public class ProviderApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProviderApplication.class, args);
    }
}

```

### 3. 服务熔断之`@HystrixCommand`
> `@HystrixCommand` 开启`Hystrix`功能之后，该命令可以给指定方法如果出现错误不能返回预期结果时指定一个最终执行的方法，
如下，指定了`hystrixListFallback`方法，下面的实例故意抛出一个异常，那么hystrixListFallback就会执行，经过测试，`fallbackMethod`
的方法返回值必须和原方法保持一致,参数也必须保持一致，但是这个方法可以入参一些如`HttpServletRequest`或`Throwable`等类信息来获得额外的信息，
该注解的熔断需要在服务端定义方法的地方来做。

#### 3.1 新建一个控制器`HystrixController.java`
##### 3.1.1 演示返回默认值
```java
package com.ddf.microservicecloud.provider.controller;

import com.ddf.microservicecloud.entity.User;
import com.ddf.microservicecloud.service.UserService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
* @author DDf on 2018/5/29
*/
@RestController
@RequestMapping("/hystrix")
public class HystrixController {

    @Autowired
    private UserService userService;

    /**
     * @HystrixCommand 开启Hystrix功能之后，该命令可以给指定方法如果出现错误不能返回预期结果时指定一个最终执行的方法，
     * 如下，指定了hystrixListFallback方法，下面的实例故意抛出一个异常，那么hystrixListFallback就会执行，经过测试，fallback
     * 的方法返回值必须和原方法保持一致
     * 如下实例返回结果如下
     * [
         * {
             * id: 0,
             * userName: "管理员",
             * password: "123456",
             * gender: "男",
             * tel: "88888888",
             * dbSource: "dbSource-01",
             * removed: 0,
             * errorMessage: null
         * }
     * ]
     * @return
     */
    @RequestMapping("hystrixList")
    @HystrixCommand(fallbackMethod = "hystrixListFallback")
    public List<User> hystrixList() {
        if (true) {
            throw new RuntimeException();
        }
        return userService.queryAll();
    }

    /**
     * hystrixList的fallback方法
     * @return
     */
    public List<User> hystrixListFallback() {
        List<User> rtnList = new ArrayList<>();
        // 如果错误返回一条默认数据
        User user = new User(0, "管理员", "123456", "男", "88888888");
        rtnList.add(user);
        return rtnList;
    }
}
```

#### 3.1.2 测试效果
> 访问链接http://localhost:8000/provider/hystrix/hystrixList，返回结果如下

```json
[
{
id: 0,
userName: "管理员",
password: "123456",
gender: "男",
tel: "88888888",
dbSource: "dbSource-01",
removed: 0,
errorMessage: null
}
]
```

#### 3.3 使用`Feign`客户端来测试熔断
##### 3.3.1 在`microservicecloud-api`中定义接口,之后`clean install`
```java
package com.ddf.microservicecloud.api.feignservice;

import com.ddf.microservicecloud.entity.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
* @author DDf on 2018/5/29
*/
@FeignClient(value="MICROSERVICECLOUD-PROVIDER")
public interface HystrixFeignService {
    /**
    * 这里的RequestMapping要写成当前FeignService要访问的微服务名中定义的对应接口的访问路径，
    * /provider为MICROSERVICECLOUD-PROVIDER的context-path，暂时未找到在一个地方统一定义
    * @return
    */
    @RequestMapping("/provider/hystrix/hystrixList")
    List<User> queryAll();
}

```

##### 3.3.2 在`microservicecloud-consumer-feign`编写访问控制器,注入`HystrixFeignService`
```java
package com.ddf.microservicecloud.feign.controller;

import com.ddf.microservicecloud.entity.User;
import com.ddf.microservicecloud.feignservice.HystrixFeignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
* @author DDf on 2018/5/29
*/
@RestController
@RequestMapping("/hystrix")
public class HystrixController {
    @Autowired
    private HystrixFeignService hystrixFeignService;


    @RequestMapping("/hystrixList")
    public List<User> hystrixList() {
        return hystrixFeignService.queryAll();
    }
}

```

##### .3.3.3 测试结果
> 访问地址: http://localhost:9005/feign/hystrix/hystrixList
结果如下

```json
[
{
id: 0,
userName: "管理员",
password: "123456",
gender: "男",
tel: "88888888",
dbSource: "dbSource-01",
removed: 0,
errorMessage: null
}
]
```

#### 3.4 使用`Feign`演示返回错误消息
> 演示第二种情况，能够让程序在抛出异常的同时依然能够返回数据
##### 3.4.1 修改`microservicecloud-provider`微服务提供者，编辑`HystrixController.java`来编写服务端对应方法的`@HystrixCommand`
```java
package com.ddf.microservicecloud.provider.controller;

import com.ddf.microservicecloud.api.entity.User;
import com.ddf.microservicecloud.provider.service.UserService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @author DDf on 2018/5/29
 */
@RestController
@RequestMapping("/hystrix")
public class HystrixController {

    @Autowired
    private UserService userService;

    /**
     * @HystrixCommand 开启Hystrix功能之后，该命令可以给指定方法如果出现错误不能返回预期结果时指定一个最终执行的方法，
     * 如下，指定了hystrixListFallback方法，下面的实例故意抛出一个异常，那么hystrixListFallback就会执行，经过测试，fallback
     * 的方法返回值必须和原方法保持一致
     * 如下实例返回结果如下
     * [
         * {
             * id: 0,
             * userName: "管理员",
             * password: "123456",
             * gender: "男",
             * tel: "88888888",
             * dbSource: "dbSource-01",
             * removed: 0,
             * errorMessage: null
         * }
     * ]
     * @return
     */
    @RequestMapping("hystrixList")
    @HystrixCommand(fallbackMethod = "hystrixListFallback")
    public List<User> hystrixList() {
        if (true) {
            throw new RuntimeException();
        }
        return userService.queryAll();
    }

    /**
     * hystrixList的fallback方法
     * @return
     */
    public List<User> hystrixListFallback() {
        List<User> rtnList = new ArrayList<>();
        // 如果错误返回一条默认数据
        User user = new User(0, "管理员", "123456", "男", "88888888");
        rtnList.add(user);
        return rtnList;
    }

    @RequestMapping("/queryOne/{id}")
    @HystrixCommand(fallbackMethod = "returnMessageFallback")
    public User returnMessage(@PathVariable Integer id) {
        User user = userService.queryOne(id);
        if (user == null) {
            throw new RuntimeException("没有找到对应的用户, id = " + id);
        }
        return user;
    }

    /**
     * returnMessage方法的facllback方法
     * @param id
     * @param ex
     * @return
     */
    public User returnMessageFallback(Integer id, Throwable ex) {
        User user = new User();
        if (ex != null) {
            user.setErrorMessage(ex.getMessage());
        }
        return user;
    }
}
```

##### 3.4.2 修改`microservicecloud-consumer-feign`的`HystrixFeignService.java`
```java
package com.ddf.microservicecloud.feign.feignservice;

import com.ddf.microservicecloud.api.entity.User;
import com.ddf.microservicecloud.api.feignservice.HystrixFallFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @author DDf on 2018/5/29
 */
@FeignClient(value="MICROSERVICECLOUD-PROVIDER", fallbackFactory = HystrixFallFactory.class)
public interface HystrixFeignService {
    /**
     * 这里的RequestMapping要写成当前FeignService要访问的微服务名中定义的对应接口的访问路径，
     * /provider为MICROSERVICECLOUD-PROVIDER的context-path，暂时未找到在一个地方统一定义
     * @return
     */
    @RequestMapping("/provider/hystrix/hystrixList")
    List<User> hystrixList();

    @RequestMapping("/provider/user/list")
    List<User> userList();


    /**
     * 这里的@PathVariable一定要写value，否则会报错
     * @param id
     * @return
     */
    @RequestMapping("provider/hystrix/queryOne/{id}")
    User queryOne(@PathVariable(value = "id") Integer id);
}

```

##### 3.4.3 修改`microservicecloud-consumer-feign`，编辑`HystrixController.java`
```java
package com.ddf.microservicecloud.feign.controller;

import com.ddf.microservicecloud.api.entity.User;
import com.ddf.microservicecloud.feign.feignservice.HystrixFeignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author DDf on 2018/5/29
 */
@RestController
@RequestMapping("/hystrix")
public class HystrixController {

    @Autowired
    private HystrixFeignService hystrixFeignService;

    @RequestMapping("/hystrixList")
    public List<User> hystrixList() {
        return hystrixFeignService.hystrixList();
    }

    @RequestMapping("/userList")
    public List<User> userList() {
        return hystrixFeignService.userList();
    }

    @RequestMapping("/queryOne/{id}")
    public User queryOne(@PathVariable Integer id) {
        return hystrixFeignService.queryOne(id);
    }
}
```

##### 3.4.5 访问请求
http://localhost:9005/feign/hystrix/queryOne/1，这个是正常的可以查询到数据的
```json
{
id: 1,
userName: "ddf",
password: "123456",
gender: "男",
tel: "18300001111",
dbSource: "dbSource-01",
removed: 0,
errorMessage: null
}
```
现在查询一个不存在的用户，http://localhost:9005/feign/hystrix/queryOne/0, 这个报错是因为`JdbcTemplate`查询不到数据底层会抛出这么个错误，所以没有返回本来应该抛出的用户不存在的消息
```json
{
id: null,
userName: null,
password: null,
gender: null,
tel: null,
dbSource: null,
removed: null,
errorMessage: "Incorrect result size: expected 1, actual 0"
}
```

#### 3.5 总结
> @HystrixCommand该注解可以返回原方法的返回类型，所以与异常还是有些区别。这个在出现某些错误返回一些默认值还是很有用处的。
现在的问题在于这个注解是使用在方法上的，不可能每个方法都指定一个`fallbackMethod`，这样的重复量不可想象。


### 4. 服务降级之`FallbackFactory`
> 服务降级可以在服务down掉的情况下如果被访问依然可以返回指定消息，这里依然使用`Feign`来完成步骤实验

#### 4.1 在`microservicecloud-api`添加一个实现`FallbackFactory`接口的类 

### 5. 使用注意事项
* 修改默认超时时间,`application.properties`，整个网关配置中，一共存在3个超时设置。第一个是zuul路由的超时时间，第二个是hystrix超时设置，第三个是ribbon的超时时间设置，这三个应该是递减的。其中经常出问题的就是hystrix配置
```
# 设置全局超时时间
hystrix.command.default.execution.isolation.thread.timeoutInMilliseconds=3000
```
* 对单个方法进行单独的超时控制，需要配合`@HystrixCommand`注解
```java
    @RequestMapping("/mapping")
    @HystrixCommand(fallbackMethod = "mappingFallback", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds",value = "3000")
    })
    public Map<String, Object> mapping() {
    }
```

##十三、 邮件
> 关于邮件的功能在前面开发的服务消费者`microservicecloud-consumer-feign`中来开发，下面的实例使用QQ邮箱来测试

### 1. QQ邮箱授权码的生成
> 在配置邮箱密码的时候可以不使用真实密码，而在QQ邮箱的设置-账户中找到邮箱服务，如下图







### 2. `pom.xml`中加入`spring-boot-starter-mail`邮件starter
> `spring-boot`在我们引入了`spring-boot-starter-mail`，邮件相关的自动配置类便会生效，自动配置了一个`bean`为`JavaMailSenderImpl`来简化邮件开发

```
<!-- 引入邮件开发 -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-mail</artifactId>
        </dependency>
```
完整版如下
```
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <parent>
        <artifactId>microservicecloud-parent</artifactId>
        <groupId>com.ddf.microserviecloud</groupId>
        <version>1.0-SNAPSHOT</version>
        <relativePath>../microservicecloud-parent/pom.xml</relativePath>
    </parent>
    <modelVersion>4.0.0</modelVersion>

    <artifactId>microservicecloud-consumer-feign</artifactId>
    <description>微服务消费者client端</description>

    <dependencies>
        <!-- 自己定义的api -->
        <dependency>
            <groupId>com.ddf.microserviecloud</groupId>
            <artifactId>microservicecloud-api</artifactId>
            <version>${project.version}</version>
        </dependency>

        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-openfeign</artifactId>
        </dependency>

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>

        <!-- 引入Eureka -->
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
        </dependency>

        <!-- 修改后立即生效，热部署 -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-loader</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-loader-tools</artifactId>
        </dependency>


        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-devtools</artifactId>
        </dependency>

        <!-- 引入邮件开发 -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-mail</artifactId>
        </dependency>
    </dependencies>

    <build>
        <finalName>microservicecloud-consumer-feign</finalName>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
                <configuration>
                    <fork>true</fork>
                    <classifier>exec</classifier>
                </configuration>
                <executions>
                    <execution>
                        <goals>
                            <goal>repackage</goal>
                        </goals>
                    </execution>
                </executions>
            </plugin>
        </plugins>
    </build>
</project>
```

### 3. `application.yml`配置文件加入邮件相关配置， 完整版如下
```yml
server:
  port: 9005 # 当前微服务的端口号
  context-path: /feign #每个请求都必须加上这个路径
eureka:
  client:
    register-with-eureka: true #当前模块是服务消费者，只需要调用服务提供者的接口接口，而服务提供者已经注册，所以这里不需要注册到eureka
    service-url:
      defaultZone: http://eureka7000.com:7000/eureka
      # eureka集群时使用
ribbon:
  ReadTimeout: 20000 # 如果使用feign，默认超时时间为1000ms，很容易就超时
  ConnectTimeout: 5000 # 如果使用feign，默认超时时间为1000ms，很容易就超时
spring:
  mail:
    username: 1041765757@qq.com # 用来验证授权的邮件用户名
    password: gotartrfwuytbcji # 根据QQ邮箱设置-账户里生成的第三方登陆授权码，可用来代替密码登陆
    host: smtp.qq.com # 邮件服务器类型
    properties.mail.smtp.ssl.enable: true # 用以支持授权码登陆
```

### 4. 编写邮件发送代码
#### 4.1 编写一个邮件发送通用类`MailUtil.java`
```java
package com.ddf.microservicecloud.feign.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.util.Map;

/**
 * @author DDf on 2018/5/31
 */
@Component
public class MailUtil {
    private Logger log = LoggerFactory.getLogger(MailUtil.class);

    @Autowired
    private JavaMailSenderImpl mailSender;

    /**
     * 发送简单的纯文本内容的邮件
     * @param sendTo 接收人
     * @param subject 主题
     * @param content 内容
     */
    public void sendSimpleMail(String[] sendTo, String subject, String content) {
        log.debug("sendSimpleMail.....to{}...............", sendTo.toString());
        SimpleMailMessage message = new SimpleMailMessage();
        message.setSubject(subject);
        message.setTo(sendTo);
        // 经过测试，这个必须要写，而且必须要和配置的邮箱认证的用户名一致
        message.setFrom("1041765757@qq.com");
        message.setText(content);
        mailSender.send(message);
    }

    /**
     * 发送带附件的和支持html格式内容的邮件内容
     * @param sendTo 收件人
     * @param subject 主题
     * @param conent 内容
     * @param attachment 附件
     * @throws MessagingException
     */
    public void sendMimeMail(String[] sendTo, String subject, String conent, Map<String, File> attachment)
            throws MessagingException {
        log.debug("sendMimeMail.....to {}...............", sendTo.toString());
        //1、创建一个复杂的消息邮件
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

        //邮件设置
        helper.setSubject(subject);
        helper.setText(conent,true);

        helper.setTo(sendTo);
        // 经过测试，这个必须要写，而且必须要和配置的邮箱认证的用户名一致
        helper.setFrom("1041765757@qq.com");

        //上传文件
        if (attachment != null && !attachment.isEmpty()) {
            attachment.forEach((attachmentFilename, file) -> {
                try {
                    helper.addAttachment(attachmentFilename, file);
                } catch (MessagingException e) {
                    e.printStackTrace();
                }
            });
        }
        mailSender.send(mimeMessage);
    }

    public void sendMimeMail(String[] sendTo, String subject, String conent) throws MessagingException {
        sendMimeMail(sendTo, subject, conent, null);
    }
}
```
#### 4.2 编写一个控制类来发送邮件
```
package com.ddf.microservicecloud.feign.controller;

import com.ddf.microservicecloud.feign.util.MailUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
/**
 * @author DDf on 2018/5/31
 */
@RestController
public class MailController {
    @Autowired
    private MailUtil mailUtil;
    /**
     * 发送一个简单的纯文本的邮件内容
     */
    @RequestMapping("/simpleMail")
    public void sendSimpleMail() {
        mailUtil.sendSimpleMail(new String[]{"1041765757@qq.com", "dongfang.ding@hitisoft.com",
                "18356784598@163.com"}, "邮件测试", "系统测试发送邮件，请勿回复。。。。。。。。。");
    }
    /**
     * 发送一个支持html格式内容和附件的邮件
     */
    @RequestMapping("/mimeMail")
    public void sendMimeMail() throws MessagingException, FileNotFoundException {
        Map<String, File> attachMap = new HashMap<>();
        attachMap.put("README.md", ResourceUtils.getFile("classpath:attach/README.md"));
        attachMap.put("sendMimeMail.png", ResourceUtils.getFile("classpath:attach/sendMimeMail.png"));
        mailUtil.sendMimeMail(new String[]{"1041765757@qq.com", "dongfang.ding@hitisoft.com",
                        "18356784598@163.com"}, "附件邮件测试", "<b style='color:red'>这是一个带附件的邮件测试</b>",
                attachMap);
    }
}
```

### 5. 测试
> 访问地址如下
http://localhost:9005/feign/simpleMail，简单邮件内容如下：



>  [http://localhost:9005/feign/mimeMail](http://localhost:9005/feign/mimeMail), 带附件的邮件内容如下：




## 十四、 定时任务
> 
项目开发中经常需要执行一些定时任务，比如需要在每天凌晨时候，分析一次前 一天的日志信息。Spring为我们提供了异步执行任务调度的方式，提供 TaskExecutor 、TaskScheduler 接口。
两个注解：`@EnableScheduling`、`@Scheduled`
`@EnableScheduling`: 标注在主启动类上，用以开启定时
`@Scheduled`： 标注在具体方法上，用以开启定时任务，配合`cron`表达式完成任务调度
该功能基于`microservicecloud-consumer-feign`上做演示

### 1. 修改主启动类`FeignApplication`加上`@EnableScheduling`开启定时任务支持
```java
package com.ddf.microservicecloud.feign;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author DDf on 2018/5/15
 * @EnableEurekaClient 表明当前模块是eureka的客户端
 * @EnableFeignClients 启动Feign相关功能
 * @EnableScheduling 启动对定时任务的支持
 */
@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients
@ComponentScan("com.ddf.microservicecloud")
@EnableScheduling
public class FeignApplication {
    public static void main(String[] args) {
        SpringApplication.run(FeignApplication.class, args);
    }
}
```

### 2. `@Scheduled`和`cron`介绍
> 给需要定时执行的方法上加上`@Scheduled`注解，配合`cron`表达式实现调度，`cron`表达式详解,经过测试，@Scheduled好像只有在`controller`上的方法上才会生效

字段	允许值	允许的特殊字符
秒	0-59 	 , - * / 
分	0-59 	 , - * / 
小时 	0-23 	 , - * / 
日期 	1-31 	 , - * ? / L W C 
月份	1-12 	 , - * / 
星期	0-7或SUN-SAT 0,7是SUN 	 , - * ? / L C #

> 特殊字符含义解释
特殊字符	代表含义
,	枚举，如 "1,3,5 * * * * *"
-	区间， 如 "1-5 * * * * *"
*	
/	 步长，每,如 "0/10 * * * * *"
?	日/星期冲突匹配 
L	最后 
W	工作日 
C	和calendar联系后计算过的值 
#	星期，4#2，第2个星期四

### 3. 新增任务类和任务方法
```java
package com.ddf.microservicecloud.feign.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDateTime;

/**
 * @author DDf on 2018/6/3
 */
@RestController
public class ScheduleController {
    private Logger log = LoggerFactory.getLogger(ScheduleController.class);

    @Scheduled(cron = "0/10 * * * * *")
    public void scheduleDemo() {
        log.info("scheduleDemo...........{}", LocalDateTime.now());
    }
}
```
## 十五、异步调用
> 以下实例如无特别说明，在`microservicecloud-consumer-feign`上做演示
### 1. `EnableAsync`开启对异步的支持
> 修改主启动类`FeignApplication`

```java
package com.ddf.microservicecloud.feign;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author DDf on 2018/5/15
 * @EnableEurekaClient 表明当前模块是eureka的客户端
 * @EnableFeignClients 启动Feign相关功能
 * @EnableScheduling 启动对定时任务的支持
 * @EnableAsync 开启对异步调用的支持
 */
@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients
@ComponentScan("com.ddf.microservicecloud")
@EnableScheduling
@EnableAsync
public class FeignApplication {
    public static void main(String[] args) {
        SpringApplication.run(FeignApplication.class, args);
    }
}
```
### 2. `@Async`标注异步任务
#### 2.1 新建接口`AsyncService`
```java
package com.ddf.microservicecloud.feign.service;

/**
 * @author DDf on 2018/6/4
 */
public interface AsyncService {
    void asyncPrint();
}
```
#### 2.2 实现`AsyncService`接口
```java
package com.ddf.microservicecloud.feign.service.impl;

import com.ddf.microservicecloud.feign.controller.AsyncController;
import com.ddf.microservicecloud.feign.service.AsyncService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * @author DDf on 2018/6/4
 */
@Service
public class AsyncServiceImpl implements AsyncService {
    private Logger log = LoggerFactory.getLogger(AsyncServiceImpl.class);

    /**
     * @Async表明这是一个异步方法
     */
    @Async
    @Override
    public void asyncPrint() {
        try {
            Thread.sleep(3000);
            log.info("这是一个异步的打印方法..................");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

```

### 3. 新建`AsyncController`控制器调用
```java
package com.ddf.microservicecloud.feign.controller;

import com.ddf.microservicecloud.feign.service.AsyncService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author DDf on 2018/6/4
 */
@RestController
@RequestMapping("/async")
public class AsyncController {
    private Logger log = LoggerFactory.getLogger(AsyncController.class);
    @Autowired
    private AsyncService asyncService;

    @RequestMapping("/asyncPrint")
    public void asyncPrint() {
        log.info("方法执行开始........");
        asyncService.asyncPrint();
        log.info("方法执行结束........");
    }
}
```

### 4. 访问测试以及总结
[http://localhost:9005/feign/async/asyncPrint](http://localhost:9005/feign/async/asyncPrint)
```
2018-06-04 22:55:26.408 INFO 10560 --- [nio-9005-exec-5] c.d.m.feign.controller.AsyncController : 方法执行开始........
2018-06-04 22:55:26.409 INFO 10560 --- [nio-9005-exec-5] c.d.m.feign.controller.AsyncController : 方法执行结束........
2018-06-04 22:55:29.410 INFO 10560 --- [cTaskExecutor-3] c.d.m.f.service.impl.AsyncServiceImpl : 这是一个异步的打印方法..................
```
由上可以看到，方法执行结束并没有等待异步调用执行结束才打印，因为异步打印故意睡眠了三秒，现在变成了这个最后打印出来，说明异步调用生效.

## 十六、 缓存
### 1. JSR107
Java Caching定义了5个核心接口，分别是`CachingProvider`, `CacheManager`, `Cache`, `Entry `和 `Expiry`。
> 
1. `CachingProvider`定义了创建、配置、获取、管理和控制多个`CacheManager`。一个应用可以在运行期访问多个`CachingProvider`。
2. `CacheManager`定义了创建、配置、获取、管理和控制多个唯一命名的`Cache`，这些`Cache`存在于`CacheManager`的上下文中。一个`CacheManager`仅被一个`CachingProvider`所拥有。
3. `Cache`是一个类似Map的数据结构并临时存储以Key为索引的值。一个`Cache`仅被一个`CacheManager`所拥有。
4. `Entry`是一个存储在`Cache`中的key-value对。
5. `Expiry` 每一个存储在`Cache`中的条目有一个定义的有效期。一旦超过这个时间，条目为过期的状态。一旦过期，条目将不可访问、更新和删除。缓存有效期可以通过`ExpiryPolicy`设置。




### 2. Spring缓存抽象
#### 2.1 简介
> Spring从3.1开始定义了`org.springframework.cache.Cache`
和`org.springframework.cache.CacheManager`接口来统一不同的缓存技术；
并支持使用JCache（JSR-107）注解简化我们开发；

>`Cache`接口为缓存的组件规范定义，包含缓存的各种操作集合；
`Cache`接口下Spring提供了各种xxxCache的实现；如`RedisCache`，`EhCacheCache` , `ConcurrentMapCache`等；

>每次调用需要缓存功能的方法时，Spring会检查检查指定参数的指定的目标方法是否已经被调用过；如果有就直接从缓存中获取方法调用后的结果，如果没有就调用方法并缓存结果后返回给用户。下次调用直接从缓存中获取。
使用Spring缓存抽象时我们需要关注以下两点；
1、确定方法需要被缓存以及他们的缓存策略
2、从缓存中读取之前缓存存储的数据

> 原理：
      1、自动配置类；`CacheAutoConfiguration`
      2、缓存的配置类
      `org.springframework.boot.autoconfigure.cache.GenericCacheConfiguration`
      `org.springframework.boot.autoconfigure.cache.JCacheCacheConfiguration`
      `org.springframework.boot.autoconfigure.cache.EhCacheCacheConfiguration`
      `org.springframework.boot.autoconfigure.cache.HazelcastCacheConfiguration`
      `org.springframework.boot.autoconfigure.cache.InfinispanCacheConfiguration`
      `org.springframework.boot.autoconfigure.cache.CouchbaseCacheConfiguration`
      `org.springframework.boot.autoconfigure.cache.RedisCacheConfiguration`
      `org.springframework.boot.autoconfigure.cache.CaffeineCacheConfiguration`
      `org.springframework.boot.autoconfigure.cache.GuavaCacheConfiguration`
      `org.springframework.boot.autoconfigure.cache.SimpleCacheConfiguration`【默认】
      `org.springframework.boot.autoconfigure.cache.NoOpCacheConfiguration`
      3、哪个配置类默认生效：`SimpleCacheConfiguration`；
      4、给容器中注册了一个`CacheManager`：`ConcurrentMapCacheManager`
      5、可以获取和创建`ConcurrentMapCache`类型的缓存组件；他的作用将数据保存在`ConcurrentMap`
      >运行流程：
      `@Cacheable`：
      1、方法运行之前，先去查询`Cache`（缓存组件），按照`cacheNames`指定的名字获取；
      （`CacheManager`先获取相应的缓存），第一次获取缓存如果没有`Cache`组件会自动创建。
      2、去`Cache中`查找缓存的内容，使用一个`key`，默认就是方法的参数；
      key是按照某种策略生成的；默认是使用`keyGenerator`生成的，默认使用`SimpleKeyGenerator`生成key；
      `SimpleKeyGenerator`生成key的默认策略；
      如果没有参数；key=new SimpleKey()；
      如果有一个参数：key=参数的值
      如果有多个参数：key=new SimpleKey(params)；
      3、没有查到缓存就调用目标方法；
      4、将目标方法返回的结果，放进缓存中     
      >`@Cacheable`标注的方法执行之前先来检查缓存中有没有这个数据，默认按照参数的值作为key去查询缓存，
      如果没有就运行方法并将结果放入缓存；以后再来调用就可以直接使用缓存中的数据；     
      >核心：
      1）、使用CacheManager【ConcurrentMapCacheManager】按照名字得到Cache【ConcurrentMapCache】组件
      2）、key使用keyGenerator生成的，默认是SimpleKeyGenerator     
      >几个属性：
      cacheNames/value：指定缓存组件的名字;将方法的返回结果放在哪个缓存中，是数组的方式，可以指定多个缓存；     
      >key：缓存数据使用的key；可以用它来指定。默认是使用方法参数的值 1-方法的返回值
      编写SpEL； #i d;参数id的值 #a0 #p0 #root.args[0]
      getEmp[2]     
     >keyGenerator：key的生成器；可以自己指定key的生成器的组件id
     key/keyGenerator：二选一使用;
     >cacheManager：指定缓存管理器；或者cacheResolver指定获取解析器     
      >condition：指定符合条件的情况下才缓存；
      condition = "#id>0"
      condition = "#a0>1"：第一个参数的值》1的时候才进行缓存     
      >unless:否定缓存；当unless指定的条件为true，方法的返回值就不会被缓存；可以获取到结果进行判断
      unless = "#result == null"
      unless = "#a0==2":如果第一个参数的值是2，结果不缓存；
      sync：是否使用异步模式


#### 2.2 几个重要概念与缓存注解
Cache

缓存接口，定义缓存操作。实现有：RedisCache、EhCacheCache、ConcurrentMapCache等

CacheManager

缓存管理器，管理各种缓存（Cache）组件

@Cacheable

主要针对方法配置，能够根据方法的请求参数对其结果进行缓存

@CacheEvict

清空缓存

@CachePut

保证方法被调用，又希望结果被缓存。

@EnableCaching

开启基于注解的缓存

keyGenerator

缓存数据时key生成策略

serialize

缓存数据时value序列化策略


#### 2.3 @Cacheable/@CachePut/@CacheEvict 主要的参数
value

缓存的名称，在 spring 配置文件中定义，必须指定至少一个

例如：@Cacheable(value=”mycache”) 或者 @Cacheable(value={”cache1”,”cache2”}

key



缓存的 key，可以为空，如果指定要按照 SpEL 表达式编写，如果不指定，则缺省按照方法的所有参数进行组合

如：@Cacheable(value=”testcache”,key=”#userName”)

condition

缓存的条件，可以为空，使用 SpEL 编写，返回 true 或者 false，只有为 true 才进行缓存/清除缓存，在调用方法之前之后都能判断

如：@Cacheable(value=”testcache”,condition=”#userName.length()>2”)

allEntries

(@CacheEvict )

是否清空所有缓存内容，缺省为 false，如果指定为 true，则方法调用后将立即清空所有缓存

如：@CachEvict(value=”testcache”,allEntries=true)

beforeInvocation

(@CacheEvict)

是否在方法执行前就清空，缺省为 false，如果指定为 true，则在方法还没有执行的时候就清空缓存，缺省情况下，如果方法执行抛出异常，则不会清空缓存

例如：

@CachEvict(value=”testcache”，beforeInvocation=true)

unless

(@CachePut)

(@Cacheable)

用于否决缓存的，不像condition，该表达式只在方法执行之后判断，此时可以拿到返回值result进行判断。条件为true不会缓存，fasle才缓存

如：@Cacheable(value=”testcache”,unless=”#result == null”)


#### 2.4 Cache SpEL available metadata
名字

位置

描述

示例

methodName

root object

当前被调用的方法名

#root.methodName

method

root object

当前被调用的方法

#root.method.name

target

root object

当前被调用的目标对象

#root.target

targetClass

root object

当前被调用的目标对象类

#root.targetClass

args

root object

当前被调用的方法的参数列表

#root.args[0]

caches

root object

当前方法调用使用的缓存列表（如@Cacheable(value={"cache1", "cache2"})），则有两个cache

#root.caches[0].name

argument name

evaluation context

方法参数的名字. 可以直接 #参数名 ，也可以使用 #p0或#a0 的形式，0代表参数的索引；

#iban 、 #a0 、  #p0 

result

evaluation context

方法执行后的返回值（仅当方法执行之后的判断有效，如‘unless’，’cache put’的表达式 ’cache evict’的表达式beforeInvocation=false）

#result



#### 2.5 Spring缓存使用
> 以下使用，如无特别说明，则在`microservicecloud-provider`中演示

##### 2.5.1 引入`spring-boot-starter-cache`
```
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <parent>
        <artifactId>microservicecloud-parent</artifactId>
        <groupId>com.ddf.microserviecloud</groupId>
        <version>1.0-SNAPSHOT</version>
        <relativePath>../microservicecloud-parent/pom.xml</relativePath>
    </parent>
    <modelVersion>4.0.0</modelVersion>

    <artifactId>microservicecloud-provider</artifactId>
    <description>微服务提供者Server端</description>


    <dependencies>
        <!-- 引入自定义的模块 -->
        <dependency>
            <groupId>com.ddf.microserviecloud</groupId>
            <artifactId>microservicecloud-api</artifactId>
            <version>1.0-SNAPSHOT</version>
        </dependency>

        <dependency>
            <groupId>junit</groupId>
            <artifactId>junit</artifactId>
        </dependency>

        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
        </dependency>

        <dependency>
            <groupId>com.alibaba</groupId>
            <artifactId>druid</artifactId>
        </dependency>

        <dependency>
            <groupId>ch.qos.logback</groupId>
            <artifactId>logback-core</artifactId>
        </dependency>

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
        </dependency>

        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-jdbc</artifactId>
        </dependency>

        <!-- 修改后立即生效，热部署 -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-loader</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-loader-tools</artifactId>
        </dependency>


        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-devtools</artifactId>
        </dependency>

        <!-- 引入Eureka -->
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
        </dependency>

        <!-- 用于监控信息完善，如构建Info信息 -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-actuator</artifactId>
        </dependency>

        <!-- 引入hystrix -->
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-netflix-hystrix</artifactId>
        </dependency>

        <!-- 引入对缓存的支持 -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-cache</artifactId>
        </dependency>

    </dependencies>
    <build>
        <finalName>microservicecloud-provider</finalName>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
                <configuration>
                    <fork>true</fork>
                    <classifier>exec</classifier>
                </configuration>
                <executions>
                    <execution>
                        <goals>
                            <goal>repackage</goal>
                        </goals>
                    </execution>
                </executions>
            </plugin>
        </plugins>
    </build>

</project>
```

##### 2.5.2 `@EnableCaching`在主启动类开启缓存
默认使用的是ConcurrentMapCacheManager==ConcurrentMapCache；将数据保存在	ConcurrentMap<Object, Object>中
  开发中使用缓存中间件；redis、memcached、ehcache


```java
package com.ddf.microservicecloud.provider;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @author DDf on 2018/5/14
 * @EnableEurekaClient 表明当前微服务是Eureka的客户端，将当前微服务注册到Eureka服务端中
 * @EnableCircuitBreaker 对Hystrix熔断的支持
 * @EnableCaching 开启对Spring缓存的支持
 */
@SpringBootApplication
@EnableEurekaClient
@EnableCircuitBreaker
@EnableCaching
public class ProviderApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProviderApplication.class, args);
    }
}
```

##### 2.5.3 `@Cacheable`获得缓存数据
> 这个注解标注在方法上，会每次在方法执行前在指定缓存管理器中查询对应key的缓存，如果没有数据则创建缓存，如果有则方法不会执行，直接返回缓存里的数据。详细请参考上面的原理部分的详细解释。
> 修改`microservicecloud-provider`微服务提供者的用户查询接口，增加一个缓存方法

```java
package com.ddf.microservicecloud.provider.dao.impl;

import com.ddf.microservicecloud.api.entity.User;
import com.ddf.microservicecloud.provider.dao.UserDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author DDf on 2018/5/14
 */
@Repository
public class UserDaoImpl implements UserDao {
    private Logger log = LoggerFactory.getLogger(UserDaoImpl.class);
    @Autowired
    private JdbcTemplate jdbcTemplate;


    /**
     * 查询所有的用户
     * @return
     */
    @Override
    public List<User> findAll() {
        String sql = "SELECT * FROM USER WHERE REMOVED = 0";
        return jdbcTemplate.query(sql, new User());
    }

    /**
     * 获得指定的用户
     * value用于指定cacheManager， key指定缓存的key，默认是参数
     * @param id
     * @return
     */
    @Cacheable(value = "user", key = "#id")
    @Override
    public User getOne(Integer id) {
        log.info("查询用户id为{}的数据。。。", id);
        String sql = "SELECT * FROM USER WHERE REMOVED = 0 AND ID = " + id;
        return (User) jdbcTemplate.queryForObject(sql, new User());
    }

    @Override
    public List<Map<String, Object>> findAllMap() {
        String sql = "SELECT * FROM USER WHERE REMOVED = 0 ";
        List<Map<String, Object>> mapList = new ArrayList<>();
        mapList = jdbcTemplate.queryForList(sql, mapList);
        return mapList;
    }
}

```

> 测试`@Cacheable`
访问请求[http://localhost:9005/feign/user/user/1](http://localhost:9005/feign/user/user/1)
第一次访问结果

```
{
id: 1,
userName: "ddf",
password: "123456",
gender: "男",
tel: "18300001111",
dbSource: "dbSource-01",
removed: 0,
errorMessage: null
}
```
因为缓存是做到服务端的，所以我们要来看服务端的控制台，服务端控制台打印如下：
```
2018-06-05 23:24:01.651 INFO 16288 --- [nio-8000-exec-2] c.d.m.provider.dao.impl.UserDaoImpl : 查询用户id为1的数据。。。
2018-06-05 21:36:45.754 DEBUG 13196 --- [nio-8000-exec-3] o.s.b.w.f.OrderedRequestContextFilter : Cleared thread-bound request context: org.apache.catalina.connector.RequestFacade@47ffe6e6

```
第二次访问结果如下:
```
{
id: 1,
userName: "ddf",
password: "123456",
gender: "男",
tel: "18300001111",
dbSource: "dbSource-01",
removed: 0,
errorMessage: null
}
```
服务端控制台没有打印，说明已经从缓存中拿到数据了

> 总结
现在只牵扯到缓存的读操作，那么如果缓存的数据如果以及被更新了会发生什么，而我们要如何去更新缓存呢？

##### 2.5.5 `@CachePut`更新缓存
> `@CachePut`：既调用方法，又更新缓存数据；同步更新缓存
修改了数据库的某个数据，同时更新缓存；运行时机：
>> 1. 先调用目标方法
>> 2. 将目标方法的结果缓存起来

> 修改用户相关`Dao`,`Service`，`Controller`，增加更新指定用户的方法

>> 因为牵扯到对用户的更新所以在这里把事务引入进来`@EnableTransactionManagement`，开启对事务的支持,配合`@Transactional`在指定方法上

`ProvicerApplication.java`
```java
package com.ddf.microservicecloud.provider;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author DDf on 2018/5/14
 * @EnableEurekaClient 表明当前微服务是Eureka的客户端，将当前微服务注册到Eureka服务端中
 * @EnableCircuitBreaker 对Hystrix熔断的支持
 * @EnableCaching 开启对Spring缓存的支持
 * @EnableTransactionManagement 开启对事务的支持
 */
@SpringBootApplication
@EnableEurekaClient
@EnableCircuitBreaker
@EnableCaching
@EnableAspectJAutoProxy
@EnableTransactionManagement
public class ProviderApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProviderApplication.class, args);
    }
}
```

`UserDao.java`
```java
package com.ddf.microservicecloud.provider.dao;

import com.ddf.microservicecloud.api.entity.User;

import java.util.List;
import java.util.Map;

/**
 * @author DDf on 2018/5/14
 */
public interface UserDao {

    List<User> findAll();

    User getOne(Integer id);

    List<Map<String, Object>> findAllMap();

    User updateOne(User user);
}
```

`UserDaoImpl.java`
```java
package com.ddf.microservicecloud.provider.dao.impl;

import com.ddf.microservicecloud.api.entity.User;
import com.ddf.microservicecloud.provider.dao.UserDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author DDf on 2018/5/14
 */
@Repository
public class UserDaoImpl implements UserDao {
    private Logger log = LoggerFactory.getLogger(UserDaoImpl.class);
    @Autowired
    private JdbcTemplate jdbcTemplate;


    /**
     * 查询所有的用户
     * @return
     */
    @Override
    public List<User> findAll() {
        String sql = "SELECT * FROM USER WHERE REMOVED = 0";
        return jdbcTemplate.query(sql, new User());
    }

    /**
     * 获得指定的用户
     * value用于指定cacheManager， key指定缓存的key，默认是参数
     * @param id
     * @return
     */
    @Cacheable(value = "user", key = "#id")
    @Override
    public User getOne(Integer id) {
        log.info("查询用户id为{}的数据。。。", id);
        String sql = "SELECT * FROM USER WHERE REMOVED = 0 AND ID = " + id;
        return (User) jdbcTemplate.queryForObject(sql, new User());
    }

    @Override
    public List<Map<String, Object>> findAllMap() {
        String sql = "SELECT * FROM USER WHERE REMOVED = 0 ";
        List<Map<String, Object>> mapList = new ArrayList<>();
        mapList = jdbcTemplate.queryForList(sql, mapList);
        return mapList;
    }

    @Override
    public User updateOne(User user) {
        log.info("更新用户，传入用户信息为[{}]", user.toString());
        String sql = "UPDATE USER SET userName = ?, password = ?, gender = ?, tel = ? WHERE id = ?";
        int update = jdbcTemplate.update(sql, user.getUserName(), user.getPassword(), user.getGender(),
                user.getTel(), user.getId());
        System.out.println(update);
        if (update < 1) {
            throw new RuntimeException("用户更新失败");
        }
        return getOne(user.getId());
    }
```
`UserService.java`
```java
package com.ddf.microservicecloud.provider.service;

import com.ddf.microservicecloud.api.entity.User;

import java.util.List;
import java.util.Map;

/**
 * @author DDf on 2018/5/14
 */

public interface UserService {

    List<User> queryAll();

    User queryOne(Integer id);

    List<Map<String, Object>> queryAllMap();

    User updateOne(User user);
}
```

`UserServiceImpl.java`
```java
package com.ddf.microservicecloud.provider.service.impl;

import com.ddf.microservicecloud.api.entity.User;
import com.ddf.microservicecloud.provider.dao.UserDao;
import com.ddf.microservicecloud.provider.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.beans.Transient;
import java.util.List;
import java.util.Map;

/**
 * @author DDf on 2018/5/14
 */
@Service
public class UserServiceImpl implements UserService {
    private Logger log = LoggerFactory.getLogger(UserServiceImpl.class);
    @Autowired
    private UserDao userDao;

    @Override
    public List<User> queryAll() {
        return userDao.findAll();
    }

    @Override
    public User queryOne(Integer id) {
        return userDao.getOne(id);
    }

    @Override
    public List<Map<String, Object>> queryAllMap() {
        return userDao.findAllMap();
    }

    @Transactional
    @Override
    public User updateOne(User user) {
        return userDao.updateOne(user);
    }
}
```
`UserController.java`
```java
package com.ddf.microservicecloud.provider.controller;

import com.ddf.microservicecloud.api.entity.User;
import com.ddf.microservicecloud.provider.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @author DDf on 2018/5/14
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping("/test")
    public User test() {
        User user = new User(1, "ddf", "123456", "男", "18300001111");
        return user;
    }

    @RequestMapping("/list")
    public List<User> userList() {
        return userService.queryAll();
    }

    @RequestMapping("/user/{id}")
    public User user(@PathVariable Integer id) {
        return userService.queryOne(id);
    }

    @RequestMapping("/list1")
    public List<Map<String, Object>> userMapList() {
        System.out.println("1111111111111");
        return userService.queryAllMap();
    }


    @RequestMapping("/update")
    public User user(@RequestBody User user) {
        return userService.updateOne(user);
    }
}
```

> 修改`microservicecloud-consumer-feign`，来调用服务端新增的接口
`UserFeignService.java`
```java
package com.ddf.microservicecloud.feign.feignservice;

import com.ddf.microservicecloud.api.entity.User;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;

/**
 * @author DDf on 2018/5/28
 * @FeignClient 指定要访问的微服务名
 */
@FeignClient(value="MICROSERVICECLOUD-PROVIDER")
public interface UserFeignService {

    @RequestMapping("/provider/user/user/{id}")
    User queryOne(@PathVariable("id") Integer id);

    /**
     * 这里的RequestMapping要写成当前FeignService要访问的微服务名中定义的对应接口的访问路径，
     * /provider为MICROSERVICECLOUD-PROVIDER的context-path，暂时未找到在一个地方统一定义
     * @return
     */
    @RequestMapping("/provider/user/list")
    List<User> queryAll();

    @RequestMapping("/provider/user/maplist")
    List<Map<String, Object>> queryAllMap();

    @RequestMapping("/provider/user/update")
    User updateOne(User user);
}
```
> `microservicecloud-consumer-feign`修改控制器来调用更新用户的方法

`UserController.java`
```java
package com.ddf.microservicecloud.feign.controller;

import com.ddf.microservicecloud.api.entity.User;
import com.ddf.microservicecloud.feign.feignservice.UserClientService;
import com.ddf.microservicecloud.feign.feignservice.UserFeignService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author DDf on 2018/5/15
 */
@RestController
@RequestMapping("/user")
public class UserController {
    private Logger log = LoggerFactory.getLogger(UserController.class);
    @Autowired
    private UserFeignService userFeignService;
    @Autowired
    private UserClientService userClientService;

    @RequestMapping("/list1")
    public List<User> userList1() {
        return userClientService.queryAll();
    }

    @RequestMapping("/list")
    public List<User> userList() {
        return userFeignService.queryAll();
    }

    @RequestMapping("/user/{id}")
    public User userList(@PathVariable("id") Integer id) {
        return userFeignService.queryOne(id);
    }
    
    @RequestMapping("/update")
    public User updateOne(User user) {
        return userFeignService.updateOne(user);
    }
}
```
> 现在来做一个测试
[http://localhost:9005/feign/user/user/1](http://localhost:9005/feign/user/user/1),先查出用户id为1的用户，然后调用更新接口
[http://localhost:9005/feign/user/update?userName=ddf1&id=1&password=123456&gender=%E7%94%B7&tel=1001012](http://localhost:9005/feign/user/update?userName=ddf1&id=1&password=123456&gender=%E7%94%B7&tel=1001012)，
再来访问[http://localhost:9005/feign/user/user/1](http://localhost:9005/feign/user/user/1)，发现这个时候取的依然是缓存中的数据，那么这个时候是有问题的，因为我们没有在更新用户的同时更新缓存

> 修改`microservicecloud-provider`中的`UserDaoImpl.java`

```java
package com.ddf.microservicecloud.provider.dao.impl;

import com.ddf.microservicecloud.api.entity.User;
import com.ddf.microservicecloud.provider.dao.UserDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author DDf on 2018/5/14
 */
@Repository
public class UserDaoImpl implements UserDao {
    private Logger log = LoggerFactory.getLogger(UserDaoImpl.class);
    @Autowired
    private JdbcTemplate jdbcTemplate;


    /**
     * 查询所有的用户
     * @return
     */
    @Override
    public List<User> findAll() {
        String sql = "SELECT * FROM USER WHERE REMOVED = 0";
        return jdbcTemplate.query(sql, new User());
    }

    /**
     * 获得指定的用户
     * value用于指定cacheManager， key指定缓存的key，默认是参数
     * @param id
     * @return
     */
    @Cacheable(value = "user", key = "#id")
    @Override
    public User getOne(Integer id) {
        log.info("查询用户id为{}的数据。。。", id);
        String sql = "SELECT * FROM USER WHERE REMOVED = 0 AND ID = " + id;
        return (User) jdbcTemplate.queryForObject(sql, new User());
    }

    @Override
    public List<Map<String, Object>> findAllMap() {
        String sql = "SELECT * FROM USER WHERE REMOVED = 0 ";
        List<Map<String, Object>> mapList = new ArrayList<>();
        mapList = jdbcTemplate.queryForList(sql, mapList);
        return mapList;
    }

    /**
     * 更新cacheManager为user的指定key的缓存数据
     * @param user
     * @return
     */
    @CachePut(value = "user", key = "#user.id")
    @Override
    public User updateOne(User user) {
        log.info("更新用户，传入用户信息为[{}]", user.toString());
        String sql = "UPDATE USER SET userName = ?, password = ?, gender = ?, tel = ? WHERE id = ?";
        int update = jdbcTemplate.update(sql, user.getUserName(), user.getPassword(), user.getGender(),
                user.getTel(), user.getId());
        System.out.println(update);
        if (update < 1) {
            throw new RuntimeException("用户更新失败");
        }
        return getOne(user.getId());
    }
```


##### 2.5.6 `@CacheEvict`更新缓存
> 当我们缓存的元数据被删除的时候，应当同时对缓存中的数据进行清除，来保证数据同步
`@CacheEvict`：缓存清除
key：指定要清除的数据
`allEntries = true`：指定清除这个缓存中所有的数据
`beforeInvocation = false`：缓存的清除是否在方法之前执行
默认代表缓存清除操作是在方法执行之后执行;如果出现异常缓存就不会清除
`beforeInvocation = true`：
代表清除缓存操作是在方法运行之前执行，无论方法是否出现异常，缓存都清除

> 在服务端`Dao`, `Service`,`Controller`新增用户删除方法

`UserDao.java`
```java
package com.ddf.microservicecloud.provider.dao;

import com.ddf.microservicecloud.api.entity.User;

import java.util.List;
import java.util.Map;

/**
 * @author DDf on 2018/5/14
 */
public interface UserDao {

    List<User> findAll();

    User getOne(Integer id);

    List<Map<String, Object>> findAllMap();

    User updateOne(User user);

    void delete(Integer id);
}
```

`UserDaoImpl.java`
```java
package com.ddf.microservicecloud.provider.dao.impl;

import com.ddf.microservicecloud.api.entity.User;
import com.ddf.microservicecloud.provider.dao.UserDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author DDf on 2018/5/14
 */
@Repository
public class UserDaoImpl implements UserDao {
    private Logger log = LoggerFactory.getLogger(UserDaoImpl.class);
    @Autowired
    private JdbcTemplate jdbcTemplate;


    /**
     * 查询所有的用户
     * @return
     */
    @Override
    public List<User> findAll() {
        String sql = "SELECT * FROM USER WHERE REMOVED = 0";
        return jdbcTemplate.query(sql, new User());
    }

    /**
     * 获得指定的用户
     * value用于指定cacheManager， key指定缓存的key，默认是参数
     * @param id
     * @return
     */
    @Cacheable(value = "user", key = "#id")
    @Override
    public User getOne(Integer id) {
        log.info("查询用户id为{}的数据。。。", id);
        String sql = "SELECT * FROM USER WHERE REMOVED = 0 AND ID = " + id;
        return (User) jdbcTemplate.queryForObject(sql, new User());
    }

    @Override
    public List<Map<String, Object>> findAllMap() {
        String sql = "SELECT * FROM USER WHERE REMOVED = 0 ";
        List<Map<String, Object>> mapList = new ArrayList<>();
        mapList = jdbcTemplate.queryForList(sql, mapList);
        return mapList;
    }

    /**
     * 更新cacheManager为user的指定key的缓存数据
     * @param user
     * @return
     */
    @CachePut(value = "user", key = "#user.id")
    @Override
    public User updateOne(User user) {
        log.info("更新用户，传入用户信息为[{}]", user.toString());
        String sql = "UPDATE USER SET userName = ?, password = ?, gender = ?, tel = ? WHERE id = ?";
        int update = jdbcTemplate.update(sql, user.getUserName(), user.getPassword(), user.getGender(),
                user.getTel(), user.getId());
        System.out.println(update);
        if (update < 1) {
            throw new RuntimeException("用户更新失败");
        }
        return getOne(user.getId());
    }

    /**
     * 删除cacheManager为user的指定key的缓存数据
     * @param id
     */
    @CacheEvict(value = "user", key = "#id")
    @Override
    public void delete(Integer id) {
        String sql = "UPDATE USER SET REMOVED = 1 WHERE ID = ?";
        int update = jdbcTemplate.update(sql, id);
        if (update < 1) {
            throw new RuntimeException("用户删除失败");
        }
    }
}

```

`UserService.java`
```java
package com.ddf.microservicecloud.provider.service;

import com.ddf.microservicecloud.api.entity.User;

import java.util.List;
import java.util.Map;

/**
 * @author DDf on 2018/5/14
 */

public interface UserService {

    List<User> queryAll();

    User queryOne(Integer id);

    List<Map<String, Object>> queryAllMap();

    User updateOne(User user);

    void delete(Integer id);
}
```

`UserServiceImpl.java`
```java
package com.ddf.microservicecloud.provider.service.impl;

import com.ddf.microservicecloud.api.entity.User;
import com.ddf.microservicecloud.provider.dao.UserDao;
import com.ddf.microservicecloud.provider.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.beans.Transient;
import java.util.List;
import java.util.Map;

/**
 * @author DDf on 2018/5/14
 */
@Service
public class UserServiceImpl implements UserService {
    private Logger log = LoggerFactory.getLogger(UserServiceImpl.class);
    @Autowired
    private UserDao userDao;

    @Override
    public List<User> queryAll() {
        return userDao.findAll();
    }

    @Override
    public User queryOne(Integer id) {
        log.info("查询用户id为{}的数据。。。", id);
        return userDao.getOne(id);
    }

    @Override
    public List<Map<String, Object>> queryAllMap() {
        return userDao.findAllMap();
    }

    @Transactional
    @Override
    public User updateOne(User user) {
        return userDao.updateOne(user);
    }


    @Override
    public void delete(Integer id) {
        userDao.delete(id);
    }
}
```

`UserController.java`
```java
package com.ddf.microservicecloud.provider.controller;

import com.ddf.microservicecloud.api.entity.User;
import com.ddf.microservicecloud.provider.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @author DDf on 2018/5/14
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping("/test")
    public User test() {
        User user = new User(1, "ddf", "123456", "男", "18300001111");
        return user;
    }

    @RequestMapping("/list")
    public List<User> userList() {
        return userService.queryAll();
    }

    @RequestMapping("/user/{id}")
    public User user(@PathVariable Integer id) {
        return userService.queryOne(id);
    }

    @RequestMapping("/list1")
    public List<Map<String, Object>> userMapList() {
        System.out.println("1111111111111");
        return userService.queryAllMap();
    }


    @RequestMapping("/update")
    public User user(@RequestBody User user) {
        return userService.updateOne(user);
    }

    @RequestMapping("/delete/{id}")
    public void delete(@PathVariable Integer id) {
        userService.delete(id);
    }
}
```

> 在`microservicecloud-consumer-feign`增加对服务端的调用接口

`UserFeignService.java`
```java
package com.ddf.microservicecloud.feign.feignservice;

import com.ddf.microservicecloud.api.entity.User;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;

/**
 * @author DDf on 2018/5/28
 * @FeignClient 指定要访问的微服务名
 */
@FeignClient(value="MICROSERVICECLOUD-PROVIDER")
public interface UserFeignService {

    @RequestMapping("/provider/user/user/{id}")
    User queryOne(@PathVariable("id") Integer id);

    /**
     * 这里的RequestMapping要写成当前FeignService要访问的微服务名中定义的对应接口的访问路径，
     * /provider为MICROSERVICECLOUD-PROVIDER的context-path，暂时未找到在一个地方统一定义
     * @return
     */
    @RequestMapping("/provider/user/list")
    List<User> queryAll();

    @RequestMapping("/provider/user/maplist")
    List<Map<String, Object>> queryAllMap();

    @RequestMapping("/provider/user/update")
    User updateOne(User user);

    @RequestMapping("/provider/user/delete/{id}")
    void delete(@PathVariable("id") Integer id);
}
```

`UserController.java`
```java
package com.ddf.microservicecloud.feign.controller;

import com.ddf.microservicecloud.api.entity.User;
import com.ddf.microservicecloud.feign.feignservice.UserClientService;
import com.ddf.microservicecloud.feign.feignservice.UserFeignService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author DDf on 2018/5/15
 */
@RestController
@RequestMapping("/user")
public class UserController {
    private Logger log = LoggerFactory.getLogger(UserController.class);
    @Autowired
    private UserFeignService userFeignService;
    @Autowired
    private UserClientService userClientService;

    @RequestMapping("/list1")
    public List<User> userList1() {
        return userClientService.queryAll();
    }

    @RequestMapping("/list")
    public List<User> userList() {
        return userFeignService.queryAll();
    }

    @RequestMapping("/user/{id}")
    public User userList(@PathVariable("id") Integer id) {
        return userFeignService.queryOne(id);
    }

    @RequestMapping("update")
    public User updateOne(User user) {
        System.out.println(user.toString());
        return userFeignService.updateOne(user);
    }
    
    @RequestMapping("/delete/{id}")
    public void delete(@PathVariable("id") Integer id) {
        userFeignService.delete(id);
    }
}
```

### 3. Redis缓存
[`Redis`官网](https://redis.io/)
####  3.1 安装
##### 3.1.1 下载与解压
```
wget http://download.redis.io/releases/redis-4.0.10.tar.gz
sudo tar xzf redis-4.0.10.tar.gz
```
##### 3.1.2 编译
```
cd redis-4.0.10
sudo make
sudo mv redis-4.0.10 /opt
```
编译前提
编译的前提需要先安装`gcc`，`ubuntu`安装gcc的命令是
```
sudo apt-get install build-essential  
```

##### 3.1.3 配置文件修改
在主目录redis-4.0.10下有一个`redis.conf`文件，默认需要先对一些基础信息进行修改
```
# 只允许本机访问
# bind 127.0.0.1
# 修改为允许所有机器访问
bind 0.0.0.0

# 默认不是后台运行
# daemonize no
# 修改为默认后台运行
daemonize yes
# 开启持久化
appendonly yes
# everysec:表示每秒同步一次（折衷，默认值）
appendfsync everysec

```
##### 3.1.4 启动
已上面配置的配置文件来启动
```
cd /opt/redis/redis-4.0.10
src/redis-server ./redis.conf
```
##### 3.1.5 测试`redis-cli`
```
cd /opt/redis/redis-4.0.10
src/redis-cli
select 0
set test haha
get test
```
####  3.2 使用
> `SpringBoot`使用redis的步骤

* 引入`spring-boot-starter-data-redis`
* 主启动类开启缓存`@EnableCaching`
* 配置文件配置`redis`的连接信息
* `RedisAutoConfiguration`注入了`RedisTemplate`和`StringRedisTemplate`简化对`redis`的存取操作
* Redis常见的五大数据类型
>> 
>> * String（字符串）、List（列表）、Set（集合）、Hash（散列）、ZSet（有序集合）
>> * stringRedisTemplate.opsForValue()[String（字符串）]
>> * stringRedisTemplate.opsForList()[List（列表）]
>> * stringRedisTemplate.opsForSet()[Set（集合）]
>> * stringRedisTemplate.opsForHash()[Hash（散列）]
>> * stringRedisTemplate.opsForZSet()[ZSet（有序集合）]

注意： 
* redis默认是使用jdk的序列化和反序列化来实现数据的编码存储，因此如果使需要对自定义的对象进行存取，默认需要`implements Serializable`
* `RedisDesktopManager`工具提供了对`redis`数据库的可视化界面操作
* `
##### 3.2.1 代码编写
* 开启主配置类的缓存支持
* 配置redis的连接信息
* 修改用户实体类实现`Serializable`接口
* 修改查询用户的方法，使用`RedisTemplate`，将当前结果存储在`redis`中，采取的策略是，服务提供端负责对缓存的存储和同步，即数据提供方要保证缓存中的数据是最新的，而服务消费者只需要用到的时候从缓存中获取就好，不必须发送请求来获取。
###### 3.2.1.1 服务端
服务端（`microservicecloud-provider`）使用`RedisTemplate`
> `pom.xml`文件

```
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <parent>
        <artifactId>microservicecloud-parent</artifactId>
        <groupId>com.ddf.microserviecloud</groupId>
        <version>1.0-SNAPSHOT</version>
        <relativePath>../microservicecloud-parent/pom.xml</relativePath>
    </parent>
    <modelVersion>4.0.0</modelVersion>

    <artifactId>microservicecloud-provider</artifactId>
    <description>微服务提供者Server端</description>


    <dependencies>
        <!-- 引入自定义的模块 -->
        <dependency>
            <groupId>com.ddf.microserviecloud</groupId>
            <artifactId>microservicecloud-api</artifactId>
            <version>1.0-SNAPSHOT</version>
        </dependency>

        <dependency>
            <groupId>junit</groupId>
            <artifactId>junit</artifactId>
        </dependency>

        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
        </dependency>

        <dependency>
            <groupId>com.alibaba</groupId>
            <artifactId>druid</artifactId>
        </dependency>

        <dependency>
            <groupId>ch.qos.logback</groupId>
            <artifactId>logback-core</artifactId>
        </dependency>

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
        </dependency>

        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-jdbc</artifactId>
        </dependency>

        <!-- 修改后立即生效，热部署 -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-loader</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-loader-tools</artifactId>
        </dependency>


        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-devtools</artifactId>
        </dependency>

        <!-- 引入Eureka -->
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
        </dependency>

        <!-- 用于监控信息完善，如构建Info信息 -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-actuator</artifactId>
        </dependency>

        <!-- 引入hystrix -->
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-netflix-hystrix</artifactId>
        </dependency>

        <!-- 引入对缓存的支持 -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-cache</artifactId>
        </dependency>

        <!-- 引入Redis的支持 -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-data-redis</artifactId>
        </dependency>

    </dependencies>
    <build>
        <finalName>microservicecloud-provider</finalName>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
                <configuration>
                    <fork>true</fork>
                    <classifier>exec</classifier>
                </configuration>
                <executions>
                    <execution>
                        <goals>
                            <goal>repackage</goal>
                        </goals>
                    </execution>
                </executions>
            </plugin>
        </plugins>
    </build>
</project>
```

> 主启动类`ProviderApplication`

```java
package com.ddf.microservicecloud.provider;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author DDf on 2018/5/14
 * @EnableEurekaClient 表明当前微服务是Eureka的客户端，将当前微服务注册到Eureka服务端中
 * @EnableCircuitBreaker 对Hystrix熔断的支持
 * @EnableCaching 开启对Spring缓存的支持
 * @EnableTransactionManagement 开启对事务的支持
 */
@SpringBootApplication
@EnableEurekaClient
@EnableCircuitBreaker
@EnableCaching
@EnableAspectJAutoProxy
@EnableTransactionManagement
public class ProviderApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProviderApplication.class, args);
    }
}
```

> 主配置文件`application.yml`

```
server:
  port: 8000 # 配置端口号
  context-path: /provider # 配置当前模块的context-path，即每个请求前都必须附加上这个路径
debug: false
spring:
  application:
    name: microservicecloud-provider # 在使用eurake功能的时候非常重要，这个就是该服务的实例名，eurake可以通过该实例名获得这个服务的其中一个实例（服务端集群）
  datasource:
    type: com.alibaba.druid.pool.DruidDataSource # 当前数据源操作类型
    driver-class-name: com.mysql.cj.jdbc.Driver
    url: jdbc:mysql://localhost:3306/microservicecloud?serverTimezone=UTC # 数据库名称,暂时我这个版本不加serverTimezone会报一个错误
    username: root
    password: 123456
    dbcp2:
      min-idle: 5 # 数据库连接池的最小维持连接数
      initial-size: 5 # 初始化连接数
      max-total: 100 # 最大连接数
      max-wait-millis: 200 # 等待连接获取的最大超时时间

  redis:
    host: 192.168.1.6 # redis主机
    database: 0 # 默认操作哪个数据库
    port: 6379 # 端口号
    pool:
      max-active: 50
      min-idle: 1
      max-idle: 20
eureka:
  client: # 当前微服务只是eureka的客户端
    service-url:
      # 单机使用
      defaultZone: http://eureka7000.com:7000/eureka
      ## eureka集群时使用
      # defaultZone: http://eureka7000.com:7000/eureka,http://eureka7001.com:7001/eureka,http://eureka7002.com:7002/eureka # 将当前微服务注册到指定的eureka服务端中

  instance:
      instance-id: microservicecloud-provider
      prefer-ip-address: true #在eureka管理界面查看微服务路径可以显示IP地址

info:
  app-name: microservicecloud-provider
  copyright: ddf
  description: "微服务提供者"
logging:
  level: debug
```

> 用户实体类修改，修改通用api,`microservicecloud-api`,修改后clean install，然后供其他模块引用使用

```
package com.ddf.microservicecloud.api.entity;

import org.springframework.jdbc.core.RowMapper;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author DDf on 2018/5/14
 */
public class User implements RowMapper, Serializable {
    private Integer id;
    private String userName;
    private String password;
    private String gender;
    private String tel;
    private String dbSource;
    private Integer removed;
    private String errorMessage;

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public User() {
    }

    public User(Integer id, String userName, String password, String gender, String tel) {
        this.id = id;
        this.userName = userName;
        this.password = password;
        this.gender = gender;
        this.tel = tel;
        this.dbSource = "dbSource-01";
        this.removed = 0;
    }

    public User(Integer id, String userName, String password, String gender, String tel, String dbSource, Integer removed) {
        this.id = id;
        this.userName = userName;
        this.password = password;
        this.gender = gender;
        this.tel = tel;
        this.dbSource = dbSource;
        this.removed = removed;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getDbSource() {
        return dbSource;
    }

    public void setDbSource(String dbSource) {
        this.dbSource = dbSource;
    }

    public Integer getRemoved() {
        return removed;
    }

    public void setRemoved(Integer removed) {
        this.removed = removed;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", gender='" + gender + '\'' +
                ", tel='" + tel + '\'' +
                ", dbSource='" + dbSource + '\'' +
                ", removed=" + removed +
                '}';
    }

    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
        User user = new User();
        user.setId(rs.getInt("id"));
        user.setUserName(rs.getString("userName"));
        user.setPassword(rs.getString("password"));
        user.setGender(rs.getString("gender"));
        user.setTel(rs.getString("tel"));
        user.setDbSource(rs.getString("dbSource"));
        user.setRemoved(rs.getInt("removed"));
        return user;
    }
}
```


> `UserDao`负责缓存的存入和更新

```java
package com.ddf.microservicecloud.provider.dao.impl;

import com.ddf.microservicecloud.api.entity.User;
import com.ddf.microservicecloud.provider.dao.UserDao;
import com.ddf.microservicecloud.provider.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author DDf on 2018/5/14
 */
@Repository
public class UserDaoImpl implements UserDao {
    private Logger log = LoggerFactory.getLogger(UserDaoImpl.class);
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private RedisTemplate redisTemplate;


    /**
     * 查询所有的用户（使用redis存取所有用户列表）
     * @return
     */
    @Override
    public List<User> findAll() {
        List<User> userList = syncUserList();
        return userList;
    }

    private List<User> syncUserList() {
        String sql = "SELECT * FROM USER WHERE REMOVED = 0";
        List<User> userList = jdbcTemplate.query(sql, new User());
        try {
            redisTemplate.opsForHash().put("user", "users", userList);
        } catch (Exception e) {
            // 如果存入（更新）失败，那么采取清空缓存的方法，保证垃圾数据不会被使用
            redisTemplate.opsForHash().put("user", "users", null);
        }
        return userList;
    }

    /**
     * 获得指定的用户
     * value用于指定cacheManager， key指定缓存的key，默认是参数
     * @param id
     * @return
     */
    @Cacheable(value = "user", key = "#id")
    @Override
    public User getOne(Integer id) {
        log.info("查询用户id为{}的数据。。。", id);
        String sql = "SELECT * FROM USER WHERE REMOVED = 0 AND ID = " + id;
        return (User) jdbcTemplate.queryForObject(sql, new User());
    }

    @Override
    public List<Map<String, Object>> findAllMap() {
        String sql = "SELECT * FROM USER WHERE REMOVED = 0 ";
        List<Map<String, Object>> mapList = new ArrayList<>();
        mapList = jdbcTemplate.queryForList(sql, mapList);
        return mapList;
    }

    /**
     * 更新cacheManager为user的指定key的缓存数据
     * @param user
     * @return
     */
    @CachePut(value = "user", key = "#user.id")
    @Override
    public User updateOne(User user) {
        log.info("更新用户，传入用户信息为[{}]", user.toString());
        String sql = "UPDATE USER SET userName = ?, password = ?, gender = ?, tel = ? WHERE id = ?";
        int update = jdbcTemplate.update(sql, user.getUserName(), user.getPassword(), user.getGender(),
                user.getTel(), user.getId());
        System.out.println(update);
        if (update < 1) {
            throw new RuntimeException("用户更新失败");
        }
        syncUserList();
        return getOne(user.getId());
    }

    /**
     * 删除cacheManager为user的指定key的缓存数据
     * @param id
     */
    @CacheEvict(value = "user", key = "#id")
    @Override
    public void delete(Integer id) {
        String sql = "UPDATE USER SET REMOVED = 1 WHERE ID = ?";
        int update = jdbcTemplate.update(sql, id);
        if (update < 1) {
            throw new RuntimeException("用户删除失败");
        }
        syncUserList();
    }
}
```

###### 3.2.1.2 客户端
> `pom.xml`引入缓存

```
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <parent>
        <artifactId>microservicecloud-parent</artifactId>
        <groupId>com.ddf.microserviecloud</groupId>
        <version>1.0-SNAPSHOT</version>
        <relativePath>../microservicecloud-parent/pom.xml</relativePath>
    </parent>
    <modelVersion>4.0.0</modelVersion>

    <artifactId>microservicecloud-consumer-feign</artifactId>
    <description>微服务消费者client端</description>

    <dependencies>
        <!-- 自己定义的api -->
        <dependency>
            <groupId>com.ddf.microserviecloud</groupId>
            <artifactId>microservicecloud-api</artifactId>
            <version>${project.version}</version>
        </dependency>

        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-openfeign</artifactId>
        </dependency>

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>

        <!-- 引入Eureka -->
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
        </dependency>

        <!-- 修改后立即生效，热部署 -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-loader</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-loader-tools</artifactId>
        </dependency>


        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-devtools</artifactId>
        </dependency>

        <!-- 引入邮件开发 -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-mail</artifactId>
        </dependency>

        <!-- 引入对缓存的支持 -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-cache</artifactId>
        </dependency>

        <!-- 引入Redis的支持 -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-data-redis</artifactId>
        </dependency>

    </dependencies>

    <build>
        <finalName>microservicecloud-consumer-feign</finalName>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
                <configuration>
                    <fork>true</fork>
                    <classifier>exec</classifier>
                </configuration>
                <executions>
                    <execution>
                        <goals>
                            <goal>repackage</goal>
                        </goals>
                    </execution>
                </executions>
            </plugin>
        </plugins>
    </build>
</project>
```

> 主启动类

```java
package com.ddf.microservicecloud.feign;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author DDf on 2018/5/15
 * @EnableEurekaClient 表明当前模块是eureka的客户端
 * @EnableFeignClients 启动Feign相关功能
 * @EnableScheduling 启动对定时任务的支持
 * @EnableAsync 开启对异步调用的支持
 */
@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients
@ComponentScan("com.ddf.microservicecloud")
@EnableScheduling
@EnableAsync
@EnableCaching
public class FeignApplication {
    public static void main(String[] args) {
        SpringApplication.run(FeignApplication.class, args);
    }
}
```

> 主配置文件

```
server:
  port: 9005 # 当前微服务的端口号
  context-path: /feign #每个请求都必须加上这个路径
eureka:
  client:
    register-with-eureka: false #当前模块是服务消费者，只需要调用服务提供者的接口接口，而服务提供者已经注册，所以这里不需要注册到eureka
    service-url:
      defaultZone: http://eureka7000.com:7000/eureka
     # eureka集群时使用
     # defaultZone: http://eureka7000.com:7000/eureka,http://eureka7001.com:7001/eureka,http://eureka7002.com:7002/eureka
ribbon:
  ReadTimeout: 20000 # 如果使用feign，默认超时时间为1000ms，很容易就超时
  ConnectTimeout: 5000 # 如果使用feign，默认超时时间为1000ms，很容易就超时
spring:
  mail:
    username: 1041765757@qq.com # 用来验证授权的邮件用户名
    password: gotartrfwuytbcji # 根据QQ邮箱设置-账户里生成的第三方登陆授权码，可用来代替密码登陆
    host: smtp.qq.com # 邮件服务器类型
    properties.mail.smtp.ssl.enable: true # 用以支持授权码登陆

  redis:
    host: 192.168.1.6 # redis主机
    database: 0 # 默认操作哪个数据库
    port: 6379 # 端口号
    pool:
      max-active: 50
      min-idle: 1
      max-idle: 20
```

> 修改`UserController`，尝试从redis中获取数据

```java
package com.ddf.microservicecloud.feign.controller;

import com.ddf.microservicecloud.api.entity.User;
import com.ddf.microservicecloud.feign.feignservice.UserClientService;
import com.ddf.microservicecloud.feign.feignservice.UserFeignService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author DDf on 2018/5/15
 */
@RestController
@RequestMapping("/user")
public class UserController {
    private Logger log = LoggerFactory.getLogger(UserController.class);
    @Autowired
    private UserFeignService userFeignService;
    @Autowired
    private UserClientService userClientService;
    @Autowired
    private RedisTemplate redisTemplate;

    @RequestMapping("/list1")
    public List<User> userList1() {
        return userClientService.queryAll();
    }

    @RequestMapping("/list")
    public List<User> userList() {
        try {
            List<User> userList = (List<User>) redisTemplate.opsForHash().get("user", "users");
            log.info("从redis中获取数据。。。。。");
            log.info("获得数据： {}", userList);
            if (userList == null || userList.isEmpty()) {
                // 为保险起见，如果数据为空，发送http请求来获得一次最新的数据
                log.info("从redis中获得数据为空，尝试发送请求获取数据。。。。");
                return userFeignService.queryAll();
            }
            return userList;
        } catch (Exception e) {
            // 如果从redis中获取失败，那么再尝试发送Http请求获取数据
            log.info("从redis中获得数据失败，尝试发送请求获取数据。。。。");
            return userFeignService.queryAll();
        }
    }

    @RequestMapping("/user/{id}")
    public User userList(@PathVariable("id") Integer id) {
        return userFeignService.queryOne(id);
    }

    @RequestMapping("update")
    public User updateOne(User user) {
        System.out.println(user.toString());
        return userFeignService.updateOne(user);
    }

    @RequestMapping("/delete/{id}")
    public void delete(@PathVariable("id") Integer id) {
        userFeignService.delete(id);
    }
}
```
##### 3.2.2 初步测试
* 启动eureka  `microservicecloud-eureka`
* 启动微服务提供者 `microservicecloud-provider`
* 启动微服务客户端 `microservicecloud-consumer-feign`
> 第一次访问客户端查询用户列表[http://localhost:9005/feign/user/list](http://localhost:9005/feign/user/list)
控制台打印如下：

```
2018-06-19 22:46:10.446 INFO 12824 --- [nio-9005-exec-1] c.d.m.feign.controller.UserController : 从redis中获取数据。。。。。
2018-06-19 22:46:10.446 INFO 12824 --- [nio-9005-exec-1] c.d.m.feign.controller.UserController : 获得数据： null
2018-06-19 22:46:10.446 INFO 12824 --- [nio-9005-exec-1] c.d.m.feign.controller.UserController : 从redis中获得数据为空，尝试发送请求获取数据。。。。
```


> 第二次访问查询用户列表控制台打印如下

```
2018-06-19 22:19:18.360 INFO 3628 --- [nio-9005-exec-7] c.d.m.feign.controller.UserController : 从redis中获取数据。。。。。
2018-06-19 22:19:18.360 INFO 3628 --- [nio-9005-exec-7] c.d.m.feign.controller.UserController : 获得数据： [User{id=2, userName='chen', password='00000000', gender='男', tel='15688889999', dbSource='dbSource-01', removed=0}, User{id=3, userName='hong', password='99999999', gender='女', tel='021-55559999', dbSource='dbSource-01', removed=0}]
```

> 通过工具链接redis查看数据，可以看到当前数据存储的都是字节文件，默认使用jdk的序列化之后再存储的，下面的章节演示将数据存储成json格式

##### 3.2.3 JSON格式存取探究
 * 为什么redis默认使用的是jdk的序列化
> org.springframework.data.redis.core.RedisTemplate
```java
public void afterPropertiesSet() {
        super.afterPropertiesSet();
        boolean defaultUsed = false;
        if (this.defaultSerializer == null) {
            this.defaultSerializer = new JdkSerializationRedisSerializer(this.classLoader != null ? this.classLoader : this.getClass().getClassLoader());
        }
        // .............
}
```
* 查看源码发现`defaultSerializer `这个属性是存在set方法的，因此能不能自己注册个`RestTemplate`,然后设置下他的序列化方式呢？如下为序列化类的
关系树
> 


* 自己注册个RestTemplate，然后调用`setDefaultSerializer`来改变试试
> 修改`microservicecloud-provicer`的主启动类，来注册一个`RestTemplate`，代码格式参考源码类`RedisAutoConfiguration.RedisConfiguration`源码如下“
```java
    @Bean
  @ConditionalOnMissingBean(name = "redisTemplate")
  public RedisTemplate<Object, Object> redisTemplate(
    RedisConnectionFactory redisConnectionFactory)
      throws UnknownHostException {
   RedisTemplate<Object, Object> template = new RedisTemplate<Object, Object>();
   template.setConnectionFactory(redisConnectionFactory);
   return template;
  }
```
在使用Redis的模块的主启动类上重新注册一个bean，如`microservicecloud-provider`, `microservicecloud-consumer-feign`
```java
    @Bean
    @ConditionalOnMissingBean(name = "redisTemplate")
    public RedisTemplate<Object, Object> redisTemplate(
            RedisConnectionFactory redisConnectionFactory){
        RedisTemplate<Object, Object> template = new RedisTemplate<Object, Object>();
        template.setConnectionFactory(redisConnectionFactory);
        template.setDefaultSerializer(new GenericJackson2JsonRedisSerializer());
        return template;
    }
```
再次尝试查询用户列表[http://localhost:9005/feign/user/list](http://localhost:9005/feign/user/list)
使用redis可视化工具查看如下，应该是可行的。


## 十七、 Elasticsearch
本机内容如无特别说明，依然使用`microservicecloud-consumer-feign`模块演示
[spring-boot官方资料](https://docs.spring.io/spring-boot/docs/2.0.3.RELEASE/reference/htmlsingle/#boot-features-elasticsearch)
[Elasticsearch官方资料](https://www.elastic.co/products/elasticsearch)
[Jest官方文档](https://github.com/searchbox-io/Jest/tree/master/jest)
asticsearch是一个分布式的RESTful搜索和分析引擎，能够解决越来越多的用例。作为Elastic Stack的核心，它集中存储数据，以便您发现预期并发现意外情况。
Elasticsearch是一个开源的，分布式的实时搜索和分析引擎。Spring Boot为Elasticsearch提供了基本的自动配置，并为Spring Data Elasticsearch提供了抽象 。有一个spring-boot-starter-data-elasticsearch“Starter”以便捷的方式收集依赖关系。Spring Boot也支持 Jest。

### 1. 实现方式
SpringBoot默认支持两种技术来和ES交互；
* Jest（默认不生效）需要导入jest的工具包（io.searchbox.client.JestClient）
* SpringData ElasticSearch【ES版本有可能不合适】版本适配说明：https://github.com/spring-projects/spring-data-elasticsearch
### 2. 使用方式
基于SpringData ElasticSearch实现方式的适配性问题，这里使用Jest，有关于Jest的详细使用[参考](https://github.com/searchbox-io/Jest/tree/master/jest)
#### 2.1 引入Jest依赖
```
<!-- 引入对Jest的支持，Jest是用来操作Elasticsearch的工具 -->
        <dependency>
            <groupId>io.searchbox</groupId>
            <artifactId>jest</artifactId>
            <version>5.3.3</version>
        </dependency>
```
实例完整版`pom`如下
```
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <parent>
        <artifactId>microservicecloud-parent</artifactId>
        <groupId>com.ddf.microserviecloud</groupId>
        <version>1.0-SNAPSHOT</version>
        <relativePath>../microservicecloud-parent/pom.xml</relativePath>
    </parent>
    <modelVersion>4.0.0</modelVersion>

    <artifactId>microservicecloud-consumer-feign</artifactId>
    <description>微服务消费者client端</description>

    <dependencies>
        <!-- 自己定义的api -->
        <dependency>
            <groupId>com.ddf.microserviecloud</groupId>
            <artifactId>microservicecloud-api</artifactId>
            <version>${project.version}</version>
        </dependency>

        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-openfeign</artifactId>
        </dependency>

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>

        <!-- 引入Eureka -->
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
        </dependency>

        <!-- 修改后立即生效，热部署 -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-loader</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-loader-tools</artifactId>
        </dependency>

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-devtools</artifactId>
        </dependency>

        <!-- 引入邮件开发 -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-mail</artifactId>
        </dependency>

        <!-- 引入对缓存的支持 -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-cache</artifactId>
        </dependency>

        <!-- 引入Redis的支持 -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-data-redis</artifactId>
        </dependency>

        <!-- 引入对Jest的支持，Jest是用来操作Elasticsearch的工具 -->
        <dependency>
            <groupId>io.searchbox</groupId>
            <artifactId>jest</artifactId>
            <version>5.3.3</version>
        </dependency>

    </dependencies>

    <build>
        <finalName>microservicecloud-consumer-feign</finalName>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
                <configuration>
                    <fork>true</fork>
                    <classifier>exec</classifier>
                </configuration>
                <executions>
                    <execution>
                        <goals>
                            <goal>repackage</goal>
                        </goals>
                    </execution>
                </executions>
            </plugin>
        </plugins>
    </build>
</project>
```

#### 2.2 主配置类修改
在主配置类文件下配置`Jest`需要连接的`Elasticsearch`的主机,完成`application.yml`文件内容如下
```
server:
  port: 9005 # 当前微服务的端口号
  context-path: /feign #每个请求都必须加上这个路径
eureka:
  client:
    register-with-eureka: false #当前模块是服务消费者，只需要调用服务提供者的接口接口，而服务提供者已经注册，所以这里不需要注册到eureka
    service-url:
      defaultZone: http://eureka7000.com:7000/eureka
     # eureka集群时使用
     # defaultZone: http://eureka7000.com:7000/eureka,http://eureka7001.com:7001/eureka,http://eureka7002.com:7002/eureka
ribbon:
  ReadTimeout: 20000 # 如果使用feign，默认超时时间为1000ms，很容易就超时
  ConnectTimeout: 5000 # 如果使用feign，默认超时时间为1000ms，很容易就超时
spring:
  mail:
    username: 1041765757@qq.com # 用来验证授权的邮件用户名
    password: gotartrfwuytbcji # 根据QQ邮箱设置-账户里生成的第三方登陆授权码，可用来代替密码登陆
    host: smtp.qq.com # 邮件服务器类型
    properties.mail.smtp.ssl.enable: true # 用以支持授权码登陆

  redis:
    host: 192.168.1.6 # redis主机
    database: 0 # 默认操作哪个数据库
    port: 6379 # 端口号
    pool:
      max-active: 50
      min-idle: 1
      max-idle: 20


  elasticsearch:
    jest: # Jest是用来与elasticsearch交互的工具
      uris:
        - http://localhost:9200
```

#### 2.3 JestClient的使用
在主配置类中如果正确配置了`Jest`，那么springboot便会自动配置一个可注入的`bean``JestClient`，有关`JestClient`更佳详细的使用[参考]([Jest官方文档](https://github.com/searchbox-io/Jest/tree/master/jest))

* 新增`com.ddf.microservicecloud.feign.elasticsearch.JestService`
```java
package com.ddf.microservicecloud.feign.elasticsearch;

import com.ddf.microservicecloud.api.entity.User;
import com.ddf.microservicecloud.feign.feignservice.UserFeignService;
import io.searchbox.client.JestClient;
import io.searchbox.core.Index;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
```

* 新增`com.ddf.microservicecloud.feign.elasticsearch.JestController`来从界面访问测试
```java
package com.ddf.microservicecloud.feign.elasticsearch;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author DDf on 2018/6/25
 */
@RestController
@RequestMapping("/jest")
public class JestController {
    @Autowired
    private JestService jestService;

    @RequestMapping("/indexAllUser")
    public void indexAllUser() {
        jestService.indexAllUser();
    }
}
```

## 十八、`可视化监控`spring-boot-admin`
[官方文档](http://codecentric.github.io/spring-boot-admin/2.0.1/#getting-started)
[官方代码示例](https://github.com/codecentric/spring-boot-admin/tree/master/spring-boot-admin-samples)
### 1. What is Spring Boot Admin?
codecentric’s Spring Boot Admin is a community project to manage and monitor your Spring Boot ® applications. The applications register with our Spring Boot Admin Client (via HTTP) or are discovered using Spring Cloud ® (e.g. Eureka, Consul). The UI is just an AngularJs application on top of the Spring Boot Actuator endpoints.
### 2. 入门
#### 2.1 设置Spring Boot Admin Server
首先，您需要设置您的服务器。要做到这一点，只需设置一个简单的启动项目（使用start.spring.io）。由于Spring Boot Admin Server能够作为servlet或webflux应用程序运行，因此您需要决定并添加相应的Spring Boot Starter。在这个例子中，我们使用servlet web starter。
* **新建一个项目`microservicecloud-admin-server`，`pom`引入依赖**
```
<dependency>
    <groupId>de.codecentric</groupId>
    <artifactId>spring-boot-admin-starter-server</artifactId>
    <version>2.0.1</version>
</dependency>
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
</dependency>
```

* **完整版`pom`如下**
```
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <parent>
        <artifactId>microservicecloud-parent</artifactId>
        <groupId>com.ddf.microserviecloud</groupId>
        <version>1.0-SNAPSHOT</version>
        <relativePath>../microservicecloud-parent/pom.xml</relativePath>
    </parent>
    <modelVersion>4.0.0</modelVersion>

    <artifactId>microservicecloud-admin-server</artifactId>

    <dependencies>
        <dependency>
            <groupId>de.codecentric</groupId>
            <artifactId>spring-boot-admin-starter-server</artifactId>
            <version>2.0.1</version>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
    </dependencies>
</project>
```
* **新建主配置文件`application.yml`，暂时只指定端口号**
```
server:
  port: 9009
```
* **建立项目主启动类`AdminServerApplication`,配合注解`@EnableAdminServer`来表示当前是监控`Server`端**
```java
package com.ddf.microservicecloud.admin_server;

import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author DDf on 2018/7/4
 * @EnableAdminServer 表示当前是一个监控server端
 */
@SpringBootApplication
@EnableAdminServer
public class AdminServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(AdminServerApplication.class, args);
    }
}

```
5. 启动项目，访问`localhost:9009`，因为暂时只是一个`admin-server`，并且没有任何服务与之关联，所以现在的界面如下所示


#### 2.2 注册客户端应用
To register your application at the SBA Server you can either include the SBA Client or use Spring Cloud Discovery(e.g. Eureka, Consul, …). There is also a simple option using a static configuration on the SBA Server side.
也就是说，如果使用`Eureka`的情况下，根本不需要再客户端手动注册到服务端，而只需要在`admin-server`端加入`eureka-client`的支持，并且在配置文件中指向`eureka`的地址，那么`admin-server`就会自动在`eureka`上发现注册的服务
* **重要： 如何暴露自身的端点以便被监控**
默认的每个客户端暴露的端点只有`info`和`health`，如果需要自定义暴露端口，需要在每个客户端的配置文件下加入如下配置,
下面的配置是测试使用，把所有的端点都暴露的，实际使用根据情况选择，这一块由于版本不同，关闭安全验证和暴露端点的方法也不同，以下是2.0以后的写法
```
management:
  endpoints:
    web:
      exposure:
        include: "*"
  endpoint:
    health:
      show-details: ALWAYS
```
接上，如果是2.0以前的写法，则应该如下，并且在下面的步骤中不需要配置`SecurityPermitAllConfig `这个类
```
management.security.enabled: false
```
* **修改`pom.xml`，引入`eureka-client`和`spring-security`**
```
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <parent>
        <artifactId>microservicecloud-parent</artifactId>
        <groupId>com.ddf.microserviecloud</groupId>
        <version>1.0-SNAPSHOT</version>
        <relativePath>../microservicecloud-parent/pom.xml</relativePath>
    </parent>
    <modelVersion>4.0.0</modelVersion>

    <artifactId>microservicecloud-admin-server</artifactId>

    <dependencies>
        <dependency>
            <groupId>de.codecentric</groupId>
            <artifactId>spring-boot-admin-starter-server</artifactId>
            <version>2.0.1</version>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-security</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-commons</artifactId>
        </dependency>
    </dependencies>
</project>
```

* **配置文件`application.yml`修改，加入`eureka`地址，和配置来两个profile的security，一个是包含了验证信息的`security`,一个是不包含验证信息的`insecure`.注，一下配置文件皆来自官网文档的例子，我这里的例子默认激活来不需要验证用户名密码**
```java

eureka:
  instance:
    leaseRenewalIntervalInSeconds: 10
    health-check-url-path: /actuator/health
  client:
    registryFetchIntervalSeconds: 5
    serviceUrl:
      # 这一块需要注意看，这个和注册到eureka上的写法不同，加了个环境变量
      defaultZone: ${EUREKA_SERVICE_URL:http://eureka7000.com:7000}/eureka/

# 这一块的配置是为了暴露监控端点，详细请参考官方
management:
  endpoints:
    web:
      exposure:
        include: "*"  
  endpoint:
    health:
      show-details: ALWAYS
server:
  port: 9009

spring:
  application:
    name: ADMIN-SERVER
  profiles:
    active: insecure


---
spring:
  profiles: insecure

---
spring:
  profiles: secure
  security:
    user:
      name: "ddf"
      password: "123456"
eureka:
  instance:
    metadata-map:
      user.name: "ddf" #These two are needed so that the server
      user.password: "123456" #can access the protected client endpoints
```
* **修改主启动类`AdminServerApplication.java`**
```java
package com.ddf.microservicecloud.admin_server;

import de.codecentric.boot.admin.server.config.AdminServerProperties;
import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

/**
 * @author DDf on 2018/7/4
 * @EnableAdminServer 表示当前是一个admin监控server端
 * @EnableDiscoveryClient 用于从eureka中发现注册的服务列表
 */
@Configuration
@EnableAutoConfiguration
@EnableDiscoveryClient
@EnableAdminServer
public class AdminServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(AdminServerApplication.class, args);
    }

    @Profile("insecure")
    @Configuration
    public static class SecurityPermitAllConfig extends WebSecurityConfigurerAdapter {
        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http.authorizeRequests()
                    .anyRequest()
                    .permitAll()
                    .and()
                    .csrf()
                    .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                    .ignoringAntMatchers("/instances", "/actuator/**");
        }
    }

    @Profile("secure")
    @Configuration
    public static class SecuritySecureConfig extends WebSecurityConfigurerAdapter {
        private final String adminContextPath;

        public SecuritySecureConfig(AdminServerProperties adminServerProperties) {
            this.adminContextPath = adminServerProperties.getContextPath();
        }

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            // @formatter:off
            SavedRequestAwareAuthenticationSuccessHandler successHandler = new SavedRequestAwareAuthenticationSuccessHandler();
            successHandler.setTargetUrlParameter("redirectTo");

            http.authorizeRequests()
                    .antMatchers(adminContextPath + "/assets/**").permitAll()
                    .antMatchers(adminContextPath + "/login").permitAll()
                    .anyRequest().authenticated()
                    .and()
                    .formLogin().loginPage(adminContextPath + "/login").successHandler(successHandler).and()
                    .logout().logoutUrl(adminContextPath + "/logout").and()
                    .httpBasic().and()
                    .csrf()
                    .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                    .ignoringAntMatchers("/instances", "/actuator/**");
            // @formatter:on
        }
    }
}
```

* **启动后如图，可以每个服务下的生成的如`7406bc9e363d`，点击进入查看detail**





* ** 加入用户认证**