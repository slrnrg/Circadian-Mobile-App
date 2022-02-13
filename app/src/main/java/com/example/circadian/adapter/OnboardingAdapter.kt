package com.example.circadian.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.circadian.OnboardingItem
import com.example.circadian.R

class OnboardingAdapter(private val onboardingItems: List<OnboardingItem>): RecyclerView.Adapter<OnboardingAdapter.OnboardingItemsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OnboardingItemsViewHolder {
        return OnboardingItemsViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.onboarding_items,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: OnboardingItemsViewHolder, position: Int) {
        holder.bind(onboardingItems[position])
    }

    override fun getItemCount(): Int {
        return onboardingItems.size
    }

    inner class OnboardingItemsViewHolder(view: View): RecyclerView.ViewHolder(view){
        private val onboardingImage = view.findViewById<ImageView>(R.id.onboarding_image)
        private val textTitle = view.findViewById<TextView>(R.id.text_title)

        fun bind(onboardingItem: OnboardingItem){
            onboardingImage.setImageResource(onboardingItem.onboardingImage)
            textTitle.text = onboardingItem.title
        }
    }
}