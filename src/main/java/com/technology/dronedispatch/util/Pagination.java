package com.technology.dronedispatch.util;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class Pagination {

    private Pagination(){}
    public static Pageable pageable(int page, int pageRecord) {
        return PageRequest.of(page, pageRecord, Sort.Direction.DESC, "id");
    }
}
