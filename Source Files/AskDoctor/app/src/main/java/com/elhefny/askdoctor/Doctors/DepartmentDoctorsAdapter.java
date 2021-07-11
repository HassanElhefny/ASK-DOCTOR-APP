package com.elhefny.askdoctor.Doctors;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.elhefny.askdoctor.R;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.squareup.picasso.Picasso;

public class DepartmentDoctorsAdapter extends FirestoreRecyclerAdapter<Doctor, DepartmentDoctorsAdapter.DoctorViewHolder> {

    private Context context;
    DoctorInterface doctorInterface;

    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public DepartmentDoctorsAdapter(Context context, DoctorInterface doctorInterface, @NonNull FirestoreRecyclerOptions<Doctor> options) {
        super(options);
        this.context = context;
        this.doctorInterface = doctorInterface;
    }

    @Override
    protected void onBindViewHolder(@NonNull DoctorViewHolder holder, int position, @NonNull Doctor model) {
        holder.tv_name.setText(model.getName());
        ColorGenerator generator = ColorGenerator.MATERIAL;
        int color1 = generator.getRandomColor();
        TextDrawable.IBuilder builder = TextDrawable.builder()
                .beginConfig()
                .withBorder(4)
                .endConfig()
                .round();
        TextDrawable ic1 = builder.build(model.getName().trim().toUpperCase().substring(0, 1), color1);
        holder.iv_doctor.setImageDrawable(ic1);
        holder.itemView.setAnimation(AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doctorInterface.getClickedDoctor(model);
            }
        });

    }

    @NonNull
    @Override
    public DoctorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_persons_details_design, parent, false);
        return new DoctorViewHolder(v);
    }


    class DoctorViewHolder extends RecyclerView.ViewHolder {
        ImageView iv_doctor;
        TextView tv_name;

        public DoctorViewHolder(@NonNull View itemView) {
            super(itemView);
            iv_doctor = itemView.findViewById(R.id.recycler_person_detail_design_iv_person);
            tv_name = itemView.findViewById(R.id.recycler_person_detail_design_tv_person_name);
        }
    }

    public interface DoctorInterface {
        void getClickedDoctor(Doctor clickedDoctor);
    }
}
