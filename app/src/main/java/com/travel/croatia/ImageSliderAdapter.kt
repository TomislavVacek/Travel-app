package com.travel.croatia

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.viewpager.widget.PagerAdapter


class ImageSliderAdapter(private val context: Context, private val imageIds: Array<Int>) :
    PagerAdapter() {        //definira klasu ImageSliderAdapter, koja nasljeđuje klasu PagerAdapter
    override fun getCount(): Int { //vraća broj slika koje se prikazuju u ViewPageru
        return imageIds.size
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {  //poziva kada se prikazuje nova stranica ViewPagera. Ova metoda stvara novi View objekt koji se dodaje u container
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.item_image_slider, container, false) // View objekt se stvara pomoću LayoutInflater-a iz XML datoteke,,item_image_slider.xml
        val imageView = view.findViewById<ImageView>(R.id.imageView); //postavlja sliku
        Log.d("TAG", "instantiateItem: " + imageIds[position])
        imageView.setImageResource(imageIds[position])
        imageView.setImageResource(imageIds[position])
        container.addView(view)
        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) { //poziva kada se stranica uklanja iz ViewPagera. Ova metoda uklanja View objekt iz container-a.
        container.removeView(`object` as View)
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean { // provjerava da li je View objekt stvoren u instantiateItem() metodi jednak objektu koji se trenutno prikazuje u ViewPageru.
        return view === `object`
    }
}
