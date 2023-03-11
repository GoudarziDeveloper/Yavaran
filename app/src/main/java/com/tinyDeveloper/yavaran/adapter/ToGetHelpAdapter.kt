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
import com.tinyDeveloper.yavaran.api.apiModels.toGetHelp.ToGetHelp
import com.tinyDeveloper.yavaran.dataModels.KeyValues
import com.tinyDeveloper.yavaran.fragments.ShowToGetHelpFragment
import com.tinyDeveloper.yavaran.publicClass.GetCategoryTitle
import com.tinyDeveloper.yavaran.publicClass.PicassoCreator
import com.tinyDeveloper.yavaran.publicClass.ChangeFragment
import com.tinyDeveloper.yavaran.publicClass.ShowDate
import org.koin.standalone.KoinComponent
import org.koin.standalone.inject

class ToGetHelpAdapter(var list:List<ToGetHelp>):RecyclerView.Adapter<ToGetHelpAdapter.ViewHolder>(), KoinComponent {
    inner class ViewHolder(val view:View): RecyclerView.ViewHolder(view){
        val image:ImageView = view.findViewById(R.id.itemImage)
        val title:TextView = view.findViewById(R.id.itemTitle)
        val shortDescription:TextView = view.findViewById(R.id.itemShortDescription)
        val category:TextView = view.findViewById(R.id.itemCategory)
        val date:TextView = view.findViewById(R.id.itemDate)
        val status:TextView = view.findViewById(R.id.itemStatus)
        @SuppressLint("SetTextI18n")
        fun binder(item: ToGetHelp){
            val picassoCreator: PicassoCreator by inject()
            if (item.images != null && item.images.count() > 0)
                picassoCreator.getImage(MainActivity.mContext.baseDownloadUrl + item.images[0], image, null)
            else
                image.setImageDrawable(ContextCompat.getDrawable(MainActivity.mContext, R.drawable.place_holder))
            title.text = item.toGetHelp.title
            shortDescription.text = item.toGetHelp.shortDescription
            val getCategoryTitle : GetCategoryTitle by inject()
            val categoryTitle = getCategoryTitle.getCategory(item.toGetHelp.category)
            category.text = "دستبندی: $categoryTitle"
            val showDate : ShowDate by inject()
            date.text = showDate.showHumanDate(item.toGetHelp.date.toLong())
            status.text ="فوریت: ${item.toGetHelp.urgency}"
            view.setOnClickListener {
                val public: ChangeFragment by inject()
                val imageList = mutableListOf<KeyValues>()
                if (item.images != null){
                    for (counter in 0 until item.images.count()){
                        imageList += KeyValues("image$counter", item.images[counter])
                    }
                }
                public.newArgumentsFragment(
                    ShowToGetHelpFragment(),
                    isBack = true,
                    title = "مشخصات آگهی",
                    name = null,
                    listArguments = listOf(
                        KeyValues("id", item.toGetHelp.id),
                        KeyValues("parentId", item.toGetHelp.parentId),
                        KeyValues("title", item.toGetHelp.title),
                        KeyValues("shortDescription", item.toGetHelp.shortDescription),
                        KeyValues("description", item.toGetHelp.description),
                        KeyValues("category", item.toGetHelp.category),
                        KeyValues("secretLevel", item.toGetHelp.secretLevel),
                        KeyValues("urgency", item.toGetHelp.urgency),
                        KeyValues("date", item.toGetHelp.date),
                        KeyValues("status", item.toGetHelp.status)
                    ),
                    listArguments2 = imageList
                )
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(MainActivity.mContext).inflate(R.layout.items_layout, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.binder(list[position])

    override fun getItemCount(): Int = list.count()
}