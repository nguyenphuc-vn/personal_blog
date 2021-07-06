package personal.blog.entity.paging;

import personal.blog.dto.ArticleDto;

import java.util.List;


public class Pagination {
    private int totalPages;
    private int number;
    private boolean prevEnabled;
    private boolean hasNext;
    private List<ArticleDto> articles;


    public static Pagination of(int totalPages, int number, List<ArticleDto> dto) {
        Pagination pagination = new Pagination();
        pagination.setTotalPages(totalPages);
        pagination.setNumber(number);
        pagination.setArticles(dto);
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

    public List<ArticleDto> getArticles() {
        return articles;
    }

    public void setArticles(List<ArticleDto> articles) {
        this.articles = articles;
    }
}
