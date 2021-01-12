package com.ujjwalsingh.kotlinmvvm.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ujjwalsingh.kotlinmvvm.R
import com.ujjwalsingh.kotlinmvvm.getProgressDrawable
import com.ujjwalsingh.kotlinmvvm.loadImage
import com.ujjwalsingh.kotlinmvvm.model.Country
import kotlinx.android.synthetic.main.list_item.view.*

class CountryListAdapter(var countries: ArrayList<Country>) :
    RecyclerView.Adapter<CountryListAdapter.CountryViewHolder>() {

    fun updateList(newCountries: List<Country>){
        countries.clear()
        countries.addAll(newCountries)
        notifyDataSetChanged()
    }

    class CountryViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private var countryCapital = view.capital
        private var imageView = view.imageView
        private var countryName = view.country__name
        private var progressDrawable = getProgressDrawable(view.context)

        fun bind(country: Country) {
            countryName.text = country.countryName
            countryCapital.text = country.capital
            imageView.loadImage(country.flag,progressDrawable)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)= CountryViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.list_item,parent,false)
    )

    override fun getItemCount(): Int {
        return countries.size
    }

    override fun onBindViewHolder(holder: CountryViewHolder, position: Int) {
        holder.bind(countries[position])
    }
}