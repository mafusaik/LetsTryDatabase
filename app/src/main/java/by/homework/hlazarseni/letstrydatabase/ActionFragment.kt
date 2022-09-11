package by.homework.hlazarseni.letstrydatabase

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import by.homework.hlazarseni.letstrydatabase.databinding.ActionFragmentBinding
import com.google.android.material.textfield.TextInputLayout

class ActionFragment : Fragment() {
    private var _binding: ActionFragmentBinding? = null
    private val binding get() = requireNotNull(_binding)

    private val catDao by lazy {
        requireContext().catDatabase.catsDao
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ActionFragmentBinding.inflate(inflater, container, false)
            .also { _binding = it }
            .root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        updateCatsList()

        with(binding) {
            buttonAdd.setOnClickListener {
                val nickname = containerNickname.getTextOrSetError()
                val color = containerColor.getTextOrSetError()
                if (nickname == null || color == null) return@setOnClickListener

                catDao.insertAll(Cats(nickname = nickname, color = color))
                updateCatsList()
                containerNickname.clearEntryField()
                containerColor.clearEntryField()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun updateCatsList() {
        with(binding) {
            containerNickname.error = null
            containerColor.error = null
        }
    }

//    private fun removeCat(){
//        val removedCat = catDao.getAll().get()
//        catDao.delete(removedCat)
//
//    }

    private fun TextInputLayout.getTextOrSetError(): String? {
        return editText?.text?.toString()
            ?.ifBlank {
                error = "Field is empty"
                null
            }
    }

    private fun TextInputLayout.clearEntryField(): String {
        return editText?.text?.clear().toString()
    }
}