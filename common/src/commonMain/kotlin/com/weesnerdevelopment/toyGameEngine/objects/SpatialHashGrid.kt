package com.weesnerDevelopment.toyGameEngine.objects

import com.weesnerDevelopment.toyGameEngine.math.*

class SpatialHashGrid(
    val worldSize: Size,
    val cellSize: Number
) {
    val cellsPerRow = ceil(worldSize.width / cellSize)
    val cellsPerColumn = ceil(worldSize.height / cellSize)

    val numCells: Int = (cellsPerRow * cellsPerColumn).toInt()

    var dynamicCells: Array<ArrayList<GameObject>> = Array(numCells) { ArrayList(10) }
    var staticCells: Array<ArrayList<GameObject>> = Array(numCells) { ArrayList(10) }

    var cellIds = IntArray(4)

    val foundObjects = ArrayList<GameObject>(10)

    fun insertStaticObject(obj: GameObject) {
        val cellIds: IntArray = getCellIds(obj)
        var i = 0
        var cellId = -1
        while (i <= 3 && cellIds[i++].also { cellId = it } != -1) {
            staticCells[cellId].add(obj)
        }
    }

    fun insertDynamicObject(obj: GameObject) {
        val cellIds: IntArray = getCellIds(obj)
        var i = 0
        var cellId = -1
        while (i <= 3 && cellIds[i++].also { cellId = it } != -1) {
            dynamicCells[cellId].add(obj)
        }
    }

    fun removeObject(obj: GameObject) {
        val cellIds: IntArray = getCellIds(obj)
        var i = 0
        var cellId = -1
        while (i <= 3 && cellIds[i++].also { cellId = it } != -1) {
            dynamicCells[cellId].remove(obj)
            staticCells[cellId].remove(obj)
        }
    }

    fun clearDynamicCells() {
        for (element in dynamicCells) {
            element.clear()
        }
    }

    fun getPotentialColliders(obj: GameObject): List<GameObject> {
        foundObjects.clear()
        val cellIds: IntArray = getCellIds(obj)
        var i = 0
        var cellId = -1
        while (i <= 3 && cellIds[i++].also { cellId = it } != -1) {
            for (j in 0 until dynamicCells[cellId].size) {
                val collider = dynamicCells[cellId][j]
                if (!foundObjects.contains(collider)) foundObjects.add(collider)
            }
            for (j in 0 until staticCells[cellId].size) {
                val collider = staticCells[cellId][j]
                if (!foundObjects.contains(collider)) foundObjects.add(collider)
            }
        }
        return foundObjects
    }

    fun getCellIds(obj: GameObject): IntArray {
        var i = 0

        val value1 = Vector2D(
            floor(obj.bounds.lowerLeft.x / cellSize),
            floor(obj.bounds.lowerLeft.y / cellSize)
        )
        val value2 = Vector2D(
            floor((obj.bounds.lowerLeft.x + obj.bounds.size.width) / cellSize),
            floor((obj.bounds.lowerLeft.y + obj.bounds.size.height) / cellSize)
        )

        fun Vector2D.withCellsPerRow() = this.x + this.y * cellsPerRow
        fun Vector2D.withCellsPerRow(other: Vector2D) = this.x + other.y * cellsPerRow
        fun Vector2D.valueInWidth() = x >= 0 && x < cellsPerRow
        fun Vector2D.valueInHeight() = y >= 0 && y < cellsPerColumn

        when {
            value1.x == value2.x && value1.y == value2.y -> {
                cellIds[i++] =
                    if (value1.valueInWidth() && value1.valueInHeight())
                        (value1.withCellsPerRow()).toInt()
                    else
                        -1
            }
            value1.x == value2.x -> {
                if (value1.valueInWidth()) {
                    if (value1.valueInHeight())
                        cellIds[i++] = (value1.withCellsPerRow()).toInt()
                    if (value2.valueInHeight())
                        cellIds[i++] = (value1.withCellsPerRow(value2)).toInt()
                }
            }
            value1.y == value2.y -> {
                if (value1.valueInHeight()) {
                    if (value1.valueInWidth())
                        cellIds[i++] = (value1.withCellsPerRow()).toInt()
                    if (value2.valueInWidth())
                        cellIds[i++] = (value2.withCellsPerRow(value1)).toInt()
                }
            }
            else -> {
                if (value1.valueInWidth() && value1.valueInHeight())
                    cellIds[i++] = (value1.withCellsPerRow()).toInt()
                if (value2.valueInWidth() && value1.valueInHeight())
                    cellIds[i++] = (value2.withCellsPerRow(value1)).toInt()
                if (value2.valueInWidth() && value2.valueInHeight())
                    cellIds[i++] = (value2.withCellsPerRow()).toInt()
                if (value1.valueInWidth() && value2.valueInHeight())
                    cellIds[i++] = (value1.withCellsPerRow(value2)).toInt()
            }
        }

        while (i <= 3)
            cellIds[i++] = -1

        return cellIds
    }
}
