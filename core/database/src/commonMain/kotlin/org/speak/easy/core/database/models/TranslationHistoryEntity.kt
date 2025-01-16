package org.speak.easy.core.database.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "translations_history")
data class TranslationHistoryEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    @ColumnInfo("target_language")
    val targetLanguage: String,
    @ColumnInfo("source_language")
    val sourceLanguage: String,
    @ColumnInfo("target_text")
    val targetText: String,
    @ColumnInfo("source_text")
    val sourceText: String,
    @ColumnInfo("date_in_mills")
    val dateInMills: Long
)