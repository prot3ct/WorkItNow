package prot3ct.workit.views.list_dialogs

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import prot3ct.workit.R
import prot3ct.workit.views.list_dialogs.ListDialogsFragment
import prot3ct.workit.views.list_dialogs.ListDialogsPresenter
import prot3ct.workit.views.list_dialogs.base.ListDialogsContract

class ListDialogsActivity : AppCompatActivity() {
    private lateinit var presenter: ListDialogsContract.Presenter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_dialogs)
        var editTaskFragment =
            supportFragmentManager.findFragmentById(R.id.fragment_container) as ListDialogsFragment?
        if (editTaskFragment == null) {
            editTaskFragment = ListDialogsFragment.newInstance()
            supportFragmentManager
                .beginTransaction()
                .add(R.id.fragment_container, editTaskFragment)
                .commit()
        }
        presenter = ListDialogsPresenter(editTaskFragment, this)
        editTaskFragment.setPresenter(presenter)
    }
}