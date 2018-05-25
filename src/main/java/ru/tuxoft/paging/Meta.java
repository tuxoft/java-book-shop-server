package ru.tuxoft.paging;

import java.beans.ConstructorProperties;
import ru.tuxoft.paging.Paging;

public class Meta {
    int total;
    Paging paging;

    public int getTotal() {
        return this.total;
    }

    public Paging getPaging() {
        return this.paging;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public void setPaging(Paging paging) {
        this.paging = paging;
    }

    public boolean equals(Object o) {
        if(o == this) {
            return true;
        } else if(!(o instanceof Meta)) {
            return false;
        } else {
            Meta other = (Meta)o;
            if(!other.canEqual(this)) {
                return false;
            } else if(this.getTotal() != other.getTotal()) {
                return false;
            } else {
                Paging this$paging = this.getPaging();
                Paging other$paging = other.getPaging();
                if(this$paging == null) {
                    if(other$paging != null) {
                        return false;
                    }
                } else if(!this$paging.equals(other$paging)) {
                    return false;
                }

                return true;
            }
        }
    }

    protected boolean canEqual(Object other) {
        return other instanceof Meta;
    }

    public int hashCode() {
        boolean PRIME = true;
        byte result = 1;
        int result1 = result * 59 + this.getTotal();
        Paging $paging = this.getPaging();
        result1 = result1 * 59 + ($paging == null?43:$paging.hashCode());
        return result1;
    }

    public String toString() {
        return "Meta(total=" + this.getTotal() + ", paging=" + this.getPaging() + ")";
    }

    @ConstructorProperties({"total", "paging"})
    public Meta(int total, Paging paging) {
        this.total = total;
        this.paging = paging;
    }

    public Meta() {
    }
}
