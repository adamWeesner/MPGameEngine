package com.weesnerDevelopment.toyGameEngine.util

class Stack<T> {
    companion object {
        fun <T> create(list: List<T>): Stack<T> {
            val stack = Stack<T>()
            list.forEach { stack.push(it) }
            return stack
        }
    }

    private val storage = arrayListOf<T>()

    fun pop(): T? =
        if (storage.isEmpty()) null
        else storage.removeAt(storage.lastIndex)

    fun push(item: T) {
        storage.add(item)
    }

    fun peek(): T? = storage.lastOrNull()

    val size get() = storage.size

    fun isEmpty() = storage.isEmpty()
}

fun <T> stackOf(vararg item: T) = Stack.create(item.toList())