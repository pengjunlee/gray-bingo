package gray.bingo.starter.config;

import org.springframework.boot.Banner;
import org.springframework.core.env.Environment;

import java.io.PrintStream;
import java.util.LinkedList;
import java.util.List;

/**
 * 自定义启动Banner
 *
 * @作者 二月の菌
 * @版本 1.0
 * @日期 2024-01-21 14:17
 */
public class BingoBanner implements Banner {

    @Override
    public void printBanner(Environment environment, Class<?> sourceClass, PrintStream out) {
        List<String> bannerList = new LinkedList<>();
        bannerList.add("=========================================================================");
        bannerList.add("                                                                       ");
        bannerList.add("        _____                     ____  _                   ");
        bannerList.add("       / ____|                   |  _ \\(_)                  ");
        bannerList.add("      | |  __ _ __ __ _ _   _    | |_) |_ _ __   __ _  ___  ");
        bannerList.add("      | | |_ | '__/ _` | | | |   |  _ <| | '_ \\ / _` |/ _ \\ ");
        bannerList.add("      | |__| | | | (_| | |_| |   | |_) | | | | | (_| | (_) |");
        bannerList.add("       \\_____|_|  \\__,_|\\__, |   |____/|_|_| |_|\\__, |\\___/ ");
        bannerList.add("                         __/ |_____              __/ |      ");
        bannerList.add("                        |___/______|            |___/       ");
        bannerList.add("                                                                       ");
        bannerList.add("          Bingo Helps You Build Your Project more fast!");
        bannerList.add("=========================================================================");
        for (String line : bannerList) {
            out.println(line);
        }
    }
}
