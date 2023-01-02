import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.avans.rentmycar.databinding.AddCarItemBinding
import com.avans.rentmycar.model.CarList
import com.avans.rentmycar.repository.CarRepositoryStub

private val TAG = "[RMC][CarDetailAdapter]"
class CarDetailAdapter: RecyclerView.Adapter<CarDetailAdapter.CarAddItemViewHolder>() {

    // create an inner class with name ViewHolder
    // It takes a view argument, in which pass the generated class of fragment_list_mycars.xml
    // ie AddCarItemBinding and in the RecyclerView.ViewHolder(binding.root) pass it like this
    inner class CarAddItemViewHolder(val binding: AddCarItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(carList: CarList) {
            /* TODO
            binding.textviewCarModel.text = carList.model
            binding.textviewCarColor.text = carList.colorType
            binding.textviewCarNumberOfSeats.text = carList.numberOfSeats.toString()

             */
            Log.d(TAG, "bind()")
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarAddItemViewHolder {
        Log.d(TAG, "onCreateViewHolder()")
        val binding = AddCarItemBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return CarAddItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CarAddItemViewHolder, position: Int) {
        Log.d(TAG, "onBindViewHolder() => position: "+position)
        //holder.bind(CarRepository.carList[position])
    }

    override fun getItemCount() = CarRepositoryStub.carModels.size

}