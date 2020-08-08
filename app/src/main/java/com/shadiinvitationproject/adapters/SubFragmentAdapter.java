package com.shadiinvitationproject.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.shadiinvitationproject.R;
import com.shadiinvitationproject.db.DatabaseAdapter;
import com.shadiinvitationproject.network.Retrofit.ResponseModels.Result;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class SubFragmentAdapter extends RecyclerView.Adapter<SubFragmentAdapter.MyViewHolder> {

    OnItemClickListener onItemClickListener;
    private List<Result> resultList;
    private ArrayList<Result> resultArrayList;
    private String strPosition;

    public SubFragmentAdapter(List<Result> resultList, OnItemClickListener onItemClickListener, String position) {
        this.resultList = resultList;
        this.strPosition = position;
        this.resultArrayList = new ArrayList<Result>();
        this.resultArrayList.addAll(resultList);
        this.onItemClickListener = onItemClickListener;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.match_item, parent, false);
        final MyViewHolder myViewHolder = new MyViewHolder(itemView, onItemClickListener);

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Result result = resultList.get(position);
        String name = result.getName().getFirst() + " " + result.getName().getLast().charAt(0);
        String details = result.getDob().getAge() + ", " + result.getLocation().getStreet().getName() + ", " +
                result.getLocation().getCity() + ", " + result.getLocation().getState() + ", \n" + result.getLocation().getCountry() + ".";

        if (strPosition.equals("1")) {
            holder.ibAccept.setVisibility(View.GONE);
            holder.ibDecline.setVisibility(View.GONE);
            holder.ibRemove.setVisibility(View.VISIBLE);
        }

        Picasso.get()
                .load(result.getPicture().getMedium())
                .placeholder(R.drawable.profile)
                .error(R.drawable.profile)
                .into(holder.imProfile);

        holder.tvName.setText(name);
        holder.tvDetails.setText(details);

    }

    @Override
    public int getItemCount() {
        return resultList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView imProfile;
        private TextView tvName, tvDetails;
        private ImageButton ibAccept, ibDecline, ibRemove;

        MyViewHolder(@NonNull final View itemView, OnItemClickListener onItemClickListener) {
            super(itemView);
            imProfile = itemView.findViewById(R.id.imProfile);
            tvName = itemView.findViewById(R.id.tvName);
            tvDetails = itemView.findViewById(R.id.tvDetails);
            ibAccept = itemView.findViewById(R.id.ibAccept);
            ibDecline = itemView.findViewById(R.id.ibDecline);
            ibRemove = itemView.findViewById(R.id.ibRemove);

            ibAccept.setOnClickListener(this);
            ibDecline.setOnClickListener(this);
            ibRemove.setOnClickListener(this);
            imProfile.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

            switch (view.getId()) {

                case R.id.ibAccept:
                    onItemClickListener.onAcceptClick(resultList.get(getAdapterPosition()));
                    break;

                case R.id.ibDecline:
                    onItemClickListener.onDeclineClick(resultList.get(getAdapterPosition()));
                    break;

                case R.id.ibRemove:
                    onItemClickListener.onRemoveClick(resultList.get(getAdapterPosition()));
                    break;

                case R.id.imProfile:
                    onItemClickListener.onImageClick(resultList.get(getAdapterPosition()).getPicture().getLarge());
                    break;

                default:

                    break;
            }
        }

    }

    // search filter for save match list
    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        resultList.clear();
        if (charText.length() == 0) {
            resultList.addAll(resultArrayList);
        } else {
            for (Result wp : resultArrayList) {
                if (wp.getName().getFirst().toLowerCase(Locale.getDefault()).contains(charText)) {
                    resultList.add(wp);
                }
            }
        }
        notifyDataSetChanged();
    }

    public interface OnItemClickListener {
        void onDeclineClick(Result result);

        void onAcceptClick(Result result);

        void onRemoveClick(Result result);

        void onImageClick(String strURL);
    }

    public void notifyData(Result result) {
        resultList.remove(result);
        resultArrayList.remove(result);
        notifyDataSetChanged();
    }

}
