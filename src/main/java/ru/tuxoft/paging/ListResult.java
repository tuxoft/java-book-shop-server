
package ru.tuxoft.paging;

import java.beans.ConstructorProperties;
import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;
import ru.tuxoft.paging.Meta;
import ru.tuxoft.paging.Paging;

@XmlRootElement
public class ListResult<T> {
    private Meta meta = new Meta(-1, new Paging(0, 50));
    private List<T> data;

    public Meta getMeta() {
        return this.meta;
    }

    public List<T> getData() {
        return this.data;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public boolean equals(Object o) {
        if(o == this) {
            return true;
        } else if(!(o instanceof ListResult)) {
            return false;
        } else {
            ListResult other = (ListResult)o;
            if(!other.canEqual(this)) {
                return false;
            } else {
                Meta this$meta = this.getMeta();
                Meta other$meta = other.getMeta();
                if(this$meta == null) {
                    if(other$meta != null) {
                        return false;
                    }
                } else if(!this$meta.equals(other$meta)) {
                    return false;
                }

                List this$data = this.getData();
                List other$data = other.getData();
                if(this$data == null) {
                    if(other$data != null) {
                        return false;
                    }
                } else if(!this$data.equals(other$data)) {
                    return false;
                }

                return true;
            }
        }
    }

    protected boolean canEqual(Object other) {
        return other instanceof ListResult;
    }

    public int hashCode() {
        boolean PRIME = true;
        byte result = 1;
        Meta $meta = this.getMeta();
        int result1 = result * 59 + ($meta == null?43:$meta.hashCode());
        List $data = this.getData();
        result1 = result1 * 59 + ($data == null?43:$data.hashCode());
        return result1;
    }

    public String toString() {
        return "ListResult(meta=" + this.getMeta() + ", data=" + this.getData() + ")";
    }

    public ListResult() {
    }

    @ConstructorProperties({"meta", "data"})
    public ListResult(Meta meta, List<T> data) {
        this.meta = meta;
        this.data = data;
    }
}
