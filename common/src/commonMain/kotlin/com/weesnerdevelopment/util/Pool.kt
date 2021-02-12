package com.weesnerDevelopment.toyGameEngine.util

interface PoolObjectFactory<T> {
    fun createObject(): T
}

class Pool<T>(
    private val factory: PoolObjectFactory<T>,
    private val maxSize: Int,
    private val freeObjects: ArrayList<T> = ArrayList(maxSize)
) {
    /**
     * Gets recycled objects as long as the pool has some stored in the list, otherwise we create a
     * new one.
     */
    fun newObject(): T =
        if (freeObjects.isEmpty()) factory.createObject()
        else freeObjects.removeAt(freeObjects.size - 1)

    /**
     * Reinsert [newObject] that we no longer use, if the list is not full.
     */
    fun free(newObject: T) {
        if (freeObjects.size < maxSize)
            freeObjects.add(newObject)
    }
}
