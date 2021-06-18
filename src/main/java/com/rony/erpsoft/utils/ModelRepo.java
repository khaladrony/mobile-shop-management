/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rony.erpsoft.utils;

import java.util.List;

/**
 *
 * @author juba
 */
public interface ModelRepo<T> {
    public List<T>  findAll();
    public List<T>  findAllByName(String name);

    //public List<T> findAllByLocationName(String location);

    public T  findById(long id);
    public T  findByCode(String code);
    public boolean  save(T model);
    public boolean  update(T model);
    public boolean exist(T model);
}
