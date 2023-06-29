package com.amit.gatewayserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

import java.util.Date;

@SpringBootApplication
public class GatewayserverApplication {

	public static void main(String[] args) {
		SpringApplication.run(GatewayserverApplication.class, args);
	}

	@Bean
	public RouteLocator myRoute(RouteLocatorBuilder builder){
		return builder.routes()
				.route(p->p
					.path("/ambank/accounts/**")
					.filters(f->f.rewritePath("/ambank/accounts/(?<segment>.*)","/${segment}")
						.addRequestHeader("X-Response-Time",new Date().toString()))
					.uri("lb://ACCOUNTS")).
				route(p->p
						.path("/ambank/loans/**")
						.filters(f->f.rewritePath("/ambank/loans/(?<segment>.*)","/${segment}")
								.addRequestHeader("X-Response-Time",new Date().toString()))
						.uri("lb://LOANS")).
				route(p->p
						.path("/ambank/cards/**")
						.filters(f->f.rewritePath("/ambank/cards/(?<segment>.*)","/${segment}")
								.addRequestHeader("X-Response-Time",new Date().toString()))
						.uri("lb://CARDS")).build();
	}

}
