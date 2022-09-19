package by.homework.hlazarseni.letstrydatabase

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import by.homework.hlazarseni.letstrydatabase.CatAdapter.CatViewHolder
import by.homework.hlazarseni.letstrydatabase.databinding.ItemCatBinding


class CatAdapter(private val onItemClicked: (Cats) -> Unit) :
    ListAdapter<Cats, CatViewHolder>(DIFF_UTIL) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CatViewHolder {
       // return CatViewHolder.create(parent)
        return CatViewHolder(
            ItemCatBinding.inflate(
                LayoutInflater.from(
                    parent.context
                )
            )
        )
    }

    override fun onBindViewHolder(holder: CatViewHolder, position: Int) {
        val current = getItem(position)
        // holder.bind(current.id.toString(),current.nickname, current.color)
        holder.itemView.setOnClickListener {
            onItemClicked(current)
        }
        holder.bind(current)
    }

    class CatViewHolder(private var binding: ItemCatBinding) :
        RecyclerView.ViewHolder(binding.root) {
//    class CatViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
//        private val idItemView: TextView = itemView.findViewById(R.id.id_text)
//        private val nameItemView: TextView = itemView.findViewById(R.id.name_text)
//        private val colorItemView: TextView = itemView.findViewById(R.id.color_text)

        //        fun bind(id: String, name: String, color: String) {
//            idItemView.text = id
//            nameItemView.text = name
//            colorItemView.text = color
//        }
        fun bind(cats: Cats) {
            binding.idText.text = cats.id.toString()
            binding.nameText.text = cats.nickname
            binding.colorText.text = cats.color

        }

//        companion object {
//            fun create(parent: ViewGroup): CatViewHolder {
//                val view: View = LayoutInflater.from(parent.context)
//                    .inflate(R.layout.item_cat, parent, false)
//                return CatViewHolder(view)
//            }
//        }
    }

    companion object {

        private val DIFF_UTIL = object : DiffUtil.ItemCallback<Cats>() {
            override fun areItemsTheSame(
                oldItem: Cats,
                newItem: Cats
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: Cats,
                newItem: Cats
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}