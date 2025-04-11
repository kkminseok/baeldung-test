package com.my.springbootkafkaproducer.mapper;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.my.springbootkafkaproducer.model.Foo2;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.header.Headers;
import org.springframework.kafka.support.mapping.DefaultJackson2JavaTypeMapper;

@Slf4j
public class DynamicMapper extends DefaultJackson2JavaTypeMapper {

    @Override
    public JavaType toJavaType(Headers headers){

        String typeIdHeader = this.retrieveHeaderAsString(headers, this.getClassIdFieldName());
        log.info("init : {}", typeIdHeader);
        log.info("headers: {}", headers.toString());
        log.info("foo class name: {}", Foo2.class.getName());
        if (typeIdHeader != null) {
            try {
                String className = typeIdHeader;
                Class<?> clazz = Class.forName(className);
                log.info("clazz: {]", clazz.toString());
                return TypeFactory.defaultInstance().constructType(clazz);
            } catch (ClassNotFoundException e) {
                throw new IllegalArgumentException("Cannot find class for type id: " + typeIdHeader, e);
            }
        }
        return super.toJavaType(headers);
    }
}
