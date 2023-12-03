package com.testcar.car.domains.health;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/** server health check 를 수행하는 컨트롤러 */
@RestController
public class HelloController {
    @GetMapping("/")
    public void hello() {}
}
