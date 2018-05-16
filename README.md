[TOC]

# SpringCloud的使用

> 笔记内容来源于尚硅谷的`Spring-Cloud`的视频教程，以及`spring-boot`相关教程，但是`Spring-Cloud`教程里只提供了源码，却未提供笔记，以下所有内容来源于根据视频和源码参考整理。
如果没看过这套视频并且有意向的，可以[点击这里跳转](http://www.gulixueyuan.com/my/course/246),嗯,视频是收费的.
区别如下： 
视频中使用的`Eclipse`，本笔记使用的是`IDEA`
视频中使用了`mybatis`，本笔记内容使用了`JdbcTemplate`
视频中是使用了部门实体来做实验，本笔记内容使用了用户实体来做整理
因视频看完大概看完之后才发现没有笔记，于是开始整理笔记内容，但是已经没有精力再看一遍视频，因此只是根据目录然后参考源码整理，可能有遗漏。
另外一些名词专业术语，只听得大概，没有经过专业整理


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
> 步骤省略，参考上面建立子模块的步骤，只是在确定`GAV`坐标的界面，有两个属性需要注意，`Add as module to`和`Parent`











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
package com.ddf.microservicecloud.entity;
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
            <artifactId>spring-cloud-starter-eureka</artifactId>
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
            <groupId>org.springframework</groupId>
            <artifactId>springloaded</artifactId>
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


#### 3.2 添加当前模块主启动类`Application.java`
> 因为我们第一步是测试当前模块能不能正常启动，所以先不配置数据源，如果在启动的时候报找不到datasource，那是因为导入了相关的操作数据库的jar包开启了boot相关的自动配置功能，
那么只要在主启动类上加入`exclude `即可。这里做下记录功能，如果导包的时候在这里不导入相关jar包，则不需要用到这个功能。

```java
package com.ddf.microservicecloud;
/**
 * @author DDf on 2018/5/14
 */
package com.ddf.microservicecloud;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class, HibernateJpaAutoConfiguration.class})
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
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
package com.ddf.microservicecloud.controller;
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
package com.ddf.microservicecloud;
/**
* @author DDf on 2018/5/14
*/
package com.ddf.microservicecloud;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;

@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}

```

### 5. 建立用户相关的业务操作类
#### 5.1 新建`User`相关的`DAO`类
> 建立UserDao接口

```java
package com.ddf.microservicecloud.dao;
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
package com.ddf.microservicecloud.dao.impl;
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
        String sql = "SELECT * FROM USER ";
        return jdbcTemplate.query(sql, new User());
    }
    @Override
    public User getOne(Integer id) {
        String sql = "SELECT * FROM USER WHERE ID = " + id;
        return (User) jdbcTemplate.queryForObject(sql, new User());
    }
    @Override
    public List<Map<String, Object>> findAllMap() {
        String sql = "SELECT * FROM USER ";
        List<Map<String, Object>> mapList = new ArrayList<>();
        mapList = jdbcTemplate.queryForList(sql, mapList);
        return mapList;
    }
}
```

#### 5.2 新建`User`的`service`层
> 新建`UserService`接口类

```java
package com.ddf.microservicecloud.service;
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
package com.ddf.microservicecloud.service.impl;
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
package com.ddf.microservicecloud;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
/**
 * @author DDf on 2018/5/14
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class ApplicationTest {
}
```

> 建立`UserService`的测试类`UserServiceTest`，注意要保持和`UserService`相同的包路径

```java
package com.ddf.microservicecloud.service;
import com.ddf.microservicecloud.ApplicationTest;
import com.ddf.microservicecloud.entity.User;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
/**
 * @author DDf on 2018/5/14
 */
public class UserServiceTest extends ApplicationTest {
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
package com.ddf.microservicecloud.controller;
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
            <groupId>org.springframework</groupId>
            <artifactId>springloaded</artifactId>
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

### 4. 编写客户端的主启动类`Application.java`


```java
package com.ddf.microservicecloud;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
/**
 * @author DDf on 2018/5/15
 */
@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
```

### 5. 编写配置类`ConsumerConfiguration`，注册`RestTemplate`
> `spring-boot`建议使用注解形式来替换原来繁杂的配置文件，使用`@Configuration`标注在类上，即可标名这是一个配置类

```java
package com.ddf.microservicecloud.configuration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
     * @return
     */
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
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
package com.ddf.microservicecloud.api;
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
package com.ddf.microservicecloud.controller;
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
关于`Eureka`的详细信息，[请参考文档](https://springcloud.cc/spring-cloud-dalston.html#_registering_with_eureka)

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
            <artifactId>spring-cloud-starter-eureka-server</artifactId>
        </dependency>
        <!-- 修改后立即生效，热部署 -->
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>springloaded</artifactId>
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





### 4. 编写主启动类`Application.java`
```java
package com.ddf.microservicecloud;
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
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
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
            <groupId>org.springframework</groupId>
            <artifactId>springloaded</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-devtools</artifactId>
        </dependency>
        <!-- 引入Eureka -->
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-eureka</artifactId>
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

#### 1.3 修改主启动类`Application.java`
> 使用注解`@EnableEurekaClient`来实现客户端向服务端的服务注册，对应服务端的`@EnableEurekaServer`来允许服务注册

```java
package com.ddf.microservicecloud;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
/**
 * @author DDf on 2018/5/14
 * @EnableEurekaClient 表明当前微服务是Eureka的客户端，将当前微服务注册到Eureka服务端中
 */
@SpringBootApplication
@EnableEurekaClient
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
```

#### 1.4 验证`microservice-provider`是否成功注册进`Eureka`
> 首先启动 `Eureka`服务端`microservicecloud-eureka`， 然后启动服务端`microservicecloud-provider`。再进入`Eureka`的管理界面来看一下，可以看到下面的服务列表里已经有了我们刚刚注册的`microservicecloud-provider`，同时为什么下面还有一个UNKNOWN以及上面的一串红色的警告,这个就是`Eureka`的自我保护机制。




#### 1.5 `Eureka`的自我保护机制
> 参考上图就是自我保护机制的效果，`Eureka`会每隔一段时间，默认15分钟就会向所有的注册服务发送心跳测试，如果这个服务能够在指定时间内保持心跳，则这个服务就会是一个健康的服务，然而并不是所有的服务都能够与服务端保持长时间的连接操作，而这段时间的无操作,`Eureka`就会认为这个服务是不健康的，就会中断集群之间的数据同步，但是`Eureka`并不会删除这个认为不健康的连接，因为不健康的连接并不总是一个down掉的服务，如果网络恢复后，那么这个服务就会恢复正常，`Eureka`又会把这个服务当作一个健康的服务，通过这种机制，可以最大限度的保证不会因为网络问题而导致的注册卡顿和服务误删的操作，同时开启同步多个Eureka之间的注册服务。(纯手写，详细介绍请参考官方文档)

### 2. `microservicecloud-consumer`中服务消费者客户端来使用`Eureka`
> 之前我们已经完成了客户端来调用服务端提供的服务来正确返回数据，但那个时候的测试我们是通过`url`这种固定的方式来硬编码，而且不适用于如果服务端做了集群的情况，因此在这个章节开始在服务消费者客户端使用`Eureka`来调用我们的微服务提供者的接口

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
            <artifactId>spring-cloud-starter-eureka</artifactId>
        </dependency>
        <!-- 修改后立即生效，热部署 -->
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>springloaded</artifactId>
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
#### 2.3 修改主启动类`Application.java`
> 加入注解`@EnableEurekaClient `

```java
package com.ddf.microservicecloud;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
/**
 * @author DDf on 2018/5/15
 * @EnableEurekaClient 表明当前模块是eureka的客户端
 */
@SpringBootApplication
@EnableEurekaClient
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
```

#### 2.4 修改配置类`ConsumerConfiguration`
> 之前注入的`RestTemplate`需要加入了一个注解`@LoadBalanced`。这个注解是基于ribbon的一个负载均衡的类，使用这个注解之后向服务端的请求则只能使用服务名，默认
     * 负载均衡的实现算法是轮循，即向这个服务名下所有可用的地址轮流发送请求

```java
package com.ddf.microservicecloud.configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
     * @return
     */
    @Bean
    @LoadBalanced
    public RestTemplate restTemplate() {
        return new RestTemplate();
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
            <groupId>org.springframework</groupId>
            <artifactId>springloaded</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-devtools</artifactId>
        </dependency>
        <!-- 引入Eureka -->
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-eureka</artifactId>
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
````

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























