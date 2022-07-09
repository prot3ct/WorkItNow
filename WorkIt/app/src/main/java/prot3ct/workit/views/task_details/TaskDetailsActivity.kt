package prot3ct.workit.views.task_details

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import prot3ct.workit.R
import prot3ct.workit.views.task_details.TaskDetailsFragment
import prot3ct.workit.views.task_details.TaskDetailsPresenter
import prot3ct.workit.views.task_details.base.TaskDetailsContract

class TaskDetailsActivity : AppCompatActivity() {

    private lateinit var presenter: TaskDetailsContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_details)
        var jobDetailsFragment =
            supportFragmentManager.findFragmentById(R.id.fragment_container) as TaskDetailsFragment?
        if (jobDetailsFragment == null) {
            jobDetailsFragment = TaskDetailsFragment.newInstance()
            supportFragmentManager
                .beginTransaction()
                .add(R.id.fragment_container, jobDetailsFragment)
                .commit()
        }
        presenter = TaskDetailsPresenter(jobDetailsFragment, this)
        jobDetailsFragment!!.setPresenter(presenter)
    }
}