package com.rony.erpsoft.utils;


import java.util.List;

/**
 * A generic base mapper interface that defines methods for mapping between
 * entities and DTOs.
 *
 * @param <D> the DTO type
 * @param <E> the Entity type
 */
public interface BaseMapper<D, E> {


    /**
     * Maps an entity to a DTO.
     *
     * @param entity the entity to map
     * @return the mapped DTO
     */
    D entityToDto(E entity);

    /**
     * Maps a DTO to an entity.
     *
     * @param dto the DTO to map
     * @return the mapped entity
     */
    E dtoToEntity(D dto);

    /**
     * Maps a list of entities to a list of DTOs.
     *
     * @param entities the list of entities to map
     * @return the list of mapped DTOs
     */
    List<D> entitiesToDtos(List<E> entities);

    /**
     * Maps a list of DTOs to a list of entities.
     *
     * @param dtos the list of DTOs to map
     * @return the list of mapped entities
     */
    List<E> dtosToEntities(List<D> dtos);
}
