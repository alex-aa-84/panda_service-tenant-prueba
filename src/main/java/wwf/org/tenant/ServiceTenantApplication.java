package wwf.org.tenant;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
//@EnableEurekaClient
public class ServiceTenantApplication {
	public static void main(String[] args) {
		SpringApplication.run(ServiceTenantApplication.class, args);
	}

}
