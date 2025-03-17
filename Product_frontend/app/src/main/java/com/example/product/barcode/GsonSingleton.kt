package com.example.product.barcode
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonPrimitive
import com.google.gson.JsonSerializer
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

object GsonSingleton {
    val gson: Gson = GsonBuilder().apply {
        registerTypeAdapter(LocalDateTime::class.java, JsonSerializer<LocalDateTime> { src, typeOfSrc, context ->
            JsonPrimitive(src.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME))
        })
        registerTypeAdapter(LocalDateTime::class.java, LocalDateTimeDeserializer())
        // Add other configurations here if needed
    }.create()
}