package by.homework.hlazarseni.letstrydatabase

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import by.homework.hlazarseni.letstrydatabase.databinding.ListFragmentBinding
import kotlinx.coroutines.delay
import java.io.File.separator

class ListFragment : Fragment() {
    private var _binding: ListFragmentBinding? = null
    private val binding get() = requireNotNull(_binding)

    private val adapter by lazy {
        CatAdapter()
    }

    private val catDao by lazy {
        requireContext().catDatabase.catsDao
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ListFragmentBinding.inflate(inflater, container, false)
            .also { _binding = it }
            .root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {

            val linearLayoutManager = LinearLayoutManager(
                view.context, LinearLayoutManager.VERTICAL, false
            )

            swipeRefresh.setOnRefreshListener {
                updateCatsList()
                swipeRefresh.isRefreshing = false
            }

            recyclerView.adapter = adapter
            recyclerView.layoutManager = linearLayoutManager
            recyclerView.addVerticalGaps()
        }
        updateCatsList()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun updateCatsList() {
        adapter.submitList(catDao.getAll())
    }

//    private fun removeCat(){
//        val removedCat = catDao.getAll().get()
//        catDao.delete(removedCat)
//
//    }
}
