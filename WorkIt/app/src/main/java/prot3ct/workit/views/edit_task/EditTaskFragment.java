package prot3ct.workit.views.edit_task;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import prot3ct.workit.R;
import prot3ct.workit.view_models.TaskDetailViewModel;
import prot3ct.workit.utils.WorkItProgressDialog;
import prot3ct.workit.views.edit_task.base.EditTaskContract;
import prot3ct.workit.views.list_tasks.ListTasksActivity;
import prot3ct.workit.views.navigation.DrawerUtil;

public class EditTaskFragment extends Fragment implements EditTaskContract.View {
    private EditTaskContract.Presenter presenter;
    private Context context;

    private int taskId;

    private TextView titleTextView;
    private TextView startDateTextView;
    private EditText lengthEditText;
    private TextView descriptionTextView;
    private TextView cityTextView;
    private TextView addressTextView;
    private TextView rewardTextView;
    private Toolbar toolbar;
    private Button saveTaskButton;

    private Calendar date;

    private WorkItProgressDialog dialog;

    public EditTaskFragment() {
        // Required empty public constructor
    }

    public static EditTaskFragment newInstance() {
        return new EditTaskFragment();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        this.context = context;
    }

    @Override
    public void setPresenter(EditTaskContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_task, container, false);

        this.toolbar = view.findViewById(R.id.id_drawer_toolbar);
        this.dialog = new WorkItProgressDialog(context);
        this.titleTextView = (TextView) view.findViewById(R.id.id_title_edit_text);
        this.startDateTextView = (TextView) view.findViewById(R.id.id_choose_start_date_text_view);
        this.lengthEditText = (EditText) view.findViewById(R.id.id_length_edit_text);
        this.descriptionTextView = (TextView) view.findViewById(R.id.id_description_edit_text);
        this.cityTextView = (TextView) view.findViewById(R.id.id_city_edit_text);
        this.addressTextView = (TextView) view.findViewById(R.id.id_address_edit_text);
        this.rewardTextView = (TextView) view.findViewById(R.id.id_reward_edit_text);
        this.saveTaskButton = (Button) view.findViewById(R.id.id_create_task_btn);
        DrawerUtil drawer = new DrawerUtil(this.getActivity(), this.toolbar);
        drawer.getDrawer();

        this.taskId = getActivity().getIntent().getIntExtra("taskId", 0);
        presenter.getTaskDetails(taskId);

        this.startDateTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateTimePicker();
            }
        });

        this.saveTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.updateTask(
                    taskId,
                    titleTextView.getText().toString(),
                    startDateTextView.getText().toString(),
                    lengthEditText.getText().toString(),
                    descriptionTextView.getText().toString(),
                    cityTextView.getText().toString(),
                    addressTextView.getText().toString(),
                    rewardTextView.getText().toString()
                );
            }
        });

        return view;
    }

    @Override
    public void showListJobsActivity() {
        Intent intent = new Intent(this.context, ListTasksActivity.class);
        startActivity(intent);
    }

    @Override
    public void updateTask(TaskDetailViewModel task) {
        this.titleTextView.setText(task.getTitle());
        this.startDateTextView.setText(task.getStartDate());
        this.lengthEditText.setText(String.valueOf(task.getLength()));
        this.descriptionTextView.setText(task.getDescription());
        this.cityTextView.setText(task.getCity());
        this.addressTextView.setText(task.getAddress());
        this.rewardTextView.setText(String.valueOf(task.getReward()));
    }

    @Override
    public void notifyError(String errorMessage) {
        Toast.makeText(getContext(), errorMessage, Toast.LENGTH_LONG).show();
    }

    @Override
    public void notifySuccessful() {
        Toast.makeText(getContext(), "Task updated successfully", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showDialogforLoading() {
        this.dialog.showProgress("Creating task...");
    }

    @Override
    public void dismissDialog() {
        this.dialog.dismissProgress();
    }

    public void showDateTimePicker() {
        final Calendar currentDate = Calendar.getInstance();
        this.date = Calendar.getInstance();
        new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
            boolean first = true;

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                if (first) {
                    date.set(year, monthOfYear, dayOfMonth);
                    new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {

                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                            date.set(Calendar.HOUR_OF_DAY, hourOfDay);
                            date.set(Calendar.MINUTE, minute);
                            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd,HH:mm");

                            startDateTextView.setText(dateFormat.format(date.getTime()));

                            first = false;
                        }
                    }, currentDate.get(Calendar.HOUR_OF_DAY), currentDate.get(Calendar.MINUTE), true).show();

                    first = false;
                }
            }
        }, currentDate.get(Calendar.YEAR), currentDate.get(Calendar.MONTH), currentDate.get(Calendar.DATE)).show();
    }
}
