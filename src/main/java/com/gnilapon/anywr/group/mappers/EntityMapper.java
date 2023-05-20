package com.gnilapon.anywr.group.mappers;

/**
 *
 * @param <T>
 * @param <R>
 */
public interface EntityMapper <T,R>{
    T dtoToEntity(R dto);
    R entityToDto(T entity);
}
