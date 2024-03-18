package gray.bingo.es.config;

/**
 * 描述：负载均衡枚举
 *
 * <pre>
 * HISTORY
 * ****************************************************************************
 *  ID REASON   PERSON     DATE
 *  1 Create   高福兵(01409246)   2021/11/12
 * ****************************************************************************
 * </pre>
 *
 * @author 高福兵(01409246)
 * @version 1.0
 */
public enum LoadBalancerEnum {

    RANDOM("random", "随机", new RandomRule()),

    ROUND_ROBIN("roundRobin", "轮询", new RoundRobinRule()),
    ;

    private final String code;
    private final String name;
    private final LoadBalancerRule rule;

    LoadBalancerEnum(String code, String name, LoadBalancerRule rule) {
        this.code = code;
        this.name = name;
        this.rule = rule;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public LoadBalancerRule getRule() {
        return rule;
    }

    public static LoadBalancerRule getRuleByCode(String code) {
        if (code == null) {
            return ROUND_ROBIN.getRule();
        }
        for (LoadBalancerEnum loadBalancerEnum : LoadBalancerEnum.values()) {
            if (loadBalancerEnum.getCode().equals(code)) {
                return loadBalancerEnum.getRule();
            }
        }
        return ROUND_ROBIN.getRule();
    }

}