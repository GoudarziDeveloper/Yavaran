package com.tinyDeveloper.yavaran.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.tinyDeveloper.yavaran.MainActivity
import com.tinyDeveloper.yavaran.adapter.CategoriesAdapter
import com.tinyDeveloper.yavaran.adapter.ToGetHelpAdapter
import com.tinyDeveloper.yavaran.adapter.ToGetHelpEditAdapter
import com.tinyDeveloper.yavaran.api.ValidateDataListener
import com.tinyDeveloper.yavaran.api.apiModels.toGetHelp.GetAllToGetHelpBodyModel
import com.tinyDeveloper.yavaran.api.apiModels.toGetHelp.GetAllToGetHelpModel
import com.tinyDeveloper.yavaran.api.presenters.toGetHelp.GetAllToGetHelpPresenter
import com.tinyDeveloper.yavaran.dataModels.KeyValues
import com.tinyDeveloper.yavaran.databinding.HelpFragmentBinding
import com.tinyDeveloper.yavaran.publicClass.ChangeFragment
import org.koin.android.ext.android.inject
import androidx.recyclerview.widget.RecyclerView
import com.tinyDeveloper.yavaran.api.apiModels.toGetHelp.ToGetHelp
import com.tinyDeveloper.yavaran.publicClass.Help
import com.tinyDeveloper.yavaran.publicClass.help


class HelpFragment: Fragment() {
    private var _bindHelp : HelpFragmentBinding? = null
    private val bindHelp get() = _bindHelp!!
    private lateinit var type:String
    private var isBack = false
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        if (_bindHelp == null){
            _bindHelp = HelpFragmentBinding.inflate(layoutInflater, container, false)
            isBack = false
        }else
            isBack = true

        return bindHelp.root
    }

    @SuppressLint("WrongConstant")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        type = arguments?.getString("type", null)!!
        val help: Help by inject()
        if (type == "help"){
            MainActivity.mContext.binding.includeToolbar.toolbarMain.visibility = View.VISIBLE
            MainActivity.mContext.binding.bottomNavigationViewMain.visibility = View.VISIBLE
            if (MainActivity.mContext.categoriesList.count() > 0){
                val adapter = CategoriesAdapter(MainActivity.mContext.categoriesList)
                bindHelp.helpCategoriesList.layoutManager =
                    GridLayoutManager(context,1 , LinearLayout.HORIZONTAL, false)
                bindHelp.helpCategoriesList.adapter = adapter
            }
            bindHelp.helpSwipeContainer.setOnRefreshListener {
                help.getAllToGetHelps(GetAllToGetHelpBodyModel("getAll", null, 10, 0), bindHelp, type)
                bindHelp.helpSwipeContainer.isRefreshing = false
            }
            if (!isBack)
                help.getAllToGetHelps(GetAllToGetHelpBodyModel("getAll", null, 10, 0), bindHelp, type)
            bindHelp.helpAdd.setOnClickListener {
                val public: ChangeFragment by inject()
                public.newArgumentsFragment(
                    AddToGetHelpFragment(),
                    isBack = true,
                    title = "افزودن درخواست",
                    listArguments = listOf(
                        KeyValues("type", "insert"),
                        KeyValues("from", "help"),
                    )
                )
            }
        }else if (type == "findNeededToGetHelps"){
            bindHelp.helpCategoriesList.visibility = View.GONE
            val userId = arguments?.getString("userId", null)
            if (userId != null){
                help.getAllToGetHelps(GetAllToGetHelpBodyModel("findNeededToGetHelps", userId), bindHelp, type)

                bindHelp.helpAdd.setOnClickListener {
                    val public: ChangeFragment by inject()
                    public.newArgumentsFragment(
                        AddToGetHelpFragment(),
                        isBack = true,
                        title = "افزودن درخواست",
                        listArguments = listOf(
                            KeyValues("userId", userId),
                            KeyValues("type", "insert"),
                            KeyValues("from", "needed")
                        )
                    )
                }
            }
        }else if (type == "findUserToGetHelps"){
            bindHelp.helpCategoriesList.visibility = View.GONE
            bindHelp.helpAdd.visibility = View.GONE
            help.getAllToGetHelps(GetAllToGetHelpBodyModel("findUserToGetHelps", MainActivity.mContext.phone), bindHelp, type)
        }
    }

    override fun onDestroy() {
        if (type == "findNeededToGetHelps"){
            MainActivity.mContext.binding.includeToolbar.toolbarBack.visibility = View.INVISIBLE
            MainActivity.mContext.binding.includeToolbar.toolbarTitle.text = "نیازمندان"
        }else if (type == "findUserToGetHelps"){
            MainActivity.mContext.binding.includeToolbar.toolbarBack.visibility = View.INVISIBLE
            MainActivity.mContext.binding.includeToolbar.toolbarTitle.text = "وضعیت من"
        }
        super.onDestroy()
    }
}