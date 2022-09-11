package by.homework.hlazarseni.letstrydatabase

import android.app.Application
import android.content.Context
import androidx.room.Room

class DatabaseApplication : Application() {

    private var _catDatabase: CatDatabase? = null
    val catDatabase get() = requireNotNull(_catDatabase)

    override fun onCreate() {
        super.onCreate()
        _catDatabase = Room
            .databaseBuilder(
                this,
                CatDatabase::class.java,
                "cats-database"
            )
            .allowMainThreadQueries()
            .build()
    }
}

val Context.catDatabase: CatDatabase
    get() = when (this) {
        is DatabaseApplication -> catDatabase
        else -> applicationContext.catDatabase
    }