package gray.bingo.es.config;

import java.io.Serializable;
import java.util.List;

public class EsResultPaging<T> implements Serializable {

    private Integer totalCount;

    private List<T> result;

    public EsResultPaging(Integer totalCount, List<T> result) {
        this.totalCount = totalCount;
        this.result = result;
    }

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    public List<T> getResult() {
        return result;
    }

    public void setResult(List<T> result) {
        this.result = result;
    }
}