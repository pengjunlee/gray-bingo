package gray.bingo.es.config;

import java.util.List;

/**
 * 描述：负载均衡
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
public interface LoadBalancerRule {


    /**
     * @return com.sf.ground.sapper.es.util.LoadBalancerRule
     * @Author 高福兵(01409246)
     * @Description 获取负载规则
     * @Date 2021/11/12
     * @Param [java.lang.String]
     */
    static LoadBalancerRule getRule(String code) {
        return LoadBalancerEnum.getRuleByCode(code);
    }


    /**
     * @return java.lang.String
     * @Author 高福兵(01409246)
     * @Description 获取ID
     * @Date 2021/11/12
     * @Param [java.util.List<java.lang.String>]
     **/
    String getServiceId(List<String> clusterNodes);
}