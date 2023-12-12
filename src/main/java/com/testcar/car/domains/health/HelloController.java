package com.testcar.car.domains.health;


import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/** server health check 를 수행하는 컨트롤러 */
@Tag(name = "[Health Check]", description = "서버 상태 체크 API")
@RestController
public class HelloController {
    @GetMapping("/")
    public void hello() {}
}
