package com.github.tomekmazurek.petstore.service;

import java.util.List;

public interface BaseService<T> {

    List<T> getAll();

    T add(T entity);

}
