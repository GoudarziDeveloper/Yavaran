package com.tinyDeveloper.yavaran.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.tinyDeveloper.yavaran.MainActivity
import com.tinyDeveloper.yavaran.adapter.CategoriesAdapter
import com.tinyDeveloper.yavaran.api.apiModels.toHelp.GetAllToHelpBodyModel
import com.tinyDeveloper.yavaran.dataModels.KeyValues
import com.tinyDeveloper.yavaran.databinding.GivesFragmentBinding
import com.tinyDeveloper.yavaran.publicClass.Gives
import com.tinyDeveloper.yavaran.publicClass.ChangeFragment
import org.koin.android.ext.android.inject

class GivesFragment: Fragment() {
    private var _bindGives : GivesFragmentBinding? = null
    private val bindGives get() = _bindGives!!
    private lateinit var type:String
    private var isBack = false
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        if (_bindGives == null){
            _bindGives = GivesFragmentBinding.inflate(layoutInflater, container, false)
            isBack = false
        }else
            isBack = true
        return bindGives.root
    }

    @SuppressLint("WrongConstant")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (arguments != null){
            type = requireArguments().getString("type", "-1")
            if (type == "getAll"){
                if (MainActivity.mContext.categoriesList.count() > 0){
                    val adapter = CategoriesAdapter(MainActivity.mContext.categoriesList, false)
                    bindGives.givesCategoriesList.layoutManager =
                        GridLayoutManager(context,1 , LinearLayout.HORIZONTAL, false)
                    bindGives.givesCategoriesList.adapter = adapter
                }

                bindGives.giveAdd.setOnClickListener{
                    val public: ChangeFragment by inject()
                    public.newArgumentsFragment(
                        AddToHelpFragment(),
                        title = "افزودن هدیه",
                        isBack = true,
                        listArguments = listOf(
                            KeyValues("type", "insert"),
                            KeyValues("from", "gives")
                        )
                    )
                }
                bindGives.givesSwipeContainer.setOnRefreshListener {
                    val getAllToHelps: Gives by inject()
                    getAllToHelps.getAllToHelps(
                        GetAllToHelpBodyModel(
                            "getAll",
                            null,
                            10,
                            0
                        ),
                        bindGives,
                        true
                    )
                    bindGives.givesSwipeContainer.isRefreshing = false
                }
                if (!isBack){
                    val getAllToHelps: Gives by inject()
                    getAllToHelps.getAllToHelps(
                        GetAllToHelpBodyModel(
                            "getAll",
                            null,
                            10,
                            0
                        ),
                        bindGives
                    )
                }
            }else if (type == "findUserToHelps"){
                bindGives.givesCategoriesList.visibility = View.GONE
                bindGives.giveAdd.visibility = View.GONE

                val getAllToHelps: Gives by inject()
                getAllToHelps.getAllToHelps(
                    GetAllToHelpBodyModel(
                        "findUserToHelps",
                        MainActivity.mContext.phone
                    ),
                    bindGives
                )
            }
        }
    }

    override fun onDestroy() {
        if (type == "findUserToHelps"){
            MainActivity.mContext.binding.includeToolbar.toolbarBack.visibility = View.INVISIBLE
            MainActivity.mContext.binding.includeToolbar.toolbarTitle.text = "وضعیت من"
        }
        super.onDestroy()
    }
}