package com.egabi.core.application

import android.app.Application
import com.egabi.core.di.AppModule
import com.egabi.core.di.CoreComponent
import com.egabi.core.di.DaggerCoreComponent


open class CoreApp : Application() {

    companion object {
        lateinit var coreComponent: CoreComponent
    }

    override fun onCreate() {
        super.onCreate()
        initDI()
    }


    private fun initDI() {
        coreComponent = DaggerCoreComponent.builder().appModule(AppModule(this)).build()
    }
}