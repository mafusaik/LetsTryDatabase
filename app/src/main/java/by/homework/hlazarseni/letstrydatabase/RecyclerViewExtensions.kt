package by.homework.hlazarseni.letstrydatabase

import android.graphics.Rect
import android.view.View
import androidx.annotation.DimenRes
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import by.homework.hlazarseni.letstrydatabase.R

fun RecyclerView.addVerticalGaps(@DimenRes gapRes: Int = R.dimen.gap) {

    val verticalGap = context.resources.getDimensionPixelSize(gapRes)
    addItemDecoration(object : RecyclerView.ItemDecoration() {
        override fun getItemOffsets(
            outRect: Rect,
            view: View,
            parent: RecyclerView,
            state: RecyclerView.State
        ) {
            val position = parent.getChildAdapterPosition(view)
            if (position != parent.adapter?.itemCount?.minus(1)) {
                outRect.bottom = verticalGap
            }
        }
    })
}

