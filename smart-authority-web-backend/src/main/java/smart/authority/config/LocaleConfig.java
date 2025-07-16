package smart.authority.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;

import java.util.Arrays;
import java.util.Locale;

@Configuration
public class LocaleConfig {

    @Bean
    public LocaleResolver localeResolver() {
        AcceptHeaderLocaleResolver localeResolver = new AcceptHeaderLocaleResolver();
        localeResolver.setDefaultLocale(Locale.SIMPLIFIED_CHINESE); // 默认语言为中文
        localeResolver.setSupportedLocales(Arrays.asList(
            Locale.SIMPLIFIED_CHINESE,
            Locale.ENGLISH,
            Locale.JAPANESE,
            Locale.KOREAN
        ));
        return localeResolver;
    }

    @Bean
    public ResourceBundleMessageSource messageSource() {
        ResourceBundleMessageSource source = new ResourceBundleMessageSource();
        source.setBasenames("i18n/messages"); // 指定国际化文件路径
        source.setDefaultEncoding("UTF-8"); // 设置默认编码
        source.setUseCodeAsDefaultMessage(true); // 当找不到对应消息时使用消息编码作为默认消息
        return source;
    }
}