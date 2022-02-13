package com.example.circadian

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.circadian.adapter.OnboardingAdapter
import com.google.android.material.textview.MaterialTextView

class OnboardingScreen : AppCompatActivity() {
    private lateinit var textView: MaterialTextView
    private lateinit var onboardingItemsAdapter: OnboardingAdapter
    private lateinit var indicatorsContainer: LinearLayout
    private lateinit var nextTextView: MaterialTextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onboarding_screen)

        // To change the status bar color
        window.statusBarColor = ContextCompat.getColor(this, R.color.primary_dark)

        nextTextView= findViewById(R.id.next_text)
        textView = findViewById(R.id.skip_text)
        indicatorsContainer = findViewById(R.id.onboardingIndicators)

        textView.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        setOnboardingItems()
        setUpIndicators()
        setCurrentIndicator(0)
    }

    private fun setOnboardingItems(){
        onboardingItemsAdapter = OnboardingAdapter(
            listOf(
                OnboardingItem(
                    onboardingImage = R.drawable.qr_code,
                    title = "Welcome to Circadian",
                ),
                OnboardingItem(
                    onboardingImage = R.drawable.qr_code,
                    title = "Scan your QR Codes!!",
                ),
                OnboardingItem(
                    onboardingImage = R.drawable.qr_code,
                    title = "Pull readings to your app",
                )
            )
        )
        val onboardingViewPager = findViewById<ViewPager2>(R.id.view_pager)
        onboardingViewPager.adapter = onboardingItemsAdapter
        onboardingViewPager.registerOnPageChangeCallback(object:
            ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                setCurrentIndicator(position)
            }
        })
        (onboardingViewPager.getChildAt(0) as RecyclerView).overScrollMode =
            RecyclerView.OVER_SCROLL_NEVER
        findViewById<MaterialTextView>(R.id.next_text).setOnClickListener {
            if ( onboardingViewPager.currentItem + 1 < onboardingItemsAdapter.itemCount){
                onboardingViewPager.currentItem += 1
            } //else if( onboardingViewPager.currentItem +1 < onboardingAdapter.itemCount){
            //button.setText(R.string.finish)
            //navigateToHomeActivity()
            //}
            else {
                onboardingViewPager.currentItem += 2
                nextTextView.setText(R.string.finish)
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)

            }
        }

    }



    private fun setUpIndicators() {
        indicatorsContainer = findViewById(R.id.onboardingIndicators)
        val indicators = arrayOfNulls<ImageView>(onboardingItemsAdapter.itemCount)
        val layoutParams: LinearLayout.LayoutParams =
            LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
        layoutParams.setMargins(8, 0, 8, 0)
        for ( i in indicators.indices){
            indicators[i] = ImageView(applicationContext)
            indicators[i]?.let {
                it.setImageDrawable(
                    ContextCompat.getDrawable(
                        applicationContext,
                        R.drawable.indicator_active
                    )
                )
                it.layoutParams = layoutParams
                indicatorsContainer.addView(it)
            }
        }
    }
    // current indicator for onboarding screen
    private fun setCurrentIndicator(position: Int){
        val childCount = indicatorsContainer.childCount
        for (i in 0 until childCount){
            val imageView = indicatorsContainer.getChildAt(i) as ImageView
            if ( i == position){
                imageView.setImageDrawable(
                    ContextCompat.getDrawable(
                        applicationContext,
                        R.drawable.indicator_active
                    )
                )
            } else {
                imageView.setImageDrawable(
                    ContextCompat.getDrawable(
                        applicationContext,
                        R.drawable.indicator_inactive
                    )
                )
            }
        }

    }

    }
