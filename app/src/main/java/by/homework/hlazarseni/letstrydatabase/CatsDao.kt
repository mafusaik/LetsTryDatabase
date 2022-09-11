package by.homework.hlazarseni.letstrydatabase

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface CatsDao {
    @Query("SELECT * FROM cats")
    fun getAll(): List<Cats>

    @Insert
    fun insertAll(vararg cats: Cats)

    @Delete
    fun delete(cats: Cats)
}