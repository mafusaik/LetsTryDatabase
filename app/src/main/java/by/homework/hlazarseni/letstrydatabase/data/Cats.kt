package by.homework.hlazarseni.letstrydatabase.data
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Cats(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val nickname: String,
    val color: String
)
