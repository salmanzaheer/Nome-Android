package com.example.nome.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(tableName = "userPresets")
data class UserPreset(
    @PrimaryKey
    val id: UUID = UUID.randomUUID(),
    @ColumnInfo
    val name: String,
    @ColumnInfo
    val BPM: Int
)
