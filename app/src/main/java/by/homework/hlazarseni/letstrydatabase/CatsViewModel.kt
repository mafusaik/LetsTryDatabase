package by.homework.hlazarseni.letstrydatabase

import android.content.ClipData
import androidx.lifecycle.*
import kotlinx.coroutines.launch


class CatsViewModel(private val catsDao: CatsDao) : ViewModel() {

    val allCats: LiveData<List<Cats>> = catsDao.getCats().asLiveData()

    fun updateCat(
        catId: Int,
        catName: String,
        catColor: String
    ) {
        val updatedCat = getUpdatedCatEntry(catId, catName, catColor)
        updateCat(updatedCat)
    }

    private fun updateCat(cats: Cats) {
        viewModelScope.launch {
            catsDao.update(cats)
        }
    }

    fun retrieveCat(id: Int): LiveData<Cats> {
        return catsDao.getCat(id).asLiveData()
    }

    fun addNewCat(nickname: String, color: String) {
        val newCat = getNewCatEntry(nickname, color)
        insertCat(newCat)
    }

    private fun insertCat(item: Cats) {
        viewModelScope.launch {
           catsDao.insert(item)
        }
    }

    fun deleteCat(cats: Cats) {
        viewModelScope.launch {
            catsDao.delete(cats)
        }
    }

    private fun getNewCatEntry(catName: String, catColor: String): Cats {
        return Cats(
            nickname = catName,
            color = catColor
        )
    }

    private fun getUpdatedCatEntry(
        catId: Int,
        catName: String,
        catColor: String
    ): Cats {
        return Cats(
            id = catId,
            nickname = catName,
            color = catColor
        )
    }
}

class InventoryViewModelFactory(private val catsDao: CatsDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CatsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CatsViewModel(catsDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}