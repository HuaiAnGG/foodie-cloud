package wiki.laona.cloud.cart;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * Created by laona
 **/
@SpringBootApplication
@MapperScan(basePackages = {"wiki.laona.cloud.cart.mapper"})
@ComponentScan(basePackages = {"wiki.laona.cloud", "org.n3r.idworker"})
@EnableDiscoveryClient
// TODO 添加 feign 注解
public class CartApplication {

    public static void main(String[] args) {
        SpringApplication.run(CartApplication.class, args);
    }
}
