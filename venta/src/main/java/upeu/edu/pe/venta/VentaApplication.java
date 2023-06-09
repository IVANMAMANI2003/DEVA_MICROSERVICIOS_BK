package upeu.edu.pe.venta;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
//@EnableHystrix
//@EnableHystrixDashboard
public class VentaApplication {

	public static void main(String[] args) {
		SpringApplication.run(VentaApplication.class, args);
	}

}
