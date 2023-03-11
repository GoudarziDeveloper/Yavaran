package com.tinyDeveloper.yavaran.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.tinyDeveloper.yavaran.MainActivity
import com.tinyDeveloper.yavaran.R
import com.tinyDeveloper.yavaran.api.ValidateDataListener
import com.tinyDeveloper.yavaran.api.apiModels.users.UpdateAndDeleteUserModel
import com.tinyDeveloper.yavaran.api.apiModels.users.UserIdAndPhoneModel
import com.tinyDeveloper.yavaran.api.apiModels.users.UserModel
import com.tinyDeveloper.yavaran.api.presenters.users.DeleteUserPresenter
import com.tinyDeveloper.yavaran.dataModels.KeyValues
import com.tinyDeveloper.yavaran.fragments.AccountFragment
import com.tinyDeveloper.yavaran.fragments.HelpFragment
import com.tinyDeveloper.yavaran.publicClass.PicassoCreator
import com.tinyDeveloper.yavaran.publicClass.ChangeFragment
import com.tinyDeveloper.yavaran.publicClass.ShowDate
import de.hdodenhof.circleimageview.CircleImageView
import org.koin.standalone.KoinComponent
import org.koin.standalone.inject

class UsersAdapter(var list:List<UserModel>):RecyclerView.Adapter<UsersAdapter.ViewHolder>(), KoinComponent {
    inner class ViewHolder(val view: View):RecyclerView.ViewHolder(view){
        private val image: CircleImageView = view.findViewById(R.id.usersImage)
        private val title: TextView = view.findViewById(R.id.usersTitle)
        private val tel: TextView = view.findViewById(R.id.usersTel)
        private val data: TextView = view.findViewById(R.id.usersDate)
        private val status: TextView = view.findViewById(R.id.usersStatus)
        private val btnDelete: ImageButton = view.findViewById(R.id.usersDelete)
        private val btnEdit: ImageButton = view.findViewById(R.id.usersEdit)
        @SuppressLint("SetTextI18n")
        fun binder(item:UserModel){
            val picasso: PicassoCreator by inject()
            val changeFragment: ChangeFragment by inject()
            val showDate: ShowDate by inject()
            if (item.image != null)
                picasso.getImage(MainActivity.mContext.baseDownloadUrl + item.image, null, image)
            else
                image.setImageDrawable(ContextCompat.getDrawable(MainActivity.mContext, R.drawable.user_image))
            title.text = "${item.firstName} ${item.lastName}"
            tel.text = "تلفن: ${item.phone}"
            data.text = "تاریخ ثبت: ${showDate.showHumanDate(item.date?.toLong()?: 0)}"
            status.text = "وضعیت: ${item.neededLevel}"
            if (item.phone != null){
                view.setOnClickListener {
                    changeFragment.newArgumentsFragment(
                        HelpFragment(),
                        false,
                        title = "آگهی های این نیازمند",
                        isBack = true,
                        listArguments = listOf(
                            KeyValues("type", "findNeededToGetHelps"),
                            KeyValues("userId", item.id.toString())
                        ))
                }
                btnEdit.setOnClickListener {
                    changeFragment.newArgumentsFragment(
                        AccountFragment(),
                        isReplace = false,
                        isBack = true,
                        title = "ویرایش نیازمند",
                        listArguments = listOf(
                            KeyValues("type", "updateOther"),
                            KeyValues("mobile", item.phone),
                            KeyValues("from", "needed")
                        )
                    )
                }
                btnDelete.setOnClickListener {
                    delete(
                        UserIdAndPhoneModel(
                            "delete",
                            item.id.toString()
                        ),
                        adapterPosition
                    )
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(MainActivity.mContext).inflate(R.layout.users_layout, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binder(list[position])
    }

    override fun getItemCount(): Int = list.count()

    fun delete(info:UserIdAndPhoneModel, position: Int){
        DeleteUserPresenter(object : ValidateDataListener<UpdateAndDeleteUserModel> {
            override fun onSuccess(data: UpdateAndDeleteUserModel) {
                if (data.success){
                    Toast.makeText(MainActivity.mContext, "نیازمند پاک شد", Toast.LENGTH_SHORT).show()
                    list = remove(list , position)
                    notifyItemRemoved(position)
                    notifyItemRangeChanged(position , itemCount)
                }else{
                    Toast.makeText(MainActivity.mContext, "مشکلی پیش امده", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onInvalidate(isValidate: Boolean) {
                if (!isValidate){
                    Toast.makeText(MainActivity.mContext, "سرور پاسخگو نبود", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onError(error: Throwable) {

            }
        }).userData(info)
    }

    fun remove(listMain: List<UserModel>, position: Int) : List<UserModel>{
        val list = mutableListOf<UserModel>()
        for (item in listMain){
            if (item != listMain[position])
                list += item
        }
        return list
    }
}