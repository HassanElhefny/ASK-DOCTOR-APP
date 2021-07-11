package com.elhefny.askdoctor.Dedelopers;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.elhefny.askdoctor.R;
import com.google.android.material.textview.MaterialTextView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class DeveloperRecyclerAdapter extends RecyclerView.Adapter<DeveloperRecyclerAdapter.developerViewHolder> {

    private ArrayList<Developer> developers = new ArrayList<>();
    Context context;
    getDeveloper Interface;

    public DeveloperRecyclerAdapter(Context context, getDeveloper anInterface) {
        this.context = context;
        Interface = anInterface;
    }

    public void addDeveloper(Developer developer) {
        developers.add(developer);
        notifyDataSetChanged();
    }

    @NonNull
    @NotNull
    @Override
    public developerViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        return new developerViewHolder(LayoutInflater.from(context).inflate(
                R.layout.recycler_developer_design,
                parent,
                false
        ));
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull developerViewHolder holder, int position) {
        Developer currentDeveloper = developers.get(position);
        holder.image.setImageResource(currentDeveloper.getImage());
        holder.tvName.setText(currentDeveloper.getName());
        holder.tvEmail.setText(currentDeveloper.getLinkedIn());
        holder.itemView.setAnimation(AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left));
        YoYo.with(Techniques.Wobble)
                .duration(3000)
                .repeat(1)
                .playOn(holder.tvName);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Interface.getClickedDeveloper(currentDeveloper);
            }
        });
    }

    @Override
    public int getItemCount() {
        return developers.size();
    }

    public class developerViewHolder extends RecyclerView.ViewHolder {
        MaterialTextView tvName, tvEmail;
        CircleImageView image;

        public developerViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.recycler_developer_name);
            tvEmail = itemView.findViewById(R.id.recycler_developer_email);
            image = itemView.findViewById(R.id.recycler_developer_iv);
        }
    }

    public interface getDeveloper {
        void getClickedDeveloper(Developer developer);
    }
}
