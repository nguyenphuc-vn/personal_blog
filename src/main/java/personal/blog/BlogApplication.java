package personal.blog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;
import java.util.TimeZone;

@SpringBootApplication
public class BlogApplication {

    @PostConstruct
    public void init(){
        // Setting Spring Boot SetTimeZone
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Bangkok"));
        System.err.println(TimeZone.getDefault().getDisplayName());
    }

    public static void main(String[] args) {
        SpringApplication.run(BlogApplication.class, args);
    }


}
