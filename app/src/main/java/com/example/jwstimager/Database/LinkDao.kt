package com.example.jwstimager.Database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface LinkDao {

    @Query("SELECT * FROM link_table ORDER BY link ASC")
    fun getAlphabetizedLinks(): Flow<List<Link>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(link: Link)

    @Query("DELETE FROM link_table")
    suspend fun deleteAll()
}