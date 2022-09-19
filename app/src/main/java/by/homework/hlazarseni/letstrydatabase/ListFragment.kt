package by.homework.hlazarseni.letstrydatabase


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import by.homework.hlazarseni.letstrydatabase.databinding.ListFragmentBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar


class ListFragment : Fragment() {
//    private val navigationArgs: ActionFragmentArgs by navArgs()
//    private lateinit var cats: Cats

    private var _binding: ListFragmentBinding? = null
    private val binding get() = requireNotNull(_binding)

    private val catViewModel: CatsViewModel by activityViewModels {
        InventoryViewModelFactory(
            (activity?.application as DatabaseApplication).catDatabase.catsDao
        )
    }

    private val adapter by lazy {
        CatAdapter(
            onItemClicked = {
                showConfirmationEditDialog()
            }
        )
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

//    private fun bind(cats: Cats) {
//        binding.apply {
//            cats.id
//            cats.nickname
//            cats.color
//
//        }
//    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        val id = navigationArgs.catId
//        val id = view.findViewById<TextView>(R.id.action_global_action_data)
//        id.text = arguments.toString()
//        val catId = id.text.toString().toInt()
//        catViewModel.retrieveCat(id).observe(this.viewLifecycleOwner) { selectedCat ->
//            cats = selectedCat
//             bind(cats)
//        }

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
                    showConfirmationDeleteDialog(view, cats)

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
        catViewModel.allCats.observe(this.viewLifecycleOwner) { items ->
            items.let {
                adapter.submitList(it)
            }
        }
    }

    private fun showConfirmationDeleteDialog(view: View, cats: Cats) {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(getString(android.R.string.dialog_alert_title))
            .setMessage(getString(R.string.delete_question))
            .setCancelable(false)
            .setNegativeButton(getString(R.string.no)) { _, _ ->
                // catViewModel.retrieveCat(cats.id)
                //не восстанавливается кот, только при перезагрузке фрагмента
                // updateCatsList()
            }
            .setPositiveButton(getString(R.string.yes)) { _, _ ->
                catViewModel.deleteCat(cats)
                updateCatsList()
                Snackbar.make(
                    view,
                    getString(R.string.REMOVE_MESSAGE),
                    Snackbar.LENGTH_SHORT
                ).show()
            }
            .show()
    }

    private fun showConfirmationEditDialog() {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(getString(android.R.string.dialog_alert_title))
            .setMessage(getString(R.string.edit_question))
            .setCancelable(false)
            .setNegativeButton(getString(R.string.no)) { _, _ ->
            }
            .setPositiveButton(getString(R.string.yes)) { _, _ ->

                 editItem()

            }
            .show()
    }

    private fun editItem() {
//        val action = ListFragmentDirections.actionGlobalActionData(
//            cats.id
//        )
//        this.findNavController().navigate(action)

//        val catId = cats.id.toString()
//        val bundle = bundleOf(catId to String)
//        view?.findNavController()?.navigate(R.id.action_global_action_data, bundle)
        view?.findNavController()?.navigate(R.id.action_global_action_data)
    }
}
