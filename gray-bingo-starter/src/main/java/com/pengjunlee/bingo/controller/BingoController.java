package com.pengjunlee.bingo.controller;

import com.pengjunlee.bingo.config.BingoMeta;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/bingo")
public class BingoController {

    @GetMapping("/meta")
    public String meta(){
        return null;
    }
}
