package com.gunay.kotlinnotebook.roomdb

import androidx.room.Database
import androidx.room.RoomDatabase


@Database(entities = [Notes::class], version = 1)
abstract class NotesDatabase : RoomDatabase() {
    abstract fun notesDao(): NotesDao
}