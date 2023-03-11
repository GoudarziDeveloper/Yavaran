package com.tinyDeveloper.yavaran.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.tinyDeveloper.yavaran.MainActivity
import com.tinyDeveloper.yavaran.R
import com.tinyDeveloper.yavaran.api.apiModels.categories.Category
import com.tinyDeveloper.yavaran.dataModels.KeyValues
import com.tinyDeveloper.yavaran.fragments.CategoryFragment
import com.tinyDeveloper.yavaran.publicClass.PicassoCreator
import com.tinyDeveloper.yavaran.publicClass.ChangeFragment
import org.koin.standalone.KoinComponent
import org.koin.standalone.inject

class CategoriesAdapter(
    val list: List<Category>,
    val isToGetHelp:Boolean = true
): RecyclerView.Adapter<CategoriesAdapter.ViewHolder>(), KoinComponent {
    inner class ViewHolder(val view: View):RecyclerView.ViewHolder(view){
        val image:ImageView = view.findViewById(R.id.categoriesImage)
        val title: TextView = view.findViewById(R.id.categoriesTitle)
        fun binder(item:Category){
            val picassoCreator: PicassoCreator by inject()
            picassoCreator.getImage(MainActivity.mContext.baseDownloadUrl + item.image, image, null)
            title.text = item.title
            view.setOnClickListener {
                val public: ChangeFragment by inject()
                public.newArgumentsFragment(CategoryFragment(),
                    isBack = true,
                    title = item.title,
                    listArguments = listOf(
                        KeyValues("id", item.id),
                        KeyValues("isToGetHelp", isToGetHelp.toString())
                    )
                )
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(MainActivity.mContext).inflate(R.layout.categoreis_list, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.binder(list[position])


    override fun getItemCount(): Int = list.count()
}