package gray.bingo.es.config;

import java.util.List;

public class EsDslResult<T> {
    private Integer took;
    private Boolean timed_out;
    private Shard _shards;
    private Hits<T> hits;

    public EsDslResult() {
    }

    public Integer getTook() {
        return took;
    }

    public void setTook(Integer took) {
        this.took = took;
    }

    public Boolean getTimed_out() {
        return timed_out;
    }

    public void setTimed_out(Boolean timed_out) {
        this.timed_out = timed_out;
    }

    public Shard get_shards() {
        return _shards;
    }

    public void set_shards(Shard _shards) {
        this._shards = _shards;
    }

    public Hits<T> getHits() {
        return hits;
    }

    public void setHits(Hits<T> hits) {
        this.hits = hits;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("EsDslResult{");
        sb.append("took=").append(took);
        sb.append(", timed_out=").append(timed_out);
        sb.append(", _shards=").append(_shards);
        sb.append(", hits=").append(hits);
        sb.append('}');
        return sb.toString();
    }

    public static class Shard{

        public Shard() {
        }

        private Integer total;
        private Integer successful;
        private Integer failed;

        public Integer getTotal() {
            return total;
        }

        public void setTotal(Integer total) {
            this.total = total;
        }

        public Integer getSuccessful() {
            return successful;
        }

        public void setSuccessful(Integer successful) {
            this.successful = successful;
        }

        public Integer getFailed() {
            return failed;
        }

        public void setFailed(Integer failed) {
            this.failed = failed;
        }

        @Override
        public String toString() {
            final StringBuffer sb = new StringBuffer("Shard{");
            sb.append("total=").append(total);
            sb.append(", successful=").append(successful);
            sb.append(", failed=").append(failed);
            sb.append('}');
            return sb.toString();
        }
    }

    public static class EsTotal{

        private Integer value;
        private String relation;

        public Integer getValue() {
            return value;
        }

        public void setValue(Integer value) {
            this.value = value;
        }

        public String getRelation() {
            return relation;
        }

        public void setRelation(String relation) {
            this.relation = relation;
        }
    }

    public static class Hits<T> {

        public Hits() {
        }

        private EsTotal total;
        private Integer max_score;
        private List<EsGetResult<T>> hits;

        public Integer getTotal() {
            return total.getValue();
        }

        public void setTotal(EsTotal total) {
            this.total = total;
        }

        public Integer getMax_score() {
            return max_score;
        }

        public void setMax_score(Integer max_score) {
            this.max_score = max_score;
        }

        public List<EsGetResult<T>> getHits() {
            return hits;
        }

        public void setHits(List<EsGetResult<T>> hits) {
            this.hits = hits;
        }

        @Override
        public String toString() {
            final StringBuffer sb = new StringBuffer("Hits{");
            sb.append("total=").append(total);
            sb.append(", max_score=").append(max_score);
            sb.append(", hits=").append(hits);
            sb.append('}');
            return sb.toString();
        }
    }
}