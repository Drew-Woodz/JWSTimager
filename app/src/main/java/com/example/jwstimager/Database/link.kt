package com.example.jwstimager.Database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "link_table")
data class Link(

    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "link") val link: String

)