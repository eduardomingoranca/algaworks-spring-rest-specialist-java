package com.algaworks.algafood.core.data.wrapper;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

public class PageWrapper<T> extends PageImpl<T> {
    private final Pageable pageable;

    public PageWrapper(Page<T> page, Pageable pageable) {
        super(page.getContent(), pageable, page.getTotalElements());

        this.pageable = pageable;
    }

    @Override
    public Pageable getPageable() {
        return this.pageable;
    }

}
