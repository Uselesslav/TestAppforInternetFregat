package com.example.wyacheslav.testappforinternetfregat.adapters;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.wyacheslav.testappforinternetfregat.R;
import com.example.wyacheslav.testappforinternetfregat.fragments.CardManFragment;
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
     * Менеджер фрагментов
     */
    private FragmentManager mFragmentManager;

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

    public RecyclerViewListManAdapter(List<ManModel> manModels, FragmentManager fragmentManager) {
        mManModels = manModels;
        mFragmentManager = fragmentManager;
    }

    @Override
    public RecyclerViewListManAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.model_man_for_list, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        // Заполнение текстового поля элемента
        holder.getTextViewFullName().setText(mManModels.get(position).getFullName());

        // Обработчик нажатия на элемент
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Контейнер, передающий id элемента во фрагмент
                Bundle bundle = new Bundle();
                bundle.putInt("position", position + 1);

                // Инициализация и отправка значений
                CardManFragment cardManFragment = new CardManFragment();
                cardManFragment.setArguments(bundle);

                // Открытие карточки человека
                mFragmentManager.beginTransaction().replace(R.id.fl_container, cardManFragment).addToBackStack(null).commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mManModels.size();
    }
}

