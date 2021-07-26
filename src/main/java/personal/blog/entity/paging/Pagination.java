package personal.blog.entity.paging;

import java.util.List;


public class Pagination {
    private int totalPages;
    private int number;
    private boolean prevEnabled;
    private boolean hasNext;
    private List<?> list;


    public static Pagination of(int totalPages, int number, List<?> dto) {
        Pagination pagination = new Pagination();
        pagination.setTotalPages(totalPages);
        pagination.setNumber(number);
        pagination.setList(dto);
        pagination.setPrevEnabled(number != 0);
        pagination.setHasNext(number < totalPages - 1);
        return pagination;
    }

    public boolean isHasNext() {
        return hasNext;
    }

    public void setHasNext(boolean hasNext) {
        this.hasNext = hasNext;
    }

    public boolean isPrevEnabled() {
        return prevEnabled;
    }

    public void setPrevEnabled(boolean prevEnabled) {
        this.prevEnabled = prevEnabled;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public List<?> getList() {
        return list;
    }

    public void setList(List<?> list) {
        this.list = list;
    }
}
