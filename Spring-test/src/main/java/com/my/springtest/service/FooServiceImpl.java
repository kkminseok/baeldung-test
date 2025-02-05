package com.my.springtest.service;

import com.my.springtest.dao.IFooDAO;
import com.my.springtest.entity.Foo;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class FooServiceImpl implements IFooService{

    private IFooDAO dao;


    @Override
    public Long create(Foo entity) {
        return this.dao.create(entity);
    }
}
