package com.example.app.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebSecurity
public class WebMvcConfig implements WebMvcConfigurer {

  @Value("${custom.genFileDirPath}")
  private String genFileDirPath;

  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {
    registry.addResourceHandler("/file/**")  // 리소스와 연결될 URL Path
        .addResourceLocations("file:///" + genFileDirPath + "/");
        // 실제 리소스가 존재하는 경로 (반드시, 마지막은 /로 끝나며, 로컬인 경우 File:///가 앞에 붙어야 한다.)
  }
}
