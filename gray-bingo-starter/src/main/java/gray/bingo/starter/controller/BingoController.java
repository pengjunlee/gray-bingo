package gray.bingo.starter.controller;

import gray.bingo.common.Enums.base.BaseIntEnum;
import gray.bingo.common.Enums.base.EnumOption;
import gray.bingo.common.anno.Anonymous;
import gray.bingo.common.entity.R;
import gray.bingo.common.utils.EnumUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Bingo项目通用Rest接口访问控制器
 *
 * @作者 graython
 * @版本 1.0
 * @日期 2024-01-21 16:52
 */
@RestController
@RequestMapping("/bingo")
@RequiredArgsConstructor
public class BingoController {

    @GetMapping("/meta/test")
    public String metaTest() {
        return "{}";
    }

    @GetMapping("/json")
    public Resource json() {
        return new ClassPathResource("static/json.html");
    }


    /**
     * 获取枚举值列表
     *
     * @return
     */
    @GetMapping("/intEnum/{name}")
    @Anonymous
    public R<List<EnumOption<Integer>>> intEnum(@PathVariable(required = true, name = "name") String name) {
        Class<? extends BaseIntEnum> aClass = EnumUtil.getIntMap().get(name);
        return R.ok(BaseIntEnum.toList(aClass));
    }

}
