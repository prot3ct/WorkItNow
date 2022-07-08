package prot3ct.workit.views.list_task_requests

import android.content.Context
import prot3ct.workit.view_models.TaskRequestListViewModel
import androidx.recyclerview.widget.RecyclerView
import android.view.ViewGroup
import android.view.LayoutInflater
import prot3ct.workit.R
import android.content.Intent
import prot3ct.workit.views.profile.ProfileActivity
import android.graphics.BitmapFactory
import android.util.Base64
import android.view.View
import android.widget.Button
import android.widget.ImageView
import androidx.cardview.widget.CardView
import android.widget.TextView
import prot3ct.workit.views.list_task_requests.base.ListTaskRequestContract

class ListTaskRequestAdapter internal constructor(
    private val requests: MutableList<TaskRequestListViewModel>,
    private val context: Context,
    private val presenter: ListTaskRequestContract.Presenter
) : RecyclerView.Adapter<ListTaskRequestAdapter.TaskViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TaskViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.single_task_request, parent, false)
        return TaskViewHolder(v)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.fullName.text = requests[position].fullName
        holder.acceptButton.setOnClickListener {
            presenter.acceptTaskRequest(
                requests[position].taskRequestId, 3
            )
        }
        holder.declineButton.setOnClickListener {
            presenter.declineTaskRequest(requests[position].taskRequestId, 2)
            requests.removeAt(position)
            notifyDataSetChanged()
        }
        holder.cv.setOnClickListener {
            val intent = Intent(context, ProfileActivity::class.java)
            intent.putExtra("userId", requests[position].requesterId)
            context.startActivity(intent)
        }
        if (requests[position].profilePictureAsString != null) {
            val decodedString =
                Base64.decode(requests[position].profilePictureAsString, Base64.DEFAULT)
            val bmp = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)
            holder.profilePicture.setImageBitmap(bmp)
        }
    }

    override fun getItemCount(): Int {
        return requests.size
    }

    class TaskViewHolder internal constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var cv: CardView
        var fullName: TextView
        var acceptButton: Button
        var declineButton: Button
        var profilePicture: ImageView

        init {
            cv = itemView.findViewById(R.id.id_single_task_request_holder)
            profilePicture =
                itemView.findViewById(R.id.id_task_requester_profile_picture_image_view)
            fullName = itemView.findViewById(R.id.id_full_name_text_view)
            acceptButton = itemView.findViewById(R.id.id_accept_task_request_button)
            declineButton = itemView.findViewById(R.id.id_decline_task_request_button)
        }
    }
}