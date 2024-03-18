package gray.bingo.es.config;

import java.security.SecureRandom;
import java.util.List;

/**
 * 描述：随机
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
public class RandomRule implements LoadBalancerRule {


    @Override
    public String getServiceId(List<String> clusterNodes) {
        SecureRandom rand = new SecureRandom();
        int index = rand.nextInt(clusterNodes.size());
        return clusterNodes.get(index);
    }
}