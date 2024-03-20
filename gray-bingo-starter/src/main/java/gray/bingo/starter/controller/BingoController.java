package gray.bingo.starter.controller;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Bingo项目通用Rest接口访问控制器
 *
 * @作者 二月の菌
 * @版本 1.0
 * @日期 2024-01-21 16:52
 */
@RestController
@RequestMapping("/bingo")
public class BingoController {

    @GetMapping("/meta/test")
    public String metaTest() {
        return "{}";
    }


    @GetMapping("/json")
    public Resource json(@RequestParam(name = "name", required = true) String name) {
        return new ClassPathResource("static/json.html");
    }
}
