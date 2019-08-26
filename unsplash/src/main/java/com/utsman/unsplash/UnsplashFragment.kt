package com.utsman.unsplash

import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import com.utsman.core.ConnectivityViewModel
import com.utsman.core.load
import com.utsman.core.logi
import com.utsman.core.toast
import com.utsman.recycling.paged.extentions.LoaderIdentifierId
import com.utsman.recycling.paged.extentions.NetworkState
import com.utsman.recycling.paged.setupAdapterPaged
import com.utsman.unsplash.viewmodel.UnsplashViewModel
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.fragment_unsplash.*

class UnsplashFragment : Fragment() {

    private val unsplashViewModel by lazy {
        ViewModelProviders.of(this)[UnsplashViewModel::class.java]
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_unsplash, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val identifierId = LoaderIdentifierId.Builder()
            .setLoaderRes(R.layout.item_loader)
            .setIdProgressLoader(R.id.progress_circular)
            .setIdTextViewError(R.id.error_text_view)
            .build()

        recycler_view_unsplash.setupAdapterPaged<Unsplash>(R.layout.item_image, identifierId) {
            bind {
                val imgView = itemView.findViewById<ImageView>(com.utsman.core.R.id.image_view_item)
                imgView.load(item?.urls?.small)
                itemView.setOnClickListener {
                    context.toast(item?.links?.html + " " + item?.unsplashId.toString())
                }
            }

            unsplashViewModel.getPhoto().observe(context as LifecycleOwner, Observer {
                submitList(it)
            })

            unsplashViewModel.getLoader()?.observe(context as LifecycleOwner, Observer {
                submitNetwork(it)
                //networkState.postValue(it)
                //logi("dari network")
            })

        }

    }

}