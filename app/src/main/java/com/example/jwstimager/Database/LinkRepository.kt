package com.example.jwstimager.Database

import kotlinx.coroutines.flow.Flow


//Declares the DAO as a private property in the constructor. Pass in the DAO
//instead of the whole database, because you only need access to the DAO
class LinkRepository(private val linkDao: LinkDao) {

    // Room executes all queries on a separate thread.
    // Observed Flow will notify the observer when the data has changed.
    val allLinks: Flow<List<Link>> = linkDao.getAlphabetizedLinks()

    // By default Room runs suspend queries off the main thread, therefore, we don't need to
    // implement anything else to ensure we're not doing loop running database work
    // off the main thread.
    @Suppress("RedundantSuspendModifier")
    suspend fun insert(link: Link) {
        linkDao.insert(link)
    }
}