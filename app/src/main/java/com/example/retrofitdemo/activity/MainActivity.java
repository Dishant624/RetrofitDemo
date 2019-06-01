package com.example.retrofitdemo.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.example.retrofitdemo.R;
import com.example.retrofitdemo.adapter.EmployeeAdapter;
import com.example.retrofitdemo.model.Employee;
import com.example.retrofitdemo.model.EmployeeList;
import com.example.retrofitdemo.network.GetEmployeeDataService;
import com.example.retrofitdemo.network.RetrofitInstance;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;

    EmployeeAdapter employeeAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        GetEmployeeDataService getEmployeeDataService = RetrofitInstance.getRetrofitInstance()
                .create(GetEmployeeDataService.class);

        Call<EmployeeList> employeeListCall = getEmployeeDataService.getEmployeeData(23);

        employeeListCall.enqueue(new Callback<EmployeeList>() {
            @Override
            public void onResponse(Call<EmployeeList> call, Response<EmployeeList> response) {
                recyclerView =findViewById(R.id.rc);

                ArrayList<Employee> employees =new ArrayList<>();

                employees.addAll(response.body().getEmployeeArrayList());
                employees.addAll(response.body().getEmployeeArrayList());
                employees.addAll(response.body().getEmployeeArrayList());
                employeeAdapter =new EmployeeAdapter(employees);

                recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this,RecyclerView.VERTICAL,false));

                recyclerView.setAdapter(employeeAdapter);

            }

            @Override
            public void onFailure(Call<EmployeeList> call, Throwable throwable) {

                Toast.makeText(MainActivity.this, "Something wrong!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
