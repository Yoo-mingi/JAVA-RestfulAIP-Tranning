package kr.co.yoomin.myrestfulservice.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@OpenAPIDefinition(
        info = @Info(title="My Restful Service API",
                description = "Spring Boot로 개발하는 Restful API 명세서",
                version = "v1.0.0")
)
@Configuration
@RequiredArgsConstructor
public class NewSwaggerConfig {
    @Bean
    public GroupedOpenApi custormTestOpenAPI(){
        String[] paths = {"/users/**","/admin/**"};

        return GroupedOpenApi.builder()
                .group("일반 사용자와 관리자는 위한 user 도메인데 대한 API")
                .pathsToMatch(paths)
                .build();
    }
}
