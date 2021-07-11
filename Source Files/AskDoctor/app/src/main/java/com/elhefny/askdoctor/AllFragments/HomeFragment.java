package com.elhefny.askdoctor.AllFragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.elhefny.askdoctor.DepartmentsRecyclerClasses.DepartmentDetails;
import com.elhefny.askdoctor.DepartmentsRecyclerClasses.DepartmentRecyclerAdapter;
import com.elhefny.askdoctor.R;
import com.elhefny.askdoctor.SelectedDepartmentDetailActivity;

import java.util.ArrayList;

public class HomeFragment extends Fragment {
    RecyclerView recyclerView;
    ArrayList<DepartmentDetails> departmentDetails;
    DepartmentRecyclerAdapter adapter;


    public HomeFragment() {
        // Required empty public constructor
    }

    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        YoYo.with(Techniques.Bounce)
                .duration(700)
                .repeat(2)
                .playOn(view.findViewById(R.id.home_fragment_tv));
        departmentDetails = new ArrayList<>();
        createDepartments();
        recyclerView = view.findViewById(R.id.fragment_main_recycler_view_departments);
        adapter = new DepartmentRecyclerAdapter(getContext(), departmentDetails, new DepartmentRecyclerAdapter.onDepartmentClicked() {
            @Override
            public void getClickedDepartment(DepartmentDetails clickedDepartment) {
                Intent i = new Intent(getContext(), SelectedDepartmentDetailActivity.class);
                i.putExtra(getString(R.string.sendDepartmentDetails), clickedDepartment);
                startActivity(i);
            }
        });
//        int spanCount = 1;
//        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(spanCount, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

    }

    private void createDepartments() {
        DepartmentDetails department_1 = new DepartmentDetails(
                R.drawable.department_1
                , getString(R.string.department_1)
                , getString(R.string.department_1_Detail)
        );
        departmentDetails.add(department_1);

        DepartmentDetails department_2 = new DepartmentDetails(
                R.drawable.department_2
                , getString(R.string.department_2)
                , getString(R.string.department_2_Detail)
        );
        departmentDetails.add(department_2);

        DepartmentDetails department_3 = new DepartmentDetails(
                R.drawable.department_3
                , getString(R.string.department_3)
                , getString(R.string.department_3_Detail)
        );
        departmentDetails.add(department_3);

        DepartmentDetails department_4 = new DepartmentDetails(
                R.drawable.department_4
                , getString(R.string.department_4)
                , getString(R.string.department_4_Detail)
        );
        departmentDetails.add(department_4);

        DepartmentDetails department_5 = new DepartmentDetails(
                R.drawable.department_5
                , getString(R.string.department_5)
                , getString(R.string.department_5_Detail)
        );
        departmentDetails.add(department_5);

        DepartmentDetails department_6 = new DepartmentDetails(
                R.drawable.department_6
                , getString(R.string.department_6)
                , getString(R.string.department_6_Detail)
        );
        departmentDetails.add(department_6);

        DepartmentDetails department_7 = new DepartmentDetails(
                R.drawable.department_7
                , getString(R.string.department_7)
                , getString(R.string.department_7_Detail)
        );
        departmentDetails.add(department_7);

        DepartmentDetails department_8 = new DepartmentDetails(
                R.drawable.department_8
                , getString(R.string.department_8)
                , getString(R.string.department_8_Detail)
        );
        departmentDetails.add(department_8);

        DepartmentDetails department_9 = new DepartmentDetails(
                R.drawable.department_9
                , getString(R.string.department_9)
                , getString(R.string.department_9_Detail)
        );
        departmentDetails.add(department_9);

        DepartmentDetails department_10 = new DepartmentDetails(
                R.drawable.department_10
                , getString(R.string.department_10)
                , getString(R.string.department_10_Detail)
        );
        departmentDetails.add(department_10);

        DepartmentDetails department_11 = new DepartmentDetails(
                R.drawable.department_11
                , getString(R.string.department_11)
                , getString(R.string.department_11_Detail)
        );
        departmentDetails.add(department_11);
        DepartmentDetails department_12 = new DepartmentDetails(
                R.drawable.department_12
                , getString(R.string.department_12)
                , getString(R.string.department_12_Detail)
        );
        departmentDetails.add(department_12);
        DepartmentDetails publicDepartment = new DepartmentDetails(
                R.drawable.public_department
                , getString(R.string.public_department)
                , getString(R.string.public_department_Details)
        );
        departmentDetails.add(publicDepartment);
    }
}