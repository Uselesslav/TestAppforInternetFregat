package com.example.wyacheslav.testappforinternetfregat.adapters;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.wyacheslav.testappforinternetfregat.R;
import com.example.wyacheslav.testappforinternetfregat.fragments.CardManFragment;
import com.example.wyacheslav.testappforinternetfregat.models.Man;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Адаптер списка людей
 * Created by wyacheslav on 11.12.17.
 */
public class RecyclerViewListManAdapter extends RecyclerView.Adapter<RecyclerViewListManAdapter.ViewHolder> {
    /**
     * Список людей
     */
    private List<Man> mManModels;

    /**
     * Контекст
     */
    private Context mContext;

    /**
     * Менеджер фрагментов
     */
    private FragmentManager mFragmentManager;

    public RecyclerViewListManAdapter(List<Man> manModels, FragmentManager fragmentManager, Context context) {
        mManModels = manModels;
        mFragmentManager = fragmentManager;
        mContext = context;
    }

    @Override
    public RecyclerViewListManAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.model_man_for_list, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        // TODO исправить колхоз
        mManModels.get(position).setContext(mContext);

        // Заполнение текстового поля элемента
        holder.getTextViewFullName().setText(mManModels.get(position).getFullName());

        // Заполнение картинки
        holder.getCircleImageViewIconMan().setImageBitmap(mManModels.get(position).getIconBitmap());

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

    /**
     * Класс элемента списка
     */
    static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView textViewFullName;
        private CircleImageView circleImageViewIconMan;

        ViewHolder(View v) {
            super(v);
            textViewFullName = itemView.findViewById(R.id.tv_full_name);
            circleImageViewIconMan = itemView.findViewById(R.id.civ_icon_man);
        }

        TextView getTextViewFullName() {
            return textViewFullName;
        }

        void setTextViewFullName(TextView textViewFullName) {
            this.textViewFullName = textViewFullName;
        }

        CircleImageView getCircleImageViewIconMan() {
            return circleImageViewIconMan;
        }

        void setCircleImageViewIconMan(CircleImageView circleImageViewIconMan) {
            this.textViewFullName = textViewFullName;
        }
    }
}

