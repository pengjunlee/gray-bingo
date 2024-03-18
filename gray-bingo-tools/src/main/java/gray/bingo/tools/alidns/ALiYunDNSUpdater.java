package gray.bingo.tools.alidns;

import cn.hutool.core.util.StrUtil;
import gray.bingo.common.config.BingoProp;
import gray.bingo.common.constants.BingoCst;
import org.springframework.util.CollectionUtils;

import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author 二月の菌
 * @version 1.0
 * @date 2023-08-12 19:13
 */
public class ALiYunDNSUpdater {

    /**
     * IPv4地址获取链接
     */
    public static final String getIPv4Url = "http://ifconfig.me/ip";

    public static final String getIPv6Url = "https://v6.ip.zxinc.org/getip";

    /**
     * 异步操作任务调度线程池
     */
    private final static ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);


    public static void registerDNSUpdater() {
        // 读取配置文件
        Map<String, String> dnsUpdateMap = BingoProp.getMap(BingoCst.CONF_ALIDNS_PREFIX);
        if (CollectionUtils.isEmpty(dnsUpdateMap)) return;
        String accessKey = dnsUpdateMap.get("accessKey");
        String secretKey = dnsUpdateMap.get("secretKey");
        String domain = dnsUpdateMap.get("domain");
        String records = dnsUpdateMap.get("records");
        if (StrUtil.isBlank(accessKey) || StrUtil.isBlank(secretKey) || StrUtil.isBlank(domain) || StrUtil.isBlank(records)) {
            return;
        }

        String ipv6 = dnsUpdateMap.get("ipv6");
        boolean useIPv6 = StrUtil.isNotBlank(ipv6) && ipv6.equals("open");

        String[] domainRecordsArr = records.split(",");
        for (String record : domainRecordsArr) {
            scheduler.scheduleWithFixedDelay(new ALiYunDNSTask(domain, record, useIPv6, accessKey, secretKey), 0, 1, TimeUnit.HOURS);
        }
    }


}
