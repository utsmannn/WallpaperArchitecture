package com.utsman.wallpaper

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.viewpager.widget.ViewPager
import com.google.android.material.snackbar.Snackbar
import com.utsman.core.ConnectivityViewModel
import com.utsman.pexel.PexelFragment
import com.utsman.unsplash.UnsplashFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val connectivityViewModel by lazy {
        ViewModelProviders.of(this)[ConnectivityViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        toolbar.title = "Unsplash"

        val fragmentUnsplash = UnsplashFragment()
        val fragmentPexel = PexelFragment()

        val pagerAdapter = MainPagerAdapter(supportFragmentManager)
        pagerAdapter.addFragment(fragmentUnsplash, fragmentPexel)
        main_view_pager.adapter = pagerAdapter

        main_bottom_nav.setOnNavigationItemSelectedListener {menuItem ->
            when (menuItem.itemId) {
                R.id.action_unsplash -> {
                    setupPager(0, "Unsplash")
                    true
                }
                R.id.action_pexel -> {
                    setupPager(1, "Pexel")
                    true
                }
                else -> false
            }
        }

        main_view_pager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {

            }

            override fun onPageSelected(position: Int) {
                main_bottom_nav.selectedItemId = when (position) {
                    0 -> R.id.action_unsplash
                    1 -> R.id.action_pexel
                    else -> 0
                }
            }

        })

    }

    override fun onStart() {
        super.onStart()
        connectivityViewModel.getConnectivity().observe(this, Observer { isConnected ->
            Snackbar.make(main_layout, "Network connecting status: $isConnected", Snackbar.LENGTH_SHORT).show()
        })
    }

    private fun setupPager(position: Int, title: String) {
        main_view_pager.setCurrentItem(position, true)
        toolbar.title = title
    }
}

class MainPagerAdapter(fragmentManager: FragmentManager) : FragmentPagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    private val listFragment: MutableList<Fragment> = mutableListOf()

    override fun getItem(position: Int): Fragment = listFragment[position]

    override fun getCount(): Int = listFragment.size

    fun addFragment(vararg fragments: Fragment) {
        this.listFragment.addAll(fragments)
    }
}