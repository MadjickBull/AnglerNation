package com.anglernation.app.ui

// Data model for Moshi JSON serialization/deserialization
data class RodDto(
    @get:JsonAdapter(KotlinProtoAdapter::class)
    val id: String,
    @get:JsonAdapter(KotlinProtoAdapter::class)
    val name: String,
    @get:JsonAdapter(KotlinProtoAdapter::class)
    val description: String? = null,
    @get:JsonAdapter(KotlinProtoAdapter::class)
    val createdAt: String? = null
)