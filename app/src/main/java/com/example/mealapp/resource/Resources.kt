package com.example.mealapp.resource

sealed class Resources<out T> {
    data class LOADING<out T>(
        val state: Boolean,
    ) : Resources<T>()

    data class SUCCESS<out T>(
        val data: T,
    ) : Resources<T>()

    data class FAILURE<out T>(
        val e: String,
    ) : Resources<T>()

    data class EMPTY<out T>(
        val isEmpty: Boolean,
    ) : Resources<T>()
}
