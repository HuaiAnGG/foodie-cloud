package wiki.laona.cloud.item;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * Created by laona
 **/
@SpringBootApplication
@MapperScan(basePackages = {"wiki.laona.cloud.item.mapper"})
@ComponentScan(basePackages = {"wiki.laona.cloud", "org.n3r.idworker"})
@EnableDiscoveryClient
// TODO 添加 feign 注解
public class ItemApplication {

    public static void main(String[] args) {
        SpringApplication.run(ItemApplication.class, args);
    }
}
