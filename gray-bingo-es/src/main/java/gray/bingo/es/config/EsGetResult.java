package gray.bingo.es.config;

import java.util.List;
import java.util.Map;

public class EsGetResult<T> {
    private String _index;
    private String _type;
    private String _id;
    private String version;
    private boolean found;

    private T _source;

    private Map<String, List<String>> highlight;

    public String get_index() {
        return _index;
    }

    public void set_index(String _index) {
        this._index = _index;
    }

    public String get_type() {
        return _type;
    }

    public void set_type(String _type) {
        this._type = _type;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public boolean isFound() {
        return found;
    }

    public void setFound(boolean found) {
        this.found = found;
    }

    public T get_source() {
        return _source;
    }

    public void set_source(T _source) {
        this._source = _source;
    }


    public Map<String, List<String>> getHighlight() {
        return highlight;
    }

    public void setHighlight(Map<String, List<String>> highlight) {
        this.highlight = highlight;
    }

    @Override
    public String toString() {
        return "EsGetResult{" +
                "_index='" + _index + '\'' +
                ", _type='" + _type + '\'' +
                ", _id='" + _id + '\'' +
                ", version='" + version + '\'' +
                ", found=" + found +
                ", _source=" + _source +
                '}';
    }
}