//package com.avans.rentmycar.adapter
//
//import android.content.Context
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.Toast
//import androidx.recyclerview.widget.RecyclerView
//import com.avans.rentmycar.R
//import com.avans.rentmycar.model.Offer
//
//class ListAdapter(private val context: Context, private val mOffers: List<Offer>, private val mRowLayout: Int) : RecyclerView.Adapter<ListAdapter.OfferViewHolder>() {
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OfferViewHolder {
//        val view = LayoutInflater.from(parent.context).inflate(mRowLayout, parent, false)
//        return OfferViewHolder(view)
//    }
//
//    override fun onBindViewHolder(holder: OfferViewHolder, position: Int) {
//        holder.startDateTime?.text = context.resources.getString(R.string.startDateTime, position + 1)
//        holder.endDateTime?.text = context.resources.getString(R.string.ques_title, mQuestions[position].title)
//        holder.pickupLocation?.text = context.resources.getString(R.string.pickupLocation, mQuestions[position].link)
//
//        holder.containerView.setOnClickListener {
//            Toast.makeText(context, "Clicked on: " + holder.startDateTime.text, Toast.LENGTH_SHORT).show();
//        }
//    }
//
//    override fun getItemCount(): Int {
//        return mOffers.size
//    }
//
//    class OfferViewHolder(val containerView: View) : RecyclerView.ViewHolder(containerView) {
//        val positionNumber = containerView.positionNumber;
//        val startDateTime = containerView.startDateTime;
//        val link = containerView.link;
//    }
//}
