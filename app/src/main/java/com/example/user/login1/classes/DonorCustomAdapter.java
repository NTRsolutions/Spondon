package com.example.user.login1.classes;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.login1.R;
import com.example.user.login1.activities.SearchActivitySample;

import java.util.List;

/**
 * Created by User on 1/1/2018.
 */

public class DonorCustomAdapter extends RecyclerView.Adapter<DonorCustomAdapter.ViewHolder> {
    private Context context;
    private List<DataDonor> donorList;


    public DonorCustomAdapter(Context context, List<DataDonor> donorList) {
        this.context = context;
        this.donorList = donorList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.donor_list_layout, null);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        final DataDonor dataDonor = donorList.get(position);

        holder.name.setText("Name: " +dataDonor.getName());
        holder.location.setText("Location: " +dataDonor.getLocation());
        holder.clocation.setText("Current Location: " +dataDonor.getClocation());
        holder.distance.setText("Distance: "+dataDonor.getDistance()+ " KM");
        holder.request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(context,dataDonor.getName(),Toast.LENGTH_SHORT).show();
                SearchActivitySample sas=new SearchActivitySample();
                int status=sas.sendRequest(dataDonor.getUser(),dataDonor.getName(),dataDonor.getPhone(),dataDonor.getClocation(),dataDonor.getDistance());
                if (status==1){
                    Toast.makeText(context,"Request Sent!",Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(context,"Something Wrong, Please Try Again.",Toast.LENGTH_LONG).show();
                    //Toast.makeText(context,"Request Sent!",Toast.LENGTH_LONG).show();
                }
                Log.d("status From Adapter", String.valueOf(status));
            }
        });
    }

    @Override
    public int getItemCount() {
        return donorList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        //ImageView iv;
        TextView name, location, clocation, distance;
        Button request;

        public ViewHolder(View itemView) {
            super(itemView);

            //iv=itemView.findViewById(R.id.ivPic);
            name = itemView.findViewById(R.id.tvName);
            location = itemView.findViewById(R.id.tvLocation);
            clocation = itemView.findViewById(R.id.tvClocation);
            distance = itemView.findViewById(R.id.tvDistance);

            request = itemView.findViewById(R.id.btnSendRequest);


        }
    }

}
