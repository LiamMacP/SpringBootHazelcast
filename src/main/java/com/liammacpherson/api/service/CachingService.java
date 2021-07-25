package com.liammacpherson.api.service;

import com.liammacpherson.api.model.SerializableEntity;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Locale;

@SuppressWarnings("unused")
public abstract class CachingService<T extends SerializableEntity> {

    private final String genericClassName;

    public CachingService()
    {
        ParameterizedType cachingServiceWithType
                = (ParameterizedType) this.getClass().getGenericSuperclass();

        Type genericType = cachingServiceWithType.getActualTypeArguments()[0];

        genericClassName = ((Class<?>)genericType).getSimpleName().toUpperCase(Locale.ROOT);
    }

    public String getAllCacheName() {
        return "ALL_" + genericClassName;
    }
}
