package com.example.wyacheslav.testappforinternetfregat.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.wyacheslav.testappforinternetfregat.R;
import com.example.wyacheslav.testappforinternetfregat.models.ManModel;

import java.util.List;

/**
 * Адаптер списка людей
 * Created by wyacheslav on 11.12.17.
 */
public class RecyclerViewListManAdapter extends RecyclerView.Adapter<RecyclerViewListManAdapter.ViewHolder> {
    /**
     * Список людей
     */
    private List<ManModel> mManModels;

    /**
     * Внутренний класс элемента списка
     */
    static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView textViewFullName;

        ViewHolder(View v) {
            super(v);
            textViewFullName = itemView.findViewById(R.id.tv_full_name);
        }

        TextView getTextViewFullName() {
            return textViewFullName;
        }

        void setTextViewFullName(TextView textViewFullName) {
            this.textViewFullName = textViewFullName;
        }
    }

    public RecyclerViewListManAdapter(List<ManModel> manModels) {
        mManModels = manModels;
    }

    @Override
    public RecyclerViewListManAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.model_man_for_list, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.getTextViewFullName().setText(mManModels.get(position).getFullName());

    }

    @Override
    public int getItemCount() {
        return mManModels.size();
    }
}

