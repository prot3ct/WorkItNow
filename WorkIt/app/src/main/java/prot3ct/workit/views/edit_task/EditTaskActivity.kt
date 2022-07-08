package prot3ct.workit.views.edit_task

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import prot3ct.workit.R
import prot3ct.workit.views.edit_task.EditTaskFragment
import prot3ct.workit.views.edit_task.EditTaskPresenter
import prot3ct.workit.views.edit_task.base.EditTaskContract

class EditTaskActivity : AppCompatActivity() {

    private lateinit var presenter: EditTaskContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_task)
        var editTaskFragment =
            supportFragmentManager.findFragmentById(R.id.fragment_container) as EditTaskFragment?
        if (editTaskFragment == null) {
            editTaskFragment = EditTaskFragment.newInstance()
            supportFragmentManager
                .beginTransaction()
                .add(R.id.fragment_container, editTaskFragment)
                .commit()
        }

        presenter = EditTaskPresenter(editTaskFragment, this)
        editTaskFragment!!.setPresenter(presenter)
    }
}