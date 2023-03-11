package com.tinyDeveloper.yavaran.publicClass

import android.view.View
import com.tinyDeveloper.yavaran.MainActivity
import com.tinyDeveloper.yavaran.R
import com.tinyDeveloper.yavaran.dataModels.KeyValues
import com.tinyDeveloper.yavaran.fragments.*
import org.koin.standalone.KoinComponent
import org.koin.standalone.inject

class OnClickBNV: KoinComponent {
    fun onBottomNavigationClick(){
        val public : ChangeFragment by inject()
        MainActivity.mContext.binding.bottomNavigationViewMain.setOnItemSelectedListener {
            MainActivity.mContext.binding.includeToolbar.toolbarBack.visibility = View.INVISIBLE
            when(it.itemId){
                R.id.home -> {
                    if (!it.isChecked){
                        public.newArgumentsFragment(HelpFragment(),
                            isReplace = false,
                            title = "درخواست ها",
                            listArguments = listOf(KeyValues("type", "help"))
                        )
                        return@setOnItemSelectedListener true
                    }
                }
                R.id.add -> {
                    if (!it.isChecked){
                        public.newArgumentsFragment(GivesFragment(),
                            isReplace = false,
                            title = "اهدایی ها",
                            listArguments = listOf(
                                KeyValues("type", "getAll")
                            )
                        )
                        return@setOnItemSelectedListener true
                    }
                }
                R.id.categories -> {
                    if (!it.isChecked){
                        public.newArgumentsFragment(NeededFragment(),
                            isReplace = true,
                            title = "افزودن نیازمند"
                        )
                        return@setOnItemSelectedListener true
                    }
                }
                R.id.account -> {
                    if (!it.isChecked){
                        public.newArgumentsFragment(HomeFragment(),
                            isReplace = true,
                            title = "وضعیت من",
                        )
                        return@setOnItemSelectedListener true
                    }
                }
            }
            return@setOnItemSelectedListener false
        }
    }
}