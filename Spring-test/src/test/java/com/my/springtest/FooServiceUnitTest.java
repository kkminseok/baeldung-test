package com.my.springtest;

import com.my.springtest.dao.FooDAO;
import com.my.springtest.service.FooServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.orm.hibernate5.HibernateTemplate;

import static org.mockito.Mockito.*;

public class FooServiceUnitTest{

//    FooServiceImpl instance;
//
//    private HibernateTemplate hibernateTemplateMock;
//
//    @BeforeEach
//    public void before(){
//        this.instance = new FooServiceImpl(new FooDAO());
//        this.hibernateTemplateMock = mock( HibernateTemplate.class );
//        this.instance.dao.setHibernateTemplate( this.hibernateTemplateMock );
//    }
//
//    @Test
//    public void whenCreateIsTriggered_thenNoException(){
//        // When
//        this.instance.create( new Foo( "testName" ) );
//    }
//
//    @Test( expected = NullPointerException.class )
//    public void whenCreateIsTriggeredForNullEntity_thenException(){
//        // When
//        this.instance.create( null );
//    }
//
//    @Test
//    public void whenCreateIsTriggered_thenEntityIsCreated(){
//        // When
//        Foo entity = new Foo( "testName" );
//        this.instance.create( entity );
//
//        // Then
//        ArgumentCaptor< Foo > argument = ArgumentCaptor.forClass( Foo.class );
//        verify( this.hibernateTemplateMock ).save( argument.capture() );
//        assertThat( entity, is( argument.getValue() ) );
//    }

}
