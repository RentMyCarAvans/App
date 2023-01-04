package com.avans.rentmycar.adapter

import android.content.Context
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.avans.rentmycar.databinding.MycarsItemBinding
import com.avans.rentmycar.model.CarList
import com.avans.rentmycar.utils.ImageLoader
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class CarAdapter(
    private val imageLoader: ImageLoader,
    private val clickListener: (CarList) -> Unit) :
    RecyclerView.Adapter<CarAdapter.ViewHolder>() {

    private val TAG = "[RMC][CarAdapter]"

    // create an inner class with name ViewHolder
    // It takes a view argument, in which pass the generated class of fragment_list_mycars.xml
    // ie FragmentListMycarsBinding and in the RecyclerView.ViewHolder(binding.root) pass it like this
    inner class ViewHolder(container: View) : RecyclerView.ViewHolder(container) {
        private val carImage = itemCarBinding.carImage
        private val carTitle = itemCarBinding.textviewCarTitle
        private val carDescription1 = itemCarBinding.textviewCarDescription1
        private val carDescription2 = itemCarBinding.textviewCarDescription2

        @RequiresApi(Build.VERSION_CODES.O)
        fun bindData(carList: CarList) {
            // TODO: Replace cute placeholder images with dumb pictures of cars
            val random = (1..10).random() * 100

            imageLoader.loadImage("http://placekitten.com/" + random + "/" + random, carImage)
            carTitle.text = carList.model
            carDescription1.text = "offer: not offered"
            carDescription2.text = "year: " + carList.yearOfManufacture.toString() + " mileage: " + carList.mileage
        }
    }

    lateinit var itemCarBinding: MycarsItemBinding
    private val carResponse = mutableListOf<CarList>()

    fun setData(carData: Collection<CarList>){
        Log.d(TAG,"setData()")
        this.carResponse.clear()
        this.carResponse.addAll(carData)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarAdapter.ViewHolder {
        Log.d(TAG, "onCreateViewHolder()")
        itemCarBinding =
            MycarsItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(itemCarBinding.root)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Log.d(TAG, "onBindViewHolder() => position: "+position)
        holder.itemView.setOnClickListener {
            clickListener(carResponse[position])
            Log.d(TAG, "Clicked on car " + carResponse[position].model + " with id: " + carResponse[position].id)
        }
        holder.bindData(carResponse[position])
    }

    override fun getItemCount() = carResponse.size

}