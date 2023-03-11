package com.tinyDeveloper.yavaran

import android.app.Application
import com.tinyDeveloper.yavaran.publicClass.*
import org.koin.android.ext.android.startKoin

class App: Application() {
    override fun onCreate(){
        super.onCreate()
        startKoin(
            applicationContext,listOf(
            getAuthenticationCode,
            public,
            checkCode,
            onClickBNV,
            converter,
            registerUser,
            camera,
            gallery,
            accountUpdate,
            picassoCreator,
            getCategories,
            addToGetHelp,
            addToGetHelpSetImages,
            addToHelpSetImages,
            getStatusTitle,
            getCategoryTitle,
            getAllToHelps,
            addToHelp,
            showToGetHelpByCategory,
            showToHelpByCategory,
            imageConverter,
            updateToGetHelp,
            updateToHelp,
            completedToGetHelp,
            completedToHelp,
            showInternetErrorAlert,
            network,
            showDate,
            apiServices,
            showDisableAppAlert,
            showMessageAlert,
            showUpdateAlert,
            help
        ))
    }
}