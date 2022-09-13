package by.homework.hlazarseni.letstrydatabase

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import by.homework.hlazarseni.letstrydatabase.databinding.ListFragmentBinding
import com.google.android.material.snackbar.Snackbar


class ListFragment : Fragment() {
    private var _binding: ListFragmentBinding? = null
    private val binding get() = requireNotNull(_binding)

    private val catViewModel: CatsViewModel by activityViewModels {
        InventoryViewModelFactory(
            (activity?.application as DatabaseApplication).catDatabase.catsDao
        )
    }

    private val adapter by lazy {
        CatAdapter()
    }

//    private val catDao by lazy {
//        requireContext().catDatabase.catsDao
//    }

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

            val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(
                ItemTouchHelper.UP or ItemTouchHelper.DOWN,
                ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
            ) {
                override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    target: RecyclerView.ViewHolder
                ): Boolean {
                    return true
                }

                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    val position = viewHolder.adapterPosition
                    val cats = adapter.currentList[position]

                    catViewModel.deleteCat(cats)
                    //catDao.delete(cats)
                    updateCatsList()

                    Snackbar.make(
                        view,
                        "Сat is removed, you are cruel...",
                        Snackbar.LENGTH_SHORT
                    ).show()
                }
            }
            ItemTouchHelper(itemTouchHelperCallback).apply {
                attachToRecyclerView(binding.recyclerView)
            }
        }
        updateCatsList()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun updateCatsList() {
        //adapter.submitList(catDao.getAll())
        catViewModel.allCats.observe(this.viewLifecycleOwner) { items ->
            items.let {
                adapter.submitList(it)
            }
        }

    }


}
