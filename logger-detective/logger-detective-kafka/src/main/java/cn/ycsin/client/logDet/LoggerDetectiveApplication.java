package cn.ycsin.client.logDet;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class LoggerDetectiveApplication {

    public static void main(String[] args) {
        SpringApplication.run(LoggerDetectiveApplication.class);
    }
}
