package com.example.news.resource

class Resource<T> (
    val value: T? = null,
    val t: Throwable? = null,
    val state: State,
    val size: Int = 0
        ){
    companion object{
        enum class State{
            LOADING,
            ERROR,
            SUCCESS
        }
        fun <T> loading(): Resource<T>{
            return Resource(state = State.LOADING)
        }
        fun <T> success(value: T, size: Int): Resource<T>{
            return Resource(state = State.SUCCESS, value = value, size = size)
        }
        fun <T> error(t: Throwable): Resource<T>{
            return Resource(state = State.ERROR, t= t)
        }
    }
}