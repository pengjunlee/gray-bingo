package gray.bingo.common.entity;

import java.io.Serializable;

/**
 * 分页查询请求基本参数
 */

public class BingoPageQry implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer pageNo;

    private Integer pageSize;

    private Integer fromRowNum;

    public Integer getFromRowNum() {
        // pageNumber 默认1开始
        return (getPageNo() - 1) * getPageSize();
    }

    public Integer getPageNo() {
        if (null == pageNo || pageNo <= 0) return 1;
        return pageNo;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

    public Integer getPageSize() {
        return null != pageSize && pageSize > 0 ? pageSize : 10;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Long getPageStart() {
        return (getPageNo() - 1) * pageSize.longValue();
    }
}
