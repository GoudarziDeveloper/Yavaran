package com.tinyDeveloper.yavaran.adapter

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.tinyDeveloper.yavaran.MainActivity
import com.tinyDeveloper.yavaran.R
import com.tinyDeveloper.yavaran.api.ValidateDataListener
import com.tinyDeveloper.yavaran.api.apiModels.toHelp.AddToHelpBodyModel
import com.tinyDeveloper.yavaran.api.apiModels.toHelp.ToHelp
import com.tinyDeveloper.yavaran.api.apiModels.toHelp.UpdateAndDeleteToHelpModel
import com.tinyDeveloper.yavaran.api.presenters.toHelp.UpdateToHelpPresenter
import com.tinyDeveloper.yavaran.dataModels.KeyValues
import com.tinyDeveloper.yavaran.fragments.AddToHelpFragment
import com.tinyDeveloper.yavaran.fragments.ShowToHelpFragment
import com.tinyDeveloper.yavaran.publicClass.*
import org.koin.standalone.KoinComponent
import org.koin.standalone.inject

class ToHelpEditAdapter(val list:List<ToHelp>):RecyclerView.Adapter<ToHelpEditAdapter.ViewHolder>(), KoinComponent {
    private lateinit var completedAlertDialog: AlertDialog
    private lateinit var completedAlertDialogBuilder: AlertDialog.Builder
    inner class ViewHolder(val view:View): RecyclerView.ViewHolder(view){
        val image: ImageView = view.findViewById(R.id.itemEditImage)
        val title: TextView = view.findViewById(R.id.itemEditTitle)
        val shortDescription: TextView = view.findViewById(R.id.itemEditShortDescription)
        val category: TextView = view.findViewById(R.id.itemEditCategory)
        val date: TextView = view.findViewById(R.id.itemEditDate)
        val status: TextView = view.findViewById(R.id.itemEditStatus)
        val btnEdit: Button = view.findViewById(R.id.itemEditBtn)
        val btnCompleted: Button = view.findViewById(R.id.itemEditBtnCompleted)
        @SuppressLint("SetTextI18n")
        fun binder(item: ToHelp){
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
            val changeFragment : ChangeFragment by inject()
            val showDate : ShowDate by inject()
            date.text = showDate.showHumanDate(item.toHelp.date.toLong())
            val getStatusTitle: GetStatusTitle by inject()
            val statusTitle = getStatusTitle.getStatusTitle(item.toHelp.status)
            status.text ="وضعیت: $statusTitle"

            btnCompleted.isEnabled =
                !(item.toHelp.status == "canceled" || item.toHelp.status == "completed")

            btnEdit.setOnClickListener {
                val imageList = mutableListOf<KeyValues>()
                if (item.images != null){
                    for (counter in 0 until item.images.count()){
                        imageList += KeyValues("image$counter", item.images[counter])
                    }
                }

                changeFragment.newArgumentsFragment(
                    AddToHelpFragment(),
                    isBack = true,
                    title = "ویرایش آگهی",
                    name = null,
                    listArguments = listOf(
                        KeyValues("type", "update"),
                        KeyValues("from", "home"),
                        KeyValues("id", item.toHelp.id),
                        KeyValues("userId", item.toHelp.parentId),
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

            btnCompleted.setOnClickListener {
                val view = LayoutInflater.from(MainActivity.mContext).inflate(R.layout.completed_dialog_layout, null, false)
                val btnNo : Button = view.findViewById(R.id.completedDialogNo)
                val btnYes : Button = view.findViewById(R.id.completedDialogYes)
                completedAlertDialogBuilder = AlertDialog.Builder(MainActivity.mContext, R.style.dialogMain).setView(view)
                completedAlertDialog = completedAlertDialogBuilder.create()
                completedAlertDialog.show()

                btnNo.setOnClickListener {
                    val completedToHelp: CompletedToHelp by inject()
                    completedToHelp.completed(
                        AddToHelpBodyModel(
                            "update",
                            item.toHelp.id,
                            item.toHelp.parentId,
                            item.toHelp.title,
                            item.toHelp.shortDescription,
                            item.toHelp.description,
                            item.toHelp.category,
                            item.toHelp.date,
                            "canceled",
                            item.images
                        ),
                        btnCompleted,
                        completedAlertDialog
                    )
                    status.text = "وضعیت: لغو شده"
                }

                btnYes.setOnClickListener {
                    val completedToHelp: CompletedToHelp by inject()
                    completedToHelp.completed(
                        AddToHelpBodyModel(
                            "update",
                            item.toHelp.id,
                            item.toHelp.parentId,
                            item.toHelp.title,
                            item.toHelp.shortDescription,
                            item.toHelp.description,
                            item.toHelp.category,
                            item.toHelp.date,
                            "completed",
                            item.images
                        ),
                        btnCompleted,
                        completedAlertDialog
                    )
                    status.text = "وضعیت: تکمیل شده"
                }
            }

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
        return ViewHolder(LayoutInflater.from(MainActivity.mContext).inflate(R.layout.item_edit_layout, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binder(list[position])
    }

    override fun getItemCount(): Int = list.count()
}