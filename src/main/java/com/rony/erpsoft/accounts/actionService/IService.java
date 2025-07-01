package com.rony.erpsoft.accounts.actionService;

import com.rony.erpsoft.configuration.AppResponse;

public interface IService <E> {
    AppResponse execute(E entity);
}
