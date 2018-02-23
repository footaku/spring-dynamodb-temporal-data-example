package com.example.panage.dynamo.util

import java.time.LocalDateTime

/**
 * @author fu-taku
 */
object DateTimeHolder {
    private val threadLocal: ThreadLocal<LocalDateTime> = ThreadLocal()

    @JvmStatic
    fun <R> at(dateTime: LocalDateTime = LocalDateTime.now(), function: (LocalDateTime) -> R): R {
        threadLocal.get()?.let {
            error("DateTimeHolder has already been initialized.")
        }

        threadLocal.set(dateTime)

        return try {
            function(threadLocal.get())
        } finally {
            threadLocal.remove()
        }
    }

    @JvmStatic
    fun get(): LocalDateTime = threadLocal.get() ?: error("DateTimeHolder has not been initialized yet.")
}

