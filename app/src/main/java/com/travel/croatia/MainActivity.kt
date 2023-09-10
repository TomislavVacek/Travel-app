package com.travel.croatia

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.travel.croatia.databinding.ActivityMainBinding // Ove naredbe uvoze potrebne klase i resurse za implementaciju aktivnosti.


class MainActivity : AppCompatActivity() {  //Klasa predstavlja glavnu aktivnost aplikacije.
    var binding: ActivityMainBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) { //funkcija se poziva prilikom stvaranja aktivnosti. U ovoj funkciji se postavlja prikaz korisničkog sučelja, inicijaliziraju potrebni objekti i resursi te postavljaju slušatelji događaja za elemente korisničkog sučelja.
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding!!.root)
        val viewTreeObserver = binding!!.place2.viewTreeObserver
        viewTreeObserver.addOnGlobalLayoutListener { initUi() }
        binding!!.place1.setOnClickListener { setListener(1) }
        binding!!.place2.setOnClickListener { setListener(2) }
        binding!!.place3.setOnClickListener { setListener(3) }
        binding!!.place4.setOnClickListener { setListener(4) }
        binding!!.logout.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            startActivity(Intent(this@MainActivity,LoginActivity::class.java))
        }
    }

    fun setListener(pos: Int) { //poziva kada korisnik klikne na jedno od mjesta
        startActivity(
            Intent(this@MainActivity, PlaceActivity::class.java)
                .putExtra("place", pos)
        )
    }

    private fun initUi() {  //se poziva kada se korisničko sučelje prikazuje. Koristi animaciju za postupno prikazivanje elemenata korisničkog sučelja
        val leftAnimator = ObjectAnimator.ofFloat(binding!!.place1, "translationX", -binding?.place1?.width?.toFloat()!!, 0f)
        leftAnimator.duration = 500
        val rightAnimator = ObjectAnimator.ofFloat(binding!!.place2, "translationX", binding?.place2?.width?.toFloat()!!, 0f)
        leftAnimator.duration = 500
        val leftAnimator1 = ObjectAnimator.ofFloat(binding!!.place3, "translationX", -binding?.place3?.width?.toFloat()!!, 0f)
        leftAnimator.duration = 500
        val rightAnimator1 = ObjectAnimator.ofFloat(binding!!.place4, "translationX", binding?.place4?.width?.toFloat()!!, 0f)
        leftAnimator.duration = 500


        val animatorSet = AnimatorSet()
        animatorSet.playTogether(leftAnimator, rightAnimator, leftAnimator1, rightAnimator1)
        animatorSet.start()
    }

    override fun onResume() {  //funkcija se poziva kada se aktivnost ponovno prikaže korisniku nakon prekida
        super.onResume()
        initUi()
    }

    override fun onBackPressed() {  //funkcija se poziva kada se pritisne tipka "Back" na uređaju
        finishAffinity()        //završava sve aktivnosti aplikacije i zatvara je
    }
}