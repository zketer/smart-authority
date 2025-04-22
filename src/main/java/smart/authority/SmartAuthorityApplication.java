package smart.authority;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import smart.authority.web.config.SecurityProperties;

/**
 * @author lynn
 */
@SpringBootApplication
@EnableConfigurationProperties(SecurityProperties.class)
public class SmartAuthorityApplication {

    public static void main(String[] args) {
        SpringApplication.run(SmartAuthorityApplication.class, args);
    }

}
