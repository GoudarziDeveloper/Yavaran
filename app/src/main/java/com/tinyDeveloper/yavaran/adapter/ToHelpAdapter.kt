package com.tinyDeveloper.yavaran.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.tinyDeveloper.yavaran.MainActivity
import com.tinyDeveloper.yavaran.R
import com.tinyDeveloper.yavaran.api.apiModels.toHelp.ToHelp
import com.tinyDeveloper.yavaran.dataModels.KeyValues
import com.tinyDeveloper.yavaran.fragments.ShowToHelpFragment
import com.tinyDeveloper.yavaran.publicClass.*
import org.koin.standalone.KoinComponent
import org.koin.standalone.inject

class ToHelpAdapter(var list:List<ToHelp>): RecyclerView.Adapter<ToHelpAdapter.ViewHolder>(), KoinComponent {
    inner class ViewHolder(val view: View): RecyclerView.ViewHolder(view){
        val image: ImageView = view.findViewById(R.id.itemImage)
        val title: TextView = view.findViewById(R.id.itemTitle)
        val shortDescription: TextView = view.findViewById(R.id.itemShortDescription)
        val category: TextView = view.findViewById(R.id.itemCategory)
        val date: TextView = view.findViewById(R.id.itemDate)
        val status: TextView = view.findViewById(R.id.itemStatus)
        @SuppressLint("SetTextI18n")
        fun binder(item:ToHelp){
            val picassoCreator: PicassoCreator by inject()
            if (item.images != null && item.images.count() > 0)
                picassoCreator.getImage(MainActivity.mContext.baseDownloadUrl + item.images[0], image, null)
            else
                image.setImageDrawable(ContextCompat.getDrawable(MainActivity.mContext, R.drawable.place_holder))
            title.text = item.toHelp.title
            shortDescription.text = item.toHelp.shortDescription
            val getCategoryTitle : GetCategoryTitle by inject()
            val categoryTitle = getCategoryTitle.getCategory(item.toHelp.category)
            category.text = "دستبندی: $categoryTitle"
            val showDate : ShowDate by inject()
            date.text = showDate.showHumanDate(item.toHelp.date.toLong())
            val getStatusTitle: GetStatusTitle by inject()
            val statusTitle = getStatusTitle.getStatusTitle(item.toHelp.status)
            status.text ="وضعیت: $statusTitle"
            view.setOnClickListener {
                val public: ChangeFragment by inject()
                val imageList = mutableListOf<KeyValues>()
                if (item.images != null){
                    for (counter in 0 until item.images.count()){
                        imageList += KeyValues("image$counter", item.images[counter])
                    }
                }
                public.newArgumentsFragment(
                    ShowToHelpFragment(),
                    isBack = true,
                    title = "مشخصات آگهی",
                    name = null,
                    listArguments = listOf(
                        KeyValues("id", item.toHelp.id),
                        KeyValues("parentId", item.toHelp.parentId),
                        KeyValues("title", item.toHelp.title),
                        KeyValues("shortDescription", item.toHelp.shortDescription),
                        KeyValues("description", item.toHelp.description),
                        KeyValues("category", item.toHelp.category),
                        KeyValues("date", item.toHelp.date),
                        KeyValues("status", item.toHelp.status)
                    ),
                    listArguments2 = imageList
                )
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(MainActivity.mContext).inflate(R.layout.items_layout, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binder(list[position])
    }

    override fun getItemCount(): Int = list.count()
}