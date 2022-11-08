package com.example.jwstimager.Database

import android.app.Application
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class LinksApplication : Application() {
    // No need to cancel this scope as it''l be torn down with the process
    val applicationScope = CoroutineScope(SupervisorJob())
    // Using by lazy so the database and the repository are only created when they're needed
    // rather than when the application starts
    val database by lazy { LinkRoomDatabase.getDatabase(this, applicationScope)}
    val repository by lazy { LinkRepository(database.linkDao())}
}