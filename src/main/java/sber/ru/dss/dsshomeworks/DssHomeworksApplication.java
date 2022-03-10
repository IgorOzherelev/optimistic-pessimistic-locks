package sber.ru.dss.dsshomeworks;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.retry.annotation.EnableRetry;

@SpringBootApplication
@EnableRetry
public class DssHomeworksApplication {

    public static void main(String[] args) {
        SpringApplication.run(DssHomeworksApplication.class, args);
    }

}
