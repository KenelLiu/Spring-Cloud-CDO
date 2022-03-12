微服务的注册与发现:
	Nacos	
	Consul
	Zookeeper
	Eureka
微服务调用:
	OpenFeign ：Spring Cloud在Feign的基础上，支持了SpringMvc的注解
　	Ribbon : 一个客户端负载均衡的工具，由Netflix公司开发，很不幸，已经进入了维护状态，不再进行新的功能开发。	
	Feign ：内集成了Ribbon，可以通过注解，像本地调用似的跨域调用微服务接口
			
服务的限流、降级、熔断:
	sentinel : 阿里开发的开源框架，提供可视化界面，多语言支持的服务熔断降级限流框架 
　	Hystrix : 由Netflix公司开发的一个进行服务熔断、降级的框架，但是很不幸，进入维护状态了　　　　　
微服务网关框架：
	Spring Cloud GateWay ：Spring Cloud开发，在SpringMvc上构建的API网关框架。
	Zuul : 由Netflix公司开发，已经进入维护状态了。
	Zuul2 : 还在开发过程中，多次跳票。	
配置统一管理
	Spring Cloud Config : Spring开发的配置服务，可以从github、支持JDBC的数据库或者其他文件系统中拉取配置。
　　　　　Nacos ：nacos除了有注册中心的功能之外，还可以提供配置管理服务。
分布式事务：
           Seata

thymeleaf
<dependencies>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-thymeleaf</artifactId> 
    </dependency>
    <dependency>
        <groupId>org.projectlombok</groupId>
        <artifactId>lombok</artifactId>
    </dependency>
</dependencies>