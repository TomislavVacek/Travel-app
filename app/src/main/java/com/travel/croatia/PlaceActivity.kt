package com.travel.croatia

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.viewpager.widget.ViewPager
import com.travel.croatia.databinding.ActivityPlaceBinding


class PlaceActivity : AppCompatActivity() {
    lateinit var binding: ActivityPlaceBinding
    var placeName: String? = null
    var address: String? = null
    var `val` = 0
    private val dubrovnikID = arrayOf(R.drawable.dubrovnik,R.drawable.dubrovnik1,R.drawable.dubrovnik2,R.drawable.dubrovnik3,R.drawable.dubrovnik4)
    private val novaljaID = arrayOf(R.drawable.novalja,R.drawable.novalja1,R.drawable.novalja2,R.drawable.novalja3,R.drawable.novalja4)
    private val makarskaID = arrayOf(R.drawable.makarska,R.drawable.makarska1,R.drawable.makarska2,R.drawable.makarska3,R.drawable.makarska4)
    private val pulaID = arrayOf(R.drawable.pula,R.drawable.pula1,R.drawable.pula2,R.drawable.pula3,R.drawable.pula4)
    private var currentPosition = 0
    private var dots: MutableList<ImageView>? = null
    private fun setAdapter(imageIds: Array<Int>) {
        val adapter = ImageSliderAdapter(this, imageIds)
        binding!!.viewPager.setAdapter(adapter)
        val handler = Handler()
        val runnable: Runnable = object : Runnable {
            override fun run() {
                try {
                    if (currentPosition < imageIds.size - 1) {
                        currentPosition++
                        binding.viewPager.currentItem = currentPosition
                        updateDots()
                    } else {
                        currentPosition = 0
                        binding.viewPager.currentItem = currentPosition
                        updateDots()
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                } finally {
                    handler.postDelayed(this, 2000)
                }
            }
        }
        handler.postDelayed(runnable, 1000)
        createDots(imageIds.size)
        updateDots()
        binding.viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
            }

            override fun onPageSelected(position: Int) {
                currentPosition = position
                updateDots()
            }

            override fun onPageScrollStateChanged(state: Int) {}
        })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPlaceBinding.inflate(layoutInflater)
        setContentView(binding.root)
        `val` = intent.getIntExtra("place", 1)
        if (`val` == 1) {
            binding.mapImg.setImageResource(R.drawable.dubrovnik_map)
            binding.placeName.text = "Dubrovnik"
            placeName = "Dubrovnik"
            address = "https://goo.gl/maps/LjtYPaFz3a4TkMLC6"
            binding.placeDes.text = "Dubrovnik is a city in southern Croatia fronting the Adriatic Sea. It's known for its distinctive Old Town, encircled with massive stone walls completed in the 16th century. Its well-preserved buildings range from baroque St. Blaise Church to Renaissance Sponza Palace and Gothic Rector’s Palace, now a history museum. Paved with limestone, the pedestrianized Stradun (or Placa) is lined with shops and restaurants."
            setAdapter(dubrovnikID)
        } else if (`val` == 2) {
            setAdapter(novaljaID)
            binding.mapImg.setImageResource(R.drawable.img)
            address = "https://goo.gl/maps/mSJggwo2tue9gUUJ8"
            binding.placeName.text = "Novalja"
            placeName = "Novalja"
            binding.placeDes.text="Novalja is a town in the north of the island of Pag in the Croatian part of Adriatic Sea. In recent times, Novalja has become famous because of the Zrće Beach."
        } else if (`val` == 3) {
            setAdapter(makarskaID)
            binding.mapImg.setImageResource(R.drawable.img_2)
            address = "https://goo.gl/maps/sxyb4VsrGaCxmB5r8"
            binding.placeName.text = "Makarska"
            binding.placeDes.text = "Makarska is a port town on Croatia’s Dalmatian coast, known for its Makarska Riviera beaches, seafront promenade and nightlife. On a small bay between wooded headlands, the old town centers on Kačić Square. Ferries connect the port to nearby Brač island. To the east towers the rugged Mt. Biokovo, a nature reserve home to golden eagles and Balkan chamois, with hairpin roads up to Sveti Jure’s summit"
            placeName = "Makarska"
        } else {
            setAdapter(pulaID)
            binding.mapImg.setImageResource(R.drawable.img_1)
            address = "https://goo.gl/maps/y19yR6NYrWwrMEnWA"
            binding.placeName.text = "Pula"
            binding.placeDes.text = "Pula, a seafront city on the tip of Croatia’s Istrian Peninsula, is known for its protected harbor, beach-lined coast and Roman ruins. Settled in the prehistoric era and valued for its strategic location, Pula has been occupied, destroyed and rebuilt numerous times. The Romans, Ostrogoths and Venetians, as well as the Allied Forces in World War II, have each administered the city."
            placeName = "Pula"
        }
        binding.mapImg.setOnClickListener {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(address))
            startActivity(browserIntent)
        }
    }



    private fun updateDots() {
        for (i in dots!!.indices) {
            if (i == currentPosition) {
                dots!![i].setImageDrawable(ContextCompat.getDrawable(this, R.drawable.dot_active))
            } else {
                dots!![i].setImageDrawable(ContextCompat.getDrawable(this, R.drawable.dot_inactive))
            }
        }
    }

    private fun createDots(count: Int) {
        dots = ArrayList()
        binding.indicatorLayout.removeAllViews()
        for (i in 0 until count) {
            val dot = ImageView(this)
            dot.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.dot_inactive))
            val params = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            params.setMargins(8, 0, 8, 0)
            dot.layoutParams = params
            (dots as ArrayList<ImageView>).add(dot)
            binding.indicatorLayout.addView(dot)
        }
    }
}