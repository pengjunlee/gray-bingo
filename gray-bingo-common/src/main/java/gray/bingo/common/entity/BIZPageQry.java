package gray.bingo.common.entity;

import java.io.Serializable;

/**
 * 分页查询请求基本参数
 */
public class BIZPageQry implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer pageNo = 1;

    private Integer pageSize = 10;

    private Integer fromRowNum;

    public Integer getFromRowNum() {
        if (pageNo == null) {
            pageNo = 1;
        }
        if (pageSize == null) {
            pageSize = 10;
        }
        // pageNumber 默认1开始
        return (pageNo - 1) * pageSize;
    }

    public Integer getPageNo() {
        if (null == pageNo || pageNo <= 0) return 1;
        return pageNo;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Long getPageStart() {
        return (getPageNo() - 1) * pageSize.longValue();
    }
}
