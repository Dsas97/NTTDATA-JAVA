package com.nttadta.movement_service.utils;


import lombok.Data;

import java.util.List;

/**
 * Class for list pagination
 * @param <T> List object type
 */

@Data
public class Pagination<T> {
    private List<T> list;
    private Integer page;
    private Integer limit;
    private Integer totalRecords;
    private Integer totlaPages;


    /**
     * Class constructor
     * @param list List of objects
     * @param page Page number
     * @param limit Number of records per page
     * @param totalRecords Total number of records
     */
    public Pagination(List<T> list, Integer page, Integer limit, Integer totalRecords) {
        this.list = list;
        this.page = page;
        this.limit = limit;
        this.totalRecords = totalRecords;
        this.totlaPages = (int) Math.ceil((double) totalRecords / limit);
    }
}
