package ru.tuxoft.paging;

import java.beans.ConstructorProperties;

public class Paging {
    int start = 0;
    int pageSize = 100;

    public int getStart() {
        return this.start;
    }

    public int getPageSize() {
        return this.pageSize;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public boolean equals(Object o) {
        if(o == this) {
            return true;
        } else if(!(o instanceof Paging)) {
            return false;
        } else {
            Paging other = (Paging)o;
            return !other.canEqual(this)?false:(this.getStart() != other.getStart()?false:this.getPageSize() == other.getPageSize());
        }
    }

    protected boolean canEqual(Object other) {
        return other instanceof Paging;
    }

    public int hashCode() {
        boolean PRIME = true;
        byte result = 1;
        int result1 = result * 59 + this.getStart();
        result1 = result1 * 59 + this.getPageSize();
        return result1;
    }

    public String toString() {
        return "Paging(start=" + this.getStart() + ", pageSize=" + this.getPageSize() + ")";
    }

    @ConstructorProperties({"start", "pageSize"})
    public Paging(int start, int pageSize) {
        this.start = start;
        this.pageSize = pageSize;
    }

    public Paging() {
    }
}
