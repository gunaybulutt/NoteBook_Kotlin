package com.gunay.kotlinnotebook.roomdb

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
data class Notes (

    @ColumnInfo(name = "note_head") val note_head: String,
    @ColumnInfo(name = "note_text") val note_text: String

): Serializable {

    @PrimaryKey(autoGenerate = true)
    var noteid = 0
}
