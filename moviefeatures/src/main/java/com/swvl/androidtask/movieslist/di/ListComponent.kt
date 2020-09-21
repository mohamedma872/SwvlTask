package com.swvl.androidtask.movieslist.di

import android.content.Context
import androidx.room.Room
import com.egabi.core.constants.Constants
import com.egabi.core.di.CoreComponent
import com.egabi.core.networking.Scheduler
import com.squareup.picasso.Picasso
import com.swvl.androidtask.commons.data.local.MoviesDb
import com.swvl.androidtask.commons.data.remote.FlickerService
import com.swvl.androidtask.movieslist.adapter.MoviesAdapter
import com.swvl.androidtask.movieslist.adapter.PicturesAdapter
import com.swvl.androidtask.movieslist.model.ListDataContract
import com.swvl.androidtask.movieslist.model.ListLocalData
import com.swvl.androidtask.movieslist.model.ListRemoteData
import com.swvl.androidtask.movieslist.model.ListRepository
import com.swvl.androidtask.movieslist.view.MovieDetailsActivity
import com.swvl.androidtask.movieslist.view.MoviesActivity
import com.swvl.androidtask.movieslist.viewmodel.ListViewModelFactory
import dagger.Component
import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable
import retrofit2.Retrofit
import java.util.*

@ListScope
@Component(dependencies = [CoreComponent::class], modules = [ListModule::class])
interface ListComponent {

    //Expose to dependent components
    fun postDb(): MoviesDb
    fun picasso(): Picasso
    fun scheduler(): Scheduler

    fun inject(listActivity: MoviesActivity)
    fun inject(activity: MovieDetailsActivity)
}

@Module
@ListScope
class ListModule {

    /*Adapter*/
    @Provides
    @ListScope
    fun adapter(): MoviesAdapter = MoviesAdapter()

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
        compositeDisposable: CompositeDisposable,
        remote: ListDataContract.Remote
    ): ListDataContract.Repository = ListRepository(local, scheduler, compositeDisposable, remote)

    @Provides
    @ListScope
    fun provideremoteData(coreService: FlickerService): ListDataContract.Remote =
        ListRemoteData(coreService)

    @Provides
    @ListScope
    fun localData(
        postDb: MoviesDb,
        scheduler: Scheduler,
        context: Context
    ): ListDataContract.Local = ListLocalData(postDb, scheduler, context)

    @Provides
    @ListScope
    fun provideAdapter(context: Context): PicturesAdapter = PicturesAdapter(ArrayList(), context)

    @Provides
    @ListScope
    fun compositeDisposable(): CompositeDisposable = CompositeDisposable()

    /*Parent providers to dependents*/
    @Provides
    @ListScope
    fun postDb(context: Context): MoviesDb =
        Room.databaseBuilder(context, MoviesDb::class.java, Constants.Movies.DB_NAME)
            .allowMainThreadQueries().build()

    @Provides
    @ListScope
    fun provideService(retrofit: Retrofit): FlickerService =
        retrofit.create(FlickerService::class.java)
}