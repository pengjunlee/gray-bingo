package gray.bingo.common.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * 列表查询结果统一响应
 *
 * @param <T>
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BIZPageRsp<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    private List<T> result;

    private Long pageNo;

    private Long total;


    public BIZPageRsp<T> pageNo(Long pageNo) {
        this.pageNo = pageNo;
        return this;
    }

    public BIZPageRsp<T> total(Long count) {
        this.total = count;
        return this;
    }

    public BIZPageRsp<T> result(List<T> result) {
        this.result = result;
        return this;
    }

}
