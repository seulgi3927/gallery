package kr.co.wikibook.gallery.common;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Slf4j
@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {

    private final String uploadPath;
    public WebMvcConfiguration(@Value("${constants.file.directory}") String uploadPath) {
        this.uploadPath = uploadPath;
        log.info("uploadPath:{}", uploadPath);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/pic/**") // 백엔드에서 pic 으로 시작하는 요청이 온다면~ (컨트롤러에 있는 주소랑 겹치면 안됨)
                .addResourceLocations("file:" + uploadPath);

    }


    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("*");
    }

}
