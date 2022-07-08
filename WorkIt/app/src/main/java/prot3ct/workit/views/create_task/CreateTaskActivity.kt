package prot3ct.workit.views.create_task

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import prot3ct.workit.R
import prot3ct.workit.views.create_task.CreateTaskFragment
import prot3ct.workit.views.create_task.CreateTaskPresenter
import prot3ct.workit.views.create_task.base.CreateTaskContract

class CreateTaskActivity : AppCompatActivity() {
    private lateinit var presenter: CreateTaskContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_task)
        var createJobFragment =
            supportFragmentManager.findFragmentById(R.id.fragment_container) as CreateTaskFragment
        presenter = CreateTaskPresenter(createJobFragment, this)
        createJobFragment.setPresenter(presenter)
    }
}