package com.android.app.onetwoone.data.mapper

interface BaseMapperRepository<E, D> {

    fun transform(type: E): D

    fun transformToRepository(type: D): E

}
