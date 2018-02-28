package com.example.panage.dynamo.repository

/**
 * @author fu-taku
 */
enum class OrderBy(private val asc: Boolean) {
    ASC(true),
    DESC(false);

    fun getValue() = this.asc
}
