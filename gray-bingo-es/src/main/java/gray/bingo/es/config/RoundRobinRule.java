package gray.bingo.es.config;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 描述：轮询
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
public class RoundRobinRule implements LoadBalancerRule {

    private static AtomicInteger idx = new AtomicInteger(0);

    @Override
    public String getServiceId(List<String> clusterNodes) {
        int index = getNonNegative(idx.incrementAndGet());
        return clusterNodes.get(index % clusterNodes.size());
    }

    /**
     * @return int
     * @Author 高福兵(01409246)
     * @Description 通过二进制位操作将originValue转化为非负数:
     * 0和正数返回本身,负数通过二进制首位取反转化为正数或0（Integer.MIN_VALUE将转换为0
     * @Date 2021/11/12
     * @Param [int]
     **/
    public static int getNonNegative(int originValue) {
        return Integer.MAX_VALUE & originValue;
    }

}