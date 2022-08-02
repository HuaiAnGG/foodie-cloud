package wiki.laona.cloud.order;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * Created by laona
 **/
@SpringBootApplication
@MapperScan(basePackages = {"wiki.laona.cloud.order.mapper"})
@ComponentScan(basePackages = {"wiki.laona.cloud", "org.n3r.idworker"})
@EnableDiscoveryClient
@EnableScheduling
// TODO 添加 feign 注解
public class OrderApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrderApplication.class, args);
    }
}
