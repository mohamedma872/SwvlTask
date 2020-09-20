package com.swvl.androidtask.movieslist.di

import android.content.Context
import androidx.room.Room
import com.egabi.core.constants.Constants
import com.egabi.core.di.CoreComponent
import com.egabi.core.networking.Scheduler
import com.squareup.picasso.Picasso
import com.swvl.androidtask.commons.data.local.MoviesDb
import com.swvl.androidtask.movieslist.adapter.MoviesAdapter
import com.swvl.androidtask.movieslist.model.ListDataContract
import com.swvl.androidtask.movieslist.model.ListLocalData
import com.swvl.androidtask.movieslist.model.ListRepository
import com.swvl.androidtask.movieslist.view.MoviesActivity
import com.swvl.androidtask.movieslist.viewmodel.ListViewModelFactory
import dagger.Component
import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable

@ListScope
@Component(dependencies = [CoreComponent::class], modules = [ListModule::class])
interface ListComponent {

    //Expose to dependent components
    fun postDb(): MoviesDb
    fun picasso(): Picasso
    fun scheduler(): Scheduler

    fun inject(listActivity: MoviesActivity)
}

@Module
@ListScope
class ListModule {

    /*Adapter*/
    @Provides
    @ListScope
    fun adapter(picasso: Picasso): MoviesAdapter = MoviesAdapter()

    /*ViewModel*/
    @Provides
    @ListScope
    fun listViewModelFactory(
        repository: ListDataContract.Repository,
        compositeDisposable: CompositeDisposable
    ): ListViewModelFactory = ListViewModelFactory(repository, compositeDisposable)

    /*Repository*/
    @Provides
    @ListScope
    fun listRepo(
        local: ListDataContract.Local,
        scheduler: Scheduler,
        compositeDisposable: CompositeDisposable
    ): ListDataContract.Repository = ListRepository(local, scheduler, compositeDisposable)


    @Provides
    @ListScope
    fun localData(
        postDb: MoviesDb,
        scheduler: Scheduler,
        context: Context
    ): ListDataContract.Local = ListLocalData(postDb, scheduler, context)

    @Provides
    @ListScope
    fun compositeDisposable(): CompositeDisposable = CompositeDisposable()

    /*Parent providers to dependents*/
    @Provides
    @ListScope
    fun postDb(context: Context): MoviesDb =
        Room.databaseBuilder(context, MoviesDb::class.java, Constants.Movies.DB_NAME)
            .allowMainThreadQueries().build()


}