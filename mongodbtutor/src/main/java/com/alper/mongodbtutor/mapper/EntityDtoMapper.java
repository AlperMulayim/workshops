package com.alper.mongodbtutor.mapper;

public interface EntityDtoMapper <E,D>{
    E toEntity(D d);
    D toDto(E e);
}
