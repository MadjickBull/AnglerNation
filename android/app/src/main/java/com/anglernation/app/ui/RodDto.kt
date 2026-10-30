package com.anglernation.app.ui

// Data model for Moshi JSON serialization/deserialization
data class RodDto(
    val id: String,
    val name: String,
    val description: String? = null,
    val createdAt: String? = null
)