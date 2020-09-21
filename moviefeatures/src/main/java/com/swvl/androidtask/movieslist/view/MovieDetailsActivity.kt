package com.swvl.androidtask.movieslist.view


import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.egabi.core.application.BaseActivity
import com.egabi.core.networking.Outcome
import com.google.gson.Gson
import com.swvl.androidtask.R
import com.swvl.androidtask.commons.MovieDH
import com.swvl.androidtask.commons.data.local.Movie
import com.swvl.androidtask.movieslist.adapter.PicturesAdapter
import com.swvl.androidtask.movieslist.viewmodel.ListViewModel
import com.swvl.androidtask.movieslist.viewmodel.ListViewModelFactory
import kotlinx.android.synthetic.main.detail_activity.*
import kotlinx.android.synthetic.main.item_movies_list.*
import java.io.IOException
import javax.inject.Inject

class MovieDetailsActivity : BaseActivity() {

    private val component by lazy { MovieDH.listComponent() }

    @Inject
    lateinit var viewModelFactory: ListViewModelFactory

    @Inject
    lateinit var adapter: PicturesAdapter

    private val viewModel: ListViewModel by lazy {
        ViewModelProvider(this, viewModelFactory).get(ListViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.detail_activity)
        component.inject(this)
        initRescylceView()
        initiateDataListener()
        intent.getStringExtra("movie")?.run {
            val movieobj = Gson().fromJson(this, Movie::class.java)
            tvTitle.text = movieobj.title
            tvYear.text = movieobj.year.toString()
            tvCast.text = movieobj.cast.joinToString()
            tvGenre.text = movieobj.genres.joinToString()
            tvrating.text = movieobj.rating.toFloat().toString()
            viewModel.searchForPohotos(movieobj.title)
        }
    }


    private fun initiateDataListener() {
        //Observe the outcome and update state of the screen  accordingly
        viewModel.picturesOutcome.observe(this, { outcome ->
            // Log.d(TAG, "initiateDataListener: $outcome")
            when (outcome) {

                is Outcome.Progress -> outcome.loading
                is Outcome.Success -> {
                    // Log.d(TAG, "initiateDataListener: Successfully loaded data")
                    adapter.addPhotos(outcome.data)
                }

                is Outcome.Failure -> {

                    if (outcome.e is IOException)
                        Toast.makeText(
                            this,
                            outcome.e.localizedMessage,
                            Toast.LENGTH_LONG
                        ).show()
                    else
                        Toast.makeText(
                            this,
                            "try again later",
                            Toast.LENGTH_LONG
                        ).show()
                }

            }
        })

    }

    private fun initRescylceView() {
        rvpictures.adapter = adapter
    }


}
