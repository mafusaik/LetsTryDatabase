package by.homework.hlazarseni.letstrydatabase

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Cats::class], version = 1)
abstract class CatDatabase : RoomDatabase() {
    //abstract fun catsDao(): CatsDao
    abstract val catsDao: CatsDao

}