package com.tinyDeveloper.yavaran.adapter

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.tinyDeveloper.yavaran.MainActivity
import com.tinyDeveloper.yavaran.R
import com.tinyDeveloper.yavaran.api.apiModels.toGetHelp.AddToGetHelpBodyModel
import com.tinyDeveloper.yavaran.api.apiModels.toGetHelp.ToGetHelp
import com.tinyDeveloper.yavaran.dataModels.KeyValues
import com.tinyDeveloper.yavaran.fragments.AddToGetHelpFragment
import com.tinyDeveloper.yavaran.fragments.ShowToGetHelpFragment
import com.tinyDeveloper.yavaran.publicClass.*
import org.koin.standalone.KoinComponent
import org.koin.standalone.inject

class ToGetHelpEditAdapter(val list: List<ToGetHelp>, val backFrom: String): RecyclerView.Adapter<ToGetHelpEditAdapter.ViewHolder>(), KoinComponent {
    private lateinit var completedAlertDialog: AlertDialog
    private lateinit var completedAlertDialogBuilder: AlertDialog.Builder
    inner class ViewHolder(val view: View): RecyclerView.ViewHolder(view){
        val image: ImageView = view.findViewById(R.id.itemEditImage)
        val title: TextView = view.findViewById(R.id.itemEditTitle)
        val shortDescription: TextView = view.findViewById(R.id.itemEditShortDescription)
        val category: TextView = view.findViewById(R.id.itemEditCategory)
        val date: TextView = view.findViewById(R.id.itemEditDate)
        val status: TextView = view.findViewById(R.id.itemEditStatus)
        val btnEdit: Button = view.findViewById(R.id.itemEditBtn)
        val btnCompleted: Button = view.findViewById(R.id.itemEditBtnCompleted)
        @SuppressLint("InflateParams", "SetTextI18n")
        fun binder(item : ToGetHelp){
            if (item.images != null && item.images.count() > 0){
                val picasso: PicassoCreator by inject()
                picasso.getImage(MainActivity.mContext.baseDownloadUrl+item.images[0], image, null)
            }else
                image.setImageDrawable(ContextCompat.getDrawable(MainActivity.mContext, R.drawable.place_holder))
            title.text = item.toGetHelp.title
            shortDescription.text = item.toGetHelp.shortDescription
            val getCategoryTitle : GetCategoryTitle by inject()
            val categoryTitle = getCategoryTitle.getCategory(item.toGetHelp.category)
            category.text = "دستبندی: $categoryTitle"
            val changeFragment : ChangeFragment by inject()
            val showDate : ShowDate by inject()
            val setStatusTitle : GetStatusTitle by inject()
            date.text = showDate.showHumanDate(item.toGetHelp.date.toLong())
            status.text ="وضعیت: ${setStatusTitle.getStatusTitle(item.toGetHelp.status)}"

            btnCompleted.isEnabled =
                !(item.toGetHelp.status == "canceled" || item.toGetHelp.status == "completed")

            btnEdit.setOnClickListener {
                val imageList = mutableListOf<KeyValues>()
                if (item.images != null){
                    for (counter in 0 until item.images.count()){
                        imageList += KeyValues("image$counter", item.images[counter])
                    }
                }

                changeFragment.newArgumentsFragment(
                    AddToGetHelpFragment(),
                    isBack = true,
                    title = "ویرایش آگهی",
                    name = null,
                    listArguments = listOf(
                        KeyValues("type", "update"),
                        KeyValues("from", backFrom),
                        KeyValues("id", item.toGetHelp.id),
                        KeyValues("userId", item.toGetHelp.parentId),
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

            btnCompleted.setOnClickListener {
                val view = LayoutInflater.from(MainActivity.mContext).inflate(R.layout.completed_dialog_layout, null, false)
                val btnNo : Button = view.findViewById(R.id.completedDialogNo)
                val btnYes : Button = view.findViewById(R.id.completedDialogYes)
                completedAlertDialogBuilder = AlertDialog.Builder(MainActivity.mContext, R.style.dialogMain).setView(view)
                completedAlertDialog = completedAlertDialogBuilder.create()
                completedAlertDialog.show()

                btnNo.setOnClickListener {
                    val completedToGetHelp: CompletedToGetHelp by inject()
                    completedToGetHelp.completed(
                        AddToGetHelpBodyModel(
                        "update",
                        item.toGetHelp.id,
                        item.toGetHelp.parentId,
                        item.toGetHelp.title,
                        item.toGetHelp.shortDescription,
                        item.toGetHelp.description,
                        item.toGetHelp.category,
                        item.toGetHelp.secretLevel,
                        item.toGetHelp.urgency,
                        item.images,
                        item.toGetHelp.date,
                        "canceled"
                    ),
                    btnCompleted,
                    completedAlertDialog
                    )
                    status.text = "وضعیت: لغو شده"
                }

                btnYes.setOnClickListener {
                    val completedToGetHelp: CompletedToGetHelp by inject()
                    completedToGetHelp.completed(AddToGetHelpBodyModel(
                        "update",
                        item.toGetHelp.id,
                        item.toGetHelp.parentId,
                        item.toGetHelp.title,
                        item.toGetHelp.shortDescription,
                        item.toGetHelp.description,
                        item.toGetHelp.category,
                        item.toGetHelp.secretLevel,
                        item.toGetHelp.urgency,
                        item.images,
                        item.toGetHelp.date,
                        "completed"
                    ),
                    btnCompleted,
                    completedAlertDialog
                    )
                    status.text = "وضعیت: تکمیل شده"
                }
            }

            view.setOnClickListener {
                val imageList = mutableListOf<KeyValues>()
                if (item.images != null){
                    for (counter in 0 until item.images.count()){
                        imageList += KeyValues("image$counter", item.images[counter])
                    }
                }
                changeFragment.newArgumentsFragment(
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
        return ViewHolder(LayoutInflater.from(MainActivity.mContext).inflate(R.layout.item_edit_layout, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binder(list[position])
    }

    override fun getItemCount(): Int = list.count()
}