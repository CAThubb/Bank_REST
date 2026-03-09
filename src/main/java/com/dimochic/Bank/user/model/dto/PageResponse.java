package com.dimochic.Bank.user.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.data.domain.Page;

import java.util.List;

@Schema(description = "Spring response wrapper for paginated data")
public class PageResponse<T> {

    @Schema(description = "List of items on the current page")
    private List<T> content;

    @Schema(description = "Current page number", example = "0")
    private int pageNum;

    @Schema(description = "Number of items per page", example = "10")
    private int pageSize;

    @Schema(description = "Total number of items", example = "100")
    private long totalElements;

    @Schema(description = "Total number of pages", example = "15")
    private int totalPages;

    @Schema(description = "Indicates if this is the last page", example = "false")
    private boolean lastPage;

    public PageResponse() {
    }

    public PageResponse(Page<T> page) {
        this.content = page.getContent();
        this.pageNum = page.getNumber();
        this.pageSize = page.getSize();
        this.totalElements = page.getTotalElements();
        this.totalPages = page.getTotalPages();
        this.lastPage = page.isLast();
    }

    public PageResponse(List<T> content, int pageNum, int pageSize, long totalElements, int totalPages, boolean lastPage) {
        this.content = content;
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        this.totalElements = totalElements;
        this.totalPages = totalPages;
        this.lastPage = lastPage;
    }

    public List<T> getContent() {
        return content;
    }

    public int getPageNum() {
        return pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public long getTotalElements() {
        return totalElements;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public boolean isLastPage() {
        return lastPage;
    }

    public void setContent(List<T> content) {
        this.content = content;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public void setTotalElements(long totalElements) {
        this.totalElements = totalElements;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public void setLastPage(boolean lastPage) {
        this.lastPage = lastPage;
    }
}
