package com.avans.rentmycar.adapter

import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.avans.rentmycar.databinding.ItemCarBinding
import com.avans.rentmycar.model.response.CarList
import com.avans.rentmycar.utils.ImageLoader

class CarAdapter(
    private val imageLoader: ImageLoader,
    private val clickListener: (CarList) -> Unit) :
    RecyclerView.Adapter<CarAdapter.ViewHolder>() {

    private val TAG = "[RMC][CarAdapter]"

    // create an inner class with name ViewHolder
    // It takes a view argument, in which pass the generated class of fragment_list_mycars.xml
    // ie FragmentListMycarsBinding and in the RecyclerView.ViewHolder(binding.root) pass it like this
    inner class ViewHolder(container: View) : RecyclerView.ViewHolder(container) {
        // private val carImage = itemCarBinding.imageviewItemCarImage

        @RequiresApi(Build.VERSION_CODES.O)
        fun bindData(carList: CarList) {
            // TODO: Replace cute placeholder images with dumb pictures of cars
            val random = (1..10).random() * 100

            imageLoader.loadImage("http://placekitten.com/" + random + "/" + random, itemCarBinding.imageviewItemCarImage)
            itemCarBinding.textviewItemCarTitle.setText(carList.model)
            // itemCarBinding.textviewItemOfferDescription.isGone// TODO Set to visible if offer is present
            itemCarBinding.textviewItemOfferDescription.isVisible
            itemCarBinding.textviewItemOfferDescription.setText(carList.vehicleType)
            itemCarBinding.textviewItemCarDescription1.setText("licenseplate: " + carList.licensePlate + " color: " + carList.colorType)
            itemCarBinding.textviewItemCarDescription2.setText("year: " + carList.yearOfManufacture.toString() + " mileage: " + carList.mileage)
        }
    }

    lateinit var itemCarBinding: ItemCarBinding
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
            ItemCarBinding.inflate(LayoutInflater.from(parent.context), parent, false)
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