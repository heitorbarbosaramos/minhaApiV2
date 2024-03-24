package com.heitor.minhaApi;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.core.env.Environment;

import java.time.LocalDateTime;
import java.util.TimeZone;

@SpringBootApplication
@Slf4j
@RequiredArgsConstructor
@EnableFeignClients
public class MinhaApiApplication extends SpringBootServletInitializer implements InitializingBean {

	private final AppUtil appUtil;
	private final Environment environment;

	@Value("${config.timezone.zone}")
	private String timeZone;

	@PostConstruct
	public void init(){

		TimeZone.setDefault(TimeZone.getTimeZone(timeZone));
		log.info("TIME ZONE {} - {}", TimeZone.getDefault(), LocalDateTime.now());
		log.info("DATA HORA: {}", LocalDateTime.now());
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(MinhaApiApplication.class);
	}

	public static void main(String[] args) {
		AppUtil.startup(args, MinhaApiApplication.class);
	}

	@Override
	public void afterPropertiesSet() {
		AppUtil.checkProfiles(environment, log);
	}

}
