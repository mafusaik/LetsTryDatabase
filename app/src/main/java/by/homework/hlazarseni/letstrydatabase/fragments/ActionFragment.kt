package by.homework.hlazarseni.letstrydatabase.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import by.homework.hlazarseni.letstrydatabase.CatsViewModel
import by.homework.hlazarseni.letstrydatabase.DatabaseApplication
import by.homework.hlazarseni.letstrydatabase.InventoryViewModelFactory
import by.homework.hlazarseni.letstrydatabase.R
import by.homework.hlazarseni.letstrydatabase.databinding.ActionFragmentBinding
import com.google.android.material.textfield.TextInputLayout

class ActionFragment : Fragment() {
    private var _binding: ActionFragmentBinding? = null
    private val binding get() = requireNotNull(_binding)

    private val catViewModel: CatsViewModel by activityViewModels {
        InventoryViewModelFactory(
            (activity?.application as DatabaseApplication).catDatabase.catsDao
        )
    }

//    private val navigationArgs: ActionFragmentArgs by navArgs()
//
//    lateinit var cats: Cats

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ActionFragmentBinding.inflate(inflater, container, false)
            .also { _binding = it }
            .root
    }

//    private fun bind(cats: Cats) {
//        binding.apply {
//            editTextNickname.setText(cats.nickname, TextView.BufferType.SPANNABLE)
//            editTextColor.setText(cats.color, TextView.BufferType.SPANNABLE)
//            //  buttonAdd.setOnClickListener { updateItem() }
//        }
//    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        updateCatsList()
        // val id = navigationArgs.catId
//        val id = view.findViewById<TextView>(R.id.action_global_action_data)
//        id.text = arguments.toString()
//        val catId = id.text.toString().toInt()
//        if (catId > 0) {
//            catViewModel.retrieveCat(catId).observe(this.viewLifecycleOwner) { selectedCat ->
//                cats = selectedCat
//                bind(cats)
//            }
//        } else {
            with(binding) {
                buttonAdd.setOnClickListener {

                    val nickname = containerNickname.getTextOrSetError()
                    val color = containerColor.getTextOrSetError()
                    if (nickname == null || color == null) return@setOnClickListener

                    catViewModel.addNewCat(nickname, color)
                    updateCatsList()
                    containerNickname.clearEntryField()
                    containerColor.clearEntryField()
           //     }
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
//
//    private fun updateItem() {
//
//            catViewModel.updateCat(
//                this.navigationArgs.catId,
//                this.binding.editTextNickname.toString(),
//                this.binding.editTextColor.toString()
//
//            )
//
//
//    }
}