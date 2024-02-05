package com.mintos.task.repository.pageable;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.io.Serializable;
import java.util.Objects;

public class OffsetLimitPageable implements Pageable, Serializable {

    private final int offset;
    private final int limit;
    private final Sort sort;

    public OffsetLimitPageable(int offset, int limit, Sort sort) {
        if (offset < 0) {
            throw new IllegalArgumentException("Offset index must not be less than zero!");
        }

        if (limit < 1) {
            throw new IllegalArgumentException("Limit must not be less than one!");
        }
        this.limit = limit;
        this.offset = offset;
        this.sort = sort;
    }

    public OffsetLimitPageable(int offset, int limit) {
        this(offset, limit, Sort.unsorted());
    }

    @Override
    public int getPageNumber() {
        return offset / limit;
    }

    @Override
    public int getPageSize() {
        return limit;
    }

    @Override
    public long getOffset() {
        return offset;
    }

    @Override
    public Sort getSort() {
        return sort;
    }

    @Override
    public Pageable next() {
        return new OffsetLimitPageable(Integer.parseInt(String.valueOf(getOffset())) + getPageSize(), getPageSize(), getSort());
    }

    @Override
    public Pageable previousOrFirst() {
        return hasPrevious() ? previous() : first();
    }

    @Override
    public Pageable first() {
        return new OffsetLimitPageable(0, getPageSize(), getSort());
    }

    @Override
    public Pageable withPage(int pageNumber) {
        return null;
    }

    @Override
    public boolean hasPrevious() {
        return offset > limit;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;

        if (!(o instanceof OffsetLimitPageable that))
            return false;

        return limit == that.limit
                && offset == that.offset
                && sort.equals(that.sort);
    }

    @Override
    public int hashCode() {
        return Objects.hash(limit, offset, sort);
    }

    public OffsetLimitPageable previous() {
        return hasPrevious() ? new OffsetLimitPageable(Integer.parseInt(String.valueOf(getOffset())), getPageSize(), getSort()) : this;
    }
}
