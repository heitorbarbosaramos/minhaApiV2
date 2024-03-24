package com.heitor.minhaApi;

import io.micrometer.core.instrument.util.StringUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.Collection;

@Slf4j
@RequiredArgsConstructor
@Service
public class AppUtil {

    public static void startup(String[] args, Class clazz) {
        SpringApplication app = new SpringApplication(new Class[]{clazz});
//        DefaultProfileUtil.addDefaultProfile(app);
        Environment env = app.run(args).getEnvironment();
        logApplicationStartup(env, log);
    }

    public static void logApplicationStartup(Environment env, Logger log) {
        String protocol = "http";
        if (env.getProperty("server.ssl.key-store") != null) {
            protocol = "https";
        }

        String serverPort = env.getProperty("server.port");
        String contextPath = env.getProperty("server.servlet.context-path");
        if (StringUtils.isBlank(contextPath)) {
            contextPath = "/";
        }

        String hostAddress = "localhost";

        try {
            hostAddress = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException var7) {
            log.warn("The host name could not be determined, using `localhost` as fallback", var7);
        }

        log.info("\n----------------------------------------------------------\n\tApplication '{}' is running! Access URLs:\n\tLocal: \t\t{}://localhost:{}{}\n\tExternal: \t{}://{}:{}{}\n\tSystem: \t{}\n\tSwagger: \t{}://localhost:{}{}/swagger-ui/index.html\n\tProfile(s): \t{}\n----------------------------------------------------------", new Object[]{env.getProperty("spring.application.name"), protocol, serverPort, contextPath, protocol, hostAddress, serverPort, contextPath,env.getProperty("bucket.front.url"), protocol, serverPort, contextPath, env.getActiveProfiles()});
        String configServerStatus = env.getProperty("configserver.status");
        if (configServerStatus == null) {
            configServerStatus = "Not found or not setup for this application";
        }

        log.info("\n----------------------------------------------------------\n\tConfig Server: \t{}\n----------------------------------------------------------", configServerStatus);
    }

    public static void checkProfiles(Environment env, Logger log) {
        Collection<String> activeProfiles = Arrays.asList(env.getActiveProfiles());
        if (activeProfiles.contains("dev") && activeProfiles.contains("prod")) {
            log.error("You have misconfigured your application! It should not run with both the 'dev' and 'prod' profiles at the same time.");
        }

        if (activeProfiles.contains("dev") && activeProfiles.contains("cloud")) {
            log.error("You have misconfigured your application! It should not run with both the 'dev' and 'cloud' profiles at the same time.");
        }

    }
}
