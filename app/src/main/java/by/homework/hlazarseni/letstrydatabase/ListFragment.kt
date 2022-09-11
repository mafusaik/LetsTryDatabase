package by.homework.hlazarseni.letstrydatabase

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import by.homework.hlazarseni.letstrydatabase.databinding.ListFragmentBinding

class ListFragment: Fragment() {
    private var _binding: ListFragmentBinding? = null
    private val binding get() = requireNotNull(_binding)

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

        updateCatsList()

        }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun updateCatsList() {
        with(binding) {
            textResult.text = catDao.getAll().joinToString(separator = "\n"){
                "Cats №${it.id}: ${it.nickname}, ${it.color} color"
            }
        }
    }
}
