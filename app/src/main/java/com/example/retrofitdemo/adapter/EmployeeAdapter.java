package com.example.retrofitdemo.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.retrofitdemo.R;
import com.example.retrofitdemo.model.Employee;

import java.util.ArrayList;

public class EmployeeAdapter extends RecyclerView.Adapter<EmployeeAdapter.EmployeeViewHolder> {

    private ArrayList<Employee> dataList;

    public EmployeeAdapter(ArrayList<Employee> dataList) {
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public EmployeeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.single_employee_list,parent,false);
        return new EmployeeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EmployeeViewHolder holder, int position) {
        holder.mName.setText(dataList.get(position).getName());
        holder.mEmail.setText(dataList.get(position).getEmail());
        holder.mphone.setText(dataList.get(position).getPhone());
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class EmployeeViewHolder extends RecyclerView.ViewHolder {
        TextView mName,mEmail,mphone;

        public EmployeeViewHolder(@NonNull View itemView) {
            super(itemView);
            mName = itemView.findViewById(R.id.name);
            mEmail = itemView.findViewById(R.id.email);
            mphone = itemView.findViewById(R.id.phone);
        }
    }
}
