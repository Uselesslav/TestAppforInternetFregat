package com.example.wyacheslav.testappforinternetfregat.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.wyacheslav.testappforinternetfregat.R;
import com.example.wyacheslav.testappforinternetfregat.adapters.RecyclerViewListManAdapter;
import com.example.wyacheslav.testappforinternetfregat.models.ManModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Фрагмент со списком людей
 * Created by bonda on 11.12.2017.
 */
public class ListManFragment extends Fragment {
    /**
     * Массив людей
     */
    private List<ManModel> mManModelsList;

    /**
     * Адаптер списка
     */
    private RecyclerViewListManAdapter recyclerViewClientAdapter;


    /**
     * Переопределение метода родительского класса
     *
     * @return - View для контейнера
     */
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Создаваемый View
        View rootView = inflater.inflate(R.layout.fragment_list_man, container, false);

        // Инициализация массива людей
        mManModelsList = new ArrayList<>();

        ManModel man = new ManModel(getContext());
        man.setSecondName("ffsa");

        mManModelsList.add(man);
        mManModelsList.add(man);
        mManModelsList.add(man);
        mManModelsList.add(man);

        // Информационная строка
        TextView textViewEmptyList = rootView.findViewById(R.id.tv_empty_list);

        // Если список пуст, вывести строку
        if (mManModelsList.isEmpty()) {
            textViewEmptyList.setVisibility(View.VISIBLE);
        }
        // Инициализация адаптера
        recyclerViewClientAdapter = new RecyclerViewListManAdapter(mManModelsList, getFragmentManager());

        // FAB добавления человека
        FloatingActionButton floatingActionButtonAddMan = rootView.findViewById(R.id.fab_add_man);

        // Список людей
        RecyclerView recyclerViewMans = rootView.findViewById(R.id.rv_mans);

        // Настройки списка
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        RecyclerView.ItemAnimator itemAnimator = new DefaultItemAnimator();
        recyclerViewMans.setAdapter(recyclerViewClientAdapter);
        recyclerViewMans.setLayoutManager(layoutManager);
        recyclerViewMans.setItemAnimator(itemAnimator);

        // Обработчик нажатия на кнопку добавить человека
        floatingActionButtonAddMan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Открытие фрагмента с добавлением клиента
                getFragmentManager().beginTransaction().replace(R.id.fl_container, new AddManFragment()).addToBackStack(null).commit();
            }
        });
        return rootView;
    }
}
