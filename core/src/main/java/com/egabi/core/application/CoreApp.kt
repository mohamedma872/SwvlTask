package com.egabi.core.application

import android.app.Application
import android.content.Context
import android.content.res.Configuration
import com.egabi.core.constants.LocaleHelper
import com.egabi.core.constants.LocaleManager
import com.egabi.core.di.AppModule
import com.egabi.core.di.CoreComponent
import com.egabi.core.di.DaggerCoreComponent


open class CoreApp : Application() {

    companion object {
        lateinit var coreComponent: CoreComponent
    }

    override fun onCreate() {
        super.onCreate()
//        initSynk()
        initDI()
        initStetho()
    }

//    private fun initSynk() {
//        var change = ""
//        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
//        val language = sharedPreferences.getString("language", "EN")
//
//          if (language=="EN" ) {
//            change = "en"
//            Constants.language="EN"
//        }else {
//            change ="ar"
//            Constants.language="AR"
//        }
//
////        BaseActivity.dLocale = Locale(change) //set any locale you want here
//    }
//protected override fun attachBaseContext(base: Context?): Unit {
//    super.attachBaseContext(LocaleHelper.setLocale(base!!,"en"))
//}

//    override fun onConfigurationChanged(newConfig: Configuration?) {
//        super.onConfigurationChanged(newConfig)
//        LocaleHelper.setLocale(this,"en")
//    }
    private fun initStetho() {
//        if (BuildConfig.DEBUG)
//            Stetho.initializeWithDefaults(this)
    }

    private fun initDI() {
        coreComponent = DaggerCoreComponent.builder().appModule(AppModule(this)).build()
    }
}