# this is a project that records my laerning 
## 项目部署须知
@transactional can rollback <br/>
idea中添加一个mvn命令：“mybatis-generator:generate -e”<br/>
mybatis文件名必须为generatorConfig.xml<br/>
数据库表根据sql自建<br/>
deploy.bat可自动一键部署  其中的tomcat目录和war所在目录请自行设置<br/>
deploy.bat依赖gradlecleanwar.bat<br/>
使用deploy需要设置CATALINA_HOME为tomcat根目录<br/>
##项目如何一步一步搭建
###maven设置
我们以maven项目作为例子<br/>
首先当然是pom.xml<br/>
首先设置文件编码  编译时和文件拷贝时<br/>
```xml
    <properties>
        <!-- 文件拷贝时的编码 -->
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
        <project.reporting.outputEncoding>UTF-8</project.reporting.outputEncoding>
        <!-- 编译时的编码 -->
        <maven.compiler.encoding>UTF-8</maven.compiler.encoding>
    </properties>
```
denpendency不一一多说  复制粘贴即可<br/>
这个下面时配置编译的时候将xml文件拷贝到源文件的目录
```xml
<resources>
            <resource>
                <directory>src/main/resources</directory>
                <includes>
                    <include>**/*.properties</include>
                    <include>**/*.xml</include>
                    <include>**/*.tld</include>
                </includes>
                <filtering>false</filtering>
            </resource>
            <resource>
                <directory>src/main/java</directory>
                <includes>
                    <include>**/*.properties</include>
                    <include>**/*.xml</include>
                    <include>**/*.tld</include>
                </includes>
                <filtering>false</filtering>
            </resource>
        </resources>
        

```
这个是配置编译时候的jdk版本 免去手动设置
```xml
<plugins>
            <plugin>
                <groupId>org.mybatis.generator</groupId>
                <artifactId>mybatis-generator-maven-plugin</artifactId>
                <version>1.3.5</version>
                <configuration>
                    <verbose>true</verbose>
                    <overwrite>true</overwrite>
                </configuration>
            </plugin>

            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-compiler-plugin</artifactId>
                <configuration>
                    <source>1.8</source>
                    <target>1.8</target>
                </configuration>
            </plugin>
        </plugins>
```
###web.xml
web.xml是web.xml 文件提供有关包含 Web 应用程序的 Web 组件的配置和部署信息。<br/>
Java Servlet 规范根据 XML 模式文档来定义 web.xml 部署描述符文件。<br/>
###集成spring
在本项目中 ，我们新建application.xml,并且在其中开启注解（比如@Service等），扫描路径（这个路径下的类会被扫描，用相关注解的会被注册为bean，这个过程是在spring容器启动的时候做的）等等,我们可以通过app类来启动spring（没有和web容器整合）<br/>
spring主要的作用是管理对象生命周期，重点概念为IOC和依赖注入等<br/>
####spring集成mybatis
打开网页[mybatis文档](http://www.mybatis.org/mybatis-3/zh/index.html)
```
DataSource dataSource = BlogDataSourceFactory.getBlogDataSource();
TransactionFactory transactionFactory = new JdbcTransactionFactory();
Environment environment = new Environment("development", transactionFactory, dataSource);
Configuration configuration = new Configuration(environment);
configuration.addMapper(BlogMapper.class);
SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(configuration);
```
通过这段代码可知配置mybatis需要dataSource,transactionFactory,sqlSessionFactory等，spring整合配置如下
```xml
<beans>
<!--定义datasource和transactionManager-->
    <bean id="dataSource" class="org.apache.commons.dbcp.BasicDataSource" destroy-method="close">
        <property name="driverClassName" value="${db.driverClassName}"/>
        <property name="url" value="${db.url}"/>
        <property name="username" value="${db.username}"/>
        <property name="password" value="${db.password}"/>
        <property name="maxActive" value="${connection.maxActive}"></property>
        <property name="maxIdle" value="${connection.maxIdle}"></property>
        <property name="minIdle" value="${connection.minIdle}"></property>
        <property name="maxWait" value="${connection.maxWait}"></property>
    </bean>
    <bean id="sqlSessionFactory" class="org.mybatis.spring.SqlSessionFactoryBean">
        <property name="configLocation" value="classpath:mybatis.xml"/>
        <property name="dataSource" ref="dataSource"/>
        <property name="mapperLocations" value="classpath*:com/chen/dao/mapping/*.xml"/>
    </bean>
    <bean class="org.mybatis.spring.mapper.MapperScannerConfigurer">
        <property name="basePackage" value="com.chen.dao.mapper"/>
        <property name="sqlSessionFactoryBeanName" value="sqlSessionFactory"/>
    </bean>
    <!-- 配置事务管理器 -->
    <bean id="transactionManager" class="org.springframework.jdbc.datasource.DataSourceTransactionManager">
        <property name="dataSource" ref="dataSource"/>
    </bean>
    <tx:annotation-driven transaction-manager="transactionManager" proxy-target-class="true"/>
</beans>
```
mybatis整合完成
##springmvc整合
根据[spring文档](https://docs.spring.io/spring/docs/current/spring-framework-reference/htmlsingle/#mvc)
配置spring-mvc.xml文件，这儿直接把spring-mvc文件复制到自己项目中，知道每一项的意思


