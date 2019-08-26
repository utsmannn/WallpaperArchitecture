package com.utsman.pexel

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.utsman.core.load
import com.utsman.core.toast
import com.utsman.pexel.viewmodel.PexelViewModel
import com.utsman.recycling.paged.extentions.LoaderIdentifierId
import com.utsman.recycling.paged.setupAdapterPaged
import kotlinx.android.synthetic.main.fragment_pexel.*

class PexelFragment : Fragment() {

    private val pexelViewModel by lazy {
        ViewModelProviders.of(this)[PexelViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_pexel, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val identifierId = LoaderIdentifierId.Builder()
            .setLoaderRes(R.layout.item_loader)
            .setIdProgressLoader(R.id.progress_circular)
            .setIdTextViewError(R.id.error_text_view)
            .build()

        recycler_view_pexel.setupAdapterPaged<Pexel>(R.layout.item_image, identifierId) {

            val gridLayoutManager = GridLayoutManager(context, 2)
            setLayoutManager(gridLayoutManager)
            fixGridSpan(2)

            bind {
                val imgView = itemView.findViewById<ImageView>(com.utsman.core.R.id.image_view_item)
                imgView.load(item?.src?.medium)
                itemView.setOnClickListener {
                    context.toast(item?.url + " " + item?.id.toString())
                }
            }

            pexelViewModel.getPhoto().observe(context as LifecycleOwner, Observer {
                submitList(it)
            })

            pexelViewModel.getLoader().observe(context as LifecycleOwner, Observer {
                submitNetwork(it)
            })

        }
    }
}