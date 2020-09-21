package com.swvl.androidtask.movieslist.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.egabi.core.application.BaseActivity
import com.egabi.core.networking.Outcome
import com.google.gson.Gson
import com.swvl.androidtask.R
import com.swvl.androidtask.commons.MovieDH
import com.swvl.androidtask.commons.data.local.Movie
import com.swvl.androidtask.movieslist.adapter.MoviesAdapter
import com.swvl.androidtask.movieslist.viewmodel.ListViewModel
import com.swvl.androidtask.movieslist.viewmodel.ListViewModelFactory
import kotlinx.android.synthetic.main.movies_activity.*
import java.io.IOException
import javax.inject.Inject

class MoviesActivity : BaseActivity(), MoviesAdapter.Interaction {

    private val component by lazy { MovieDH.listComponent() }

    @Inject
    lateinit var viewModelFactory: ListViewModelFactory

    @Inject
    lateinit var adapter: MoviesAdapter

    private val viewModel: ListViewModel by lazy {
        ViewModelProvider(this, viewModelFactory).get(ListViewModel::class.java)
    }

    private val context: Context by lazy { this }

    private val TAG = "ListActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.movies_activity)
        component.inject(this)
        adapter.interaction = this
        rv.adapter = adapter
        viewModel.getMoviesList()
        initiateDataListener()
        search_et.setOnEditorActionListener(TextView.OnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                viewModel.searchForMovies(search_et.text.toString()).let {
                    return@OnEditorActionListener true
                }
            }
            false
        })
    }

    private fun initiateDataListener() {
        //Observe the outcome and update state of the screen  accordingly
        viewModel.moviesOutcome.observe(this, { outcome ->
            Log.d(TAG, "initiateDataListener: $outcome")
            when (outcome) {

                is Outcome.Progress -> outcome.loading
                is Outcome.Success -> {
                    Log.d(TAG, "initiateDataListener: Successfully loaded data")
                    adapter.swapData(outcome.data)
                }

                is Outcome.Failure -> {

                    if (outcome.e is IOException)
                        Toast.makeText(
                            context,
                            outcome.e.localizedMessage,
                            Toast.LENGTH_LONG
                        ).show()
                    else
                        Toast.makeText(
                            context,
                            "try again later",
                            Toast.LENGTH_LONG
                        ).show()
                }

            }
        })
    }

    override fun postClicked(movieObject: Movie) {
        startActivity(
            Intent(context, MovieDetailsActivity::class.java).putExtra(
                "movie",
                Gson().toJson(movieObject)
            )
        )
    }


}
