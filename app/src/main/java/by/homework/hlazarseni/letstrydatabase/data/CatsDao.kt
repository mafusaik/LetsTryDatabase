package by.homework.hlazarseni.letstrydatabase.data

import androidx.room.*
import by.homework.hlazarseni.letstrydatabase.data.Cats
import kotlinx.coroutines.flow.Flow

@Dao
interface CatsDao {
//    @Query("SELECT * FROM cats")
//    fun getAll(): List<Cats>

//    @Insert
//    fun insertAll(vararg cats: Cats)

//    @Delete
//    fun delete(cats: Cats)

//    @Query("SELECT * FROM cats")
//    fun getAllData(): LiveData<List<Cats>>



    @Query("SELECT * from cats")
    fun getCats(): Flow<List<Cats>>

    @Query("SELECT * from cats WHERE id = :id")
    fun getCat(id: Int): Flow<Cats>


    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(item: Cats)

    @Update
    suspend fun update(item: Cats)

    @Delete
    suspend fun delete(item: Cats)
}