package gray.bingo.starter.controller;

import gray.bingo.common.constants.BingoCst;
import gray.bingo.common.utils.SpringUtil;
import gray.bingo.common.utils.StringUtil;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.Map;

/**
 * Bingo项目Console面板Rest接口访问控制器
 *
 * @作者 二月の菌
 * @版本 1.0
 * @日期 2024-01-21 16:52
 */
@RestController
@RequestMapping("/bingo/console")
public class ConsoleController {


    @PostMapping("/login")
    public Map<String, String> login(@RequestBody Map<String, String> map) {
        String username = map.get("username");
        String password = map.get("password");
        if (StringUtil.isBlank(username) || StringUtil.isBlank(password)) {
            return Collections.singletonMap("message", "用户名/密码不能为空");
        }
        String uname = SpringUtil.get(BingoCst.CONF_CONSOLE_USERNAME);
        String pwd = SpringUtil.get(BingoCst.CONF_CONSOLE_PASSWORD);
        if (StringUtil.isBlank(uname) || StringUtil.isBlank(pwd)) {
            return Collections.singletonMap("message", "面板管理员用户名/密码未配置");
        }
        if (!uname.equals(username) || !pwd.equals(password)) {
            return Collections.singletonMap("message", "用户名/密码不匹配");
        }
        return Collections.singletonMap("username", username);
    }

    @GetMapping("/index")
    public Resource console() {
        return new ClassPathResource("static/index.html");
    }

    private final Path assetPath = Paths.get("src/main/resources/static/assets");

    @GetMapping("/assets/{fileName}")
    public ResponseEntity<Resource> getAssets(@PathVariable(name = "fileName") String fileName) {
        try {

            Resource resource = new ClassPathResource("static/assets/" + fileName);
            if (resource.exists() || resource.isReadable()) {
                MediaType contentType = MediaType.APPLICATION_OCTET_STREAM;
                if (fileName.endsWith(".css")) {
                    contentType = new MediaType("text", "css");
                } else if (fileName.endsWith(".js")) {
                    contentType = new MediaType("application", "javascript");
                }
                return ResponseEntity.ok()
                        .contentType(contentType)
                        .body(resource);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
