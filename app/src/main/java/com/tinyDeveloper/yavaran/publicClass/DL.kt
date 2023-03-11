package com.tinyDeveloper.yavaran.publicClass

import com.tinyDeveloper.yavaran.api.ApiServices
import org.koin.dsl.module.module

val getAuthenticationCode = module {
    single {
        GetAuthenticationCode()
    }
}

val public = module {
    single {
        ChangeFragment()
    }
}

val checkCode = module {
    single {
        CheckAuthenticationCode()
    }
}

val onClickBNV = module {
    single {
        OnClickBNV()
    }
}

val converter = module {
    single {
        Converter()
    }
}

val registerUser = module {
    single {
        RegisterUser()
    }
}

val camera = module {
    single {
        Camera()
    }
}

val gallery = module {
    single {
        Gallery()
    }
}

val accountUpdate = module {
    single {
        AccountUpdate()
    }
}

val picassoCreator = module {
    single {
        PicassoCreator()
    }
}

val getCategories = module {
    single {
        GetCategories()
    }
}

val addToGetHelp = module {
    single {
        AddToGetHelp()
    }
}

val addToGetHelpSetImages = module {
    single {
        AddToGetHelpSetImages()
    }
}

val addToHelpSetImages = module {
    single {
        AddToHelpSetImages()
    }
}

val getStatusTitle = module {
    single {
        GetStatusTitle()
    }
}

val getCategoryTitle = module {
    single {
        GetCategoryTitle()
    }
}


val getAllToHelps = module {
    single {
        Gives()
    }
}

val addToHelp = module {
    single {
        AddToHelp()
    }
}

val showToGetHelpByCategory = module {
    single {
        ShowToGetHelpByCategory()
    }
}

val showToHelpByCategory = module {
    single {
        ShowToHelpByCategory()
    }
}

val imageConverter = module {
    single {
        ImageConverter()
    }
}

val updateToGetHelp = module {
    single {
        UpdateToGetHelp()
    }
}

val updateToHelp = module {
    single {
        UpdateToHelp()
    }
}

val completedToGetHelp = module {
    single {
        CompletedToGetHelp()
    }
}

val completedToHelp = module {
    single {
        CompletedToHelp()
    }
}

val showInternetErrorAlert = module {
    single {
        ShowInternetErrorAlert()
    }
}

val network = module {
    single {
        Network()
    }
}

val showDate = module {
    single {
        ShowDate()
    }
}

val apiServices = module {
    single {
        ApiServices()
    }
}

val showDisableAppAlert = module {
    single {
        ShowDisableAppAlert()
    }
}

val showMessageAlert = module {
    single {
        ShowMessageAlert()
    }
}

val showUpdateAlert = module {
    single {
        ShowUpdateAlert()
    }
}

val help = module {
    single {
        Help()
    }
}