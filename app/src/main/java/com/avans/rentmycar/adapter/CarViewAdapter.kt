import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.avans.rentmycar.databinding.MycarsItemBinding
import com.avans.rentmycar.model.CarModel
import com.avans.rentmycar.repository.CarRepositoryStub

private val TAG = "[RMC][CarViewAdapter]"
class CarViewAdapter: RecyclerView.Adapter<CarViewAdapter.CarViewHolder>() {

    // create an inner class with name ViewHolder
    // It takes a view argument, in which pass the generated class of fragment_list_mycars.xml
    // ie FragmentListMycarsBinding and in the RecyclerView.ViewHolder(binding.root) pass it like this
    inner class CarViewHolder(val binding: MycarsItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(carModel: CarModel) {
            Log.d(TAG, "bind() => carmodel: " + carModel.title)
            binding.carImage.setImageResource(carModel.image)
            binding.textviewCarTitle.text = carModel.title
            binding.textviewCarDescription1.text = carModel.description1
            binding.textviewCarDescription2.text = carModel.description2

            // Adding clicklistener tp ropot layoput of this item
            //carModel.setOnClickListener{
            //    onItemClicked(carModel)
            //}
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarViewHolder {
        Log.d(TAG, "onCreateViewHolder()")
        val binding = MycarsItemBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return CarViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CarViewHolder, position: Int) {
        Log.d(TAG, "onBindViewHolder() => position: "+position)
        holder.bind(CarRepositoryStub.carModels[position])
    }

    override fun getItemCount() = CarRepositoryStub.carModels.size

}