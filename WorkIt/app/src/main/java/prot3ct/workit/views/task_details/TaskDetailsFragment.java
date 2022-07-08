package prot3ct.workit.views.task_details;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import prot3ct.workit.R;
import prot3ct.workit.utils.WorkItProgressDialog;
import prot3ct.workit.view_models.IsUserAssignableToTaskViewModel;
import prot3ct.workit.view_models.TaskDetailViewModel;
import prot3ct.workit.views.profile.ProfileActivity;
import prot3ct.workit.views.task_details.base.TaskDetailsContract;
import prot3ct.workit.views.navigation.DrawerUtil;

public class TaskDetailsFragment extends Fragment implements TaskDetailsContract.View, OnMapReadyCallback {
    private TaskDetailsContract.Presenter presenter;
    private Context context;

    private TaskDetailViewModel taskDetails;
    private int flag;

    private GoogleMap mMap;
    private TextView taskTitle;
    private TextView taskDescription;
    private TextView taskStartDate;
    private TextView reward;
    private TextView city;
    private TextView supervisorName;
    private TextView supervisorRating;
    private Toolbar toolbar;
    private Button applyForTask;
    private WorkItProgressDialog dialog;

    public TaskDetailsFragment() {
        // Required empty public constructor
    }

    public static TaskDetailsFragment newInstance() {
        return new TaskDetailsFragment();
    }

    @Override
    public void setPresenter(TaskDetailsContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_task_details, container, false);

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        this.dialog = new WorkItProgressDialog(context);
        this.applyForTask = view.findViewById(R.id.id_apply_for_task_button);
        this.toolbar = view.findViewById(R.id.id_drawer_toolbar);
        this.taskTitle = view.findViewById(R.id.id_title_details_edit_text);
        this.reward = view.findViewById(R.id.id_reward_details_edit_text);
        this.taskDescription = view.findViewById(R.id.id_description_details_edit_text);
        this.taskStartDate = view.findViewById(R.id.id_date_details_text_view);
        this.city = view.findViewById(R.id.id_city_details_text_view);
        this.supervisorName = view.findViewById(R.id.id_supervisor_text_view);
        this.supervisorRating = view.findViewById(R.id.id_supervisor_rating_text_view);

        DrawerUtil drawer = new DrawerUtil(this.getActivity(), this.toolbar);
        drawer.getDrawer();

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        this.context = context;
    }

    @Override
    public void updateTask(final TaskDetailViewModel task) {
        taskDetails = task;
        this.taskTitle.setText(taskDetails.getTitle());
        this.taskDescription.setText(taskDetails.getDescription());

        DateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm", Locale.ENGLISH);
        Date date = null;
        try {
            date = format.parse(taskDetails.getStartDate());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Date currentDate = Calendar.getInstance().getTime();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        ;
        this.taskStartDate.setText(getOrdinal(calendar.get(Calendar.DAY_OF_MONTH)) + " " +
                getMonthForInt(calendar.get(Calendar.MONTH)) + " at " +
                String.format("%02d:%02d", calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE)) +
                " for " + taskDetails.getLength() + " hours");
        this.reward.setText("BGN " + taskDetails.getReward() +"/hr");
        this.city.setText(taskDetails.getCity() + ", " + taskDetails.getAddress());// for
        this.supervisorName.setText("For " + task.getSupervisorName());
        this.supervisorRating.setText(task.getSupervisorRating() + "");

        this.supervisorName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ProfileActivity.class);
                intent.putExtra("userId", task.getSupervisorId());
                context.startActivity(intent);
            }
        });

        presenter.getLatLng(taskDetails.getCity() + ", " + taskDetails.getAddress());
    }

    @Override
    public void notifySuccessful(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void notifyError(String errorMessage) {
        Toast.makeText(getContext(), errorMessage, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showDialogforLoading() {
        this.dialog.showProgress("Logging in...");
    }

    @Override
    public void dismissDialog() {
        this.dialog.dismissProgress();
    }

    private String getMonthForInt(int num) {
        String month = "wrong";
        DateFormatSymbols dfs = new DateFormatSymbols();
        String[] months = dfs.getMonths();
        if (num >= 0 && num <= 11 ) {
            month = months[num];
        }
        return month;
    }

    private String getOrdinal(int i) {
        String[] sufixes = new String[] { "th", "st", "nd", "rd", "th", "th", "th", "th", "th", "th" };
        switch (i % 100) {
            case 11:
            case 12:
            case 13:
                return i + "th";
            default:
                return i + sufixes[i % 10];
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        int taskId = getActivity().getIntent().getIntExtra("taskId", 0);
        presenter.getTaskDetails(taskId);
        presenter.getCanAssignToTask(taskId);
    }

    @Override
    public void updateMap(double lat, double lng) {
        LatLng location = new LatLng(lat, lng);
        mMap.addMarker(new MarkerOptions().position(location));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 17.0f));
    }

    @Override
    public void updateButton(final IsUserAssignableToTaskViewModel canAssignToTask) {
        this.applyForTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (flag == 0) {
                    presenter.declineTaskRequest(canAssignToTask.getPendingRequestId(), 2);
                    applyForTask.setText("I'll do it");
                    applyForTask.setTextColor(getResources().getColor(R.color.md_black_1000));
                    applyForTask.setBackgroundColor(getResources().getColor(R.color.bg_login));
                    flag = 2;
                }
                else if (flag == 1) {
                    presenter.removeAssignedUser(taskDetails.getTaskId());
                    getActivity().finish();
                }
                else {
                    presenter.createTaskRequest(taskDetails.getTaskId());
                    applyForTask.setText("Cancel pending request");
                    applyForTask.setTextColor(getResources().getColor(R.color.md_white_1000));
                    applyForTask.setBackgroundColor(getResources().getColor(R.color.md_red_400));
                    flag = 0;
                }
            }
        });


        if (canAssignToTask.isUserAssignableToTaskMessage().equals("Cancel pending request"))
        {
            this.applyForTask.setText(canAssignToTask.isUserAssignableToTaskMessage());
            this.applyForTask.setTextColor(getResources().getColor(R.color.md_white_1000));
            this.applyForTask.setBackgroundColor(getResources().getColor(R.color.md_red_400));

            flag = 0;
        }
        else if (canAssignToTask.isUserAssignableToTaskMessage().equals("I can't do this task anymore"))
        {
            this.applyForTask.setText(canAssignToTask.isUserAssignableToTaskMessage());
            this.applyForTask.setTextColor(getResources().getColor(R.color.md_white_1000));
            this.applyForTask.setBackgroundColor(getResources().getColor(R.color.md_red_900));

            flag = 1;
        }
        else
        {
            flag = 2;
        }
    }
}
