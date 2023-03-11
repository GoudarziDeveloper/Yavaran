package com.tinyDeveloper.yavaran

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.tinyDeveloper.yavaran.api.apiModels.categories.Category
import com.tinyDeveloper.yavaran.api.apiModels.toGetHelp.ToGetHelp
import com.tinyDeveloper.yavaran.databinding.ActivityMainBinding
import com.tinyDeveloper.yavaran.fragments.*
import com.tinyDeveloper.yavaran.publicClass.*
import de.hdodenhof.circleimageview.CircleImageView
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity() {
    val ACCOUNT_IMAGE_REQUEST_CODE = 1000
    val REGISTER_IMAGE_REQUEST_CODE = 1001
    val TO_GET_HELP_IMAGE_REQUEST_CODE = 1002
    val TO_HELP_IMAGE_REQUEST_CODE = 1003
    val PICK_IMAGE_PERMITION_CODE = 1001
    val TAKE_IMAGE_PERMITION_CODE = 1000
    lateinit var imageUri: Uri
    lateinit var binding : ActivityMainBinding
    lateinit var info: SharedPreferences
    var phone: String? = null
    var categoriesList = mutableListOf<Category>()
    //val baseDownloadUrl = "https://mgoudarzi.ir/help/uploads/"
    var baseUrl = "https://mgoudarzi.ir/"
    //var baseUrl = "http://192.168.1.100/"
    lateinit var baseDownloadUrl: String
    lateinit var usersUrl: String
    lateinit var toGetHelpUrl: String
    lateinit var toHelpUrl: String
    lateinit var categories: String
    lateinit var sendCodeUrl: String
    lateinit var checkCodeUrl: String
    lateinit var categoriesUrl: String
    private var currentTime = 0L
    private val leftTime = 2000
    companion object{
        lateinit var mContext:MainActivity
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //mContext = applicationContext
        mContext = this
        info = getSharedPreferences("userInfo", Context.MODE_PRIVATE)
        phone = info.getString("mobile", null)

        binding.includeToolbar.toolbarBack.visibility = View.INVISIBLE
        window.decorView.layoutDirection = View.LAYOUT_DIRECTION_RTL
        window.statusBarColor = ContextCompat.getColor(this, R.color.colorBackground)
        window.navigationBarColor = ContextCompat.getColor(this, R.color.colorBackground)
        binding.includeToolbar.toolbarMain.visibility = View.INVISIBLE
        binding.bottomNavigationViewMain.visibility = View.INVISIBLE
        val public: ChangeFragment by inject()
        public.newArgumentsFragment(SplashScreenFragment(), true)
        val onClickBNV : OnClickBNV by inject()
        onClickBNV.onBottomNavigationClick()
        binding.includeToolbar.toolbarBack.setOnClickListener {
            supportFragmentManager.popBackStack()
        }
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount == 0){
            if (currentTime + leftTime > System.currentTimeMillis()){
                this.finishAffinity()
            }else{
                currentTime = System.currentTimeMillis()
                Toast.makeText(this, "برای خروج دوبار دکمه برگشت را بفشارید!", Toast.LENGTH_LONG).show()
            }
        }
        supportFragmentManager.popBackStack()
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK){
            val addToGetHelpSetImages : AddToGetHelpSetImages by inject()
            val addToHelpSetImages : AddToHelpSetImages by inject()
            if (data?.data != null){
                when(requestCode){
                    ACCOUNT_IMAGE_REQUEST_CODE -> findViewById<CircleImageView>(R.id.accountImage).setImageURI(data.data)
                    REGISTER_IMAGE_REQUEST_CODE-> findViewById<CircleImageView>(R.id.circleImageView).setImageURI(data.data)
                    TO_GET_HELP_IMAGE_REQUEST_CODE -> addToGetHelpSetImages.setToGetHelpImage(data.data!!)
                    TO_HELP_IMAGE_REQUEST_CODE -> addToHelpSetImages.setToHelpImage(data.data!!)
                }
            }else{
                when(requestCode){
                    ACCOUNT_IMAGE_REQUEST_CODE -> findViewById<CircleImageView>(R.id.accountImage).setImageURI(imageUri)
                    REGISTER_IMAGE_REQUEST_CODE -> findViewById<CircleImageView>(R.id.circleImageView).setImageURI(imageUri)
                    TO_GET_HELP_IMAGE_REQUEST_CODE -> addToGetHelpSetImages.setToGetHelpImage(imageUri)
                    TO_HELP_IMAGE_REQUEST_CODE -> addToHelpSetImages.setToHelpImage(imageUri)
                }
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode){
            TAKE_IMAGE_PERMITION_CODE -> {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    val camera : Camera by inject()
                    when(requestCode){
                        ACCOUNT_IMAGE_REQUEST_CODE -> camera.openCamera(ACCOUNT_IMAGE_REQUEST_CODE)
                        REGISTER_IMAGE_REQUEST_CODE-> camera.openCamera(REGISTER_IMAGE_REQUEST_CODE)
                        TO_GET_HELP_IMAGE_REQUEST_CODE -> camera.openCamera(TO_GET_HELP_IMAGE_REQUEST_CODE)
                        TO_HELP_IMAGE_REQUEST_CODE -> camera.openCamera(TO_HELP_IMAGE_REQUEST_CODE)
                    }
                }
            }
            PICK_IMAGE_PERMITION_CODE -> {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    val gallery : Gallery by inject()
                    when(requestCode){
                        ACCOUNT_IMAGE_REQUEST_CODE -> gallery.pickImage(ACCOUNT_IMAGE_REQUEST_CODE)
                        REGISTER_IMAGE_REQUEST_CODE-> gallery.pickImage(REGISTER_IMAGE_REQUEST_CODE)
                        TO_GET_HELP_IMAGE_REQUEST_CODE -> gallery.pickImage(TO_GET_HELP_IMAGE_REQUEST_CODE)
                        TO_HELP_IMAGE_REQUEST_CODE -> gallery.pickImage(TO_HELP_IMAGE_REQUEST_CODE)
                    }
                }
            }
        }
    }
}