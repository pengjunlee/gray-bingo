package gray.bingo.starter.controller;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.file.Path;
import java.nio.file.Paths;

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
    public Resource json() {
        return new ClassPathResource("static/json.html");
    }



}
