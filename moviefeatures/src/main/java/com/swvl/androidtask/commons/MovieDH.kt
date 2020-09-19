package com.swvl.androidtask.commons

import com.egabi.core.application.CoreApp
import com.swvl.androidtask.movieslist.di.DaggerListComponent
import com.swvl.androidtask.movieslist.di.ListComponent
import javax.inject.Singleton

@Singleton
object MovieDH {
    private var listComponent: ListComponent? = null

    // private var detailsComponent: DetailsComponent? = null
//
    fun listComponent(): ListComponent {
        if (listComponent == null)
            listComponent =
                DaggerListComponent.builder().coreComponent(CoreApp.coreComponent).build()
        return listComponent as ListComponent
    }

    fun destroyListComponent() {
        listComponent = null
    }

//    fun detailsComponent(): DetailsComponent {
//        if (detailsComponent == null)
//            detailsComponent = DaggerDetailsComponent.builder().listComponent(listComponent()).build()
//        return detailsComponent as DetailsComponent
//    }
//
//    fun destroyDetailsComponent() {
//        detailsComponent = null
//    }
}