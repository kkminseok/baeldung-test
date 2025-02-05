package com.my.springtest.dao;

import com.google.common.base.Preconditions;
import com.my.springtest.entity.Foo;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

@Repository
public class FooDAO extends HibernateDaoSupport implements IFooDAO{

    public Long create( Foo entity ){
        Preconditions.checkNotNull( entity );

        return (Long) this.getHibernateTemplate().save( entity );
    }

}