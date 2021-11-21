package com.example.news.base


class Resource<T> (
    val value: T? = null,
    val t: Throwable? = null,
    val state: State,
    val size: Int = 0,
    val allData: T? =null
        ){
    companion object{
        enum class State{
            LOADING,
            ERROR,
            SUCCESS
        }
        fun <T> loading(): Resource<T> {
            return Resource(state = State.LOADING)
        }
        fun <T> success(value: T, size: Int, allData: T): Resource<T> {
            for (i in 4..size step 5){
                // size // alldata
                    // check end of screen size then reload new
                    allData as List<*>
                    allData.take(i)
                //load 5 items
            }
            return Resource(state = State.SUCCESS, value = value, size = size, allData = allData)
        }
        fun <T> error(t: Throwable): Resource<T> {
            return Resource(state = State.ERROR, t= t)
        }
    }

}