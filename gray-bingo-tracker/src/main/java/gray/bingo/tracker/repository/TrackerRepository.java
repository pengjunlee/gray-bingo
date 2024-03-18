package gray.bingo.tracker.repository;


import gray.bingo.tracker.common.SpanNode;

import java.util.List;

/**
 * tracker span 信息持久化接口
 * <p>tracker 客户端和服务端的实现不同
 * <p><h3>客户端的实现目前有</h3>
 * <ol>
 * <li>disk: 保存在本地日志中
 * </ol>
 *
 * @作者 二月の菌
 * @版本 1.0
 * @日期 2024-01-21 14:17
 */
public interface TrackerRepository {

    /**
     * 保存 span 集合,
     *
     * @param spanNodes span 集合
     * @return true: 保存成功; false:保存失败
     */
    boolean save(List<SpanNode> spanNodes);

    /**
     * span 信息到期
     *
     * @return 删除的条数
     */
    default long expire() {
        return 0;
    }
}
