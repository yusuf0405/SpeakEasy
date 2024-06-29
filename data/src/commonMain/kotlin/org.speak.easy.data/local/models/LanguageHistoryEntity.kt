package org.speak.easy.data.local.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "language_history")
internal class LanguageHistoryEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    @ColumnInfo(name = "language")
    val language: String,
    @ColumnInfo(name = "language_code")
    val languageCode: String,
)