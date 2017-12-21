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
import android.widget.SearchView;
import android.widget.TextView;

import com.example.wyacheslav.testappforinternetfregat.R;
import com.example.wyacheslav.testappforinternetfregat.adapters.RecyclerViewListManAdapter;
import com.example.wyacheslav.testappforinternetfregat.database.HelperFactory;
import com.example.wyacheslav.testappforinternetfregat.models.Man;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Фрагмент со списком людей
 * Created by bonda on 11.12.2017.
 */
public class ListManFragment extends Fragment implements SearchView.OnQueryTextListener {
    /**
     * Массив людей
     */
    private List<Man> mManModelsList;

    /**
     * Адаптер списка
     */
    private RecyclerViewListManAdapter mRecyclerViewClientAdapter;

    /**
     * Информационная строка
     */
    private TextView mTextViewEmptyList;

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

        // Заполнение массива из БД
        try {
            mManModelsList.addAll(HelperFactory.getHelper().getInstanceManDAO().getAllMan());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Информационная строка и поле поиска
        mTextViewEmptyList = rootView.findViewById(R.id.tv_empty_list);
        SearchView searchView = rootView.findViewById(R.id.sv_man);
        searchView.setOnQueryTextListener(this);

        // Инициализация адаптера
        mRecyclerViewClientAdapter = new RecyclerViewListManAdapter(mManModelsList, getFragmentManager(), getContext());

        // FAB добавления человека
        FloatingActionButton floatingActionButtonAddMan = rootView.findViewById(R.id.fab_add_man);

        // Список
        RecyclerView recyclerViewMans = rootView.findViewById(R.id.rv_mans);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        RecyclerView.ItemAnimator itemAnimator = new DefaultItemAnimator();
        recyclerViewMans.setAdapter(mRecyclerViewClientAdapter);
        recyclerViewMans.setLayoutManager(layoutManager);
        recyclerViewMans.setItemAnimator(itemAnimator);

        // Если список пуст, будет выведена строка
        if (mManModelsList.isEmpty()) {
            mTextViewEmptyList.setVisibility(View.VISIBLE);
        }

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

    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        // Отчистка массива
        mManModelsList.clear();

        // Заполнение массива из БД
        try {
            mManModelsList
                    .addAll(HelperFactory
                            .getHelper()
                            .getInstanceManDAO()
                            .getManByName(s));
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Если не найдено соответствий - вывод сообщения
        if (mManModelsList.isEmpty()) {
            mTextViewEmptyList.setVisibility(View.VISIBLE);
            mTextViewEmptyList.setText(R.string.not_find_result);
        } else {
            mTextViewEmptyList.setVisibility(View.GONE);
        }

        // Обновление списка
        mRecyclerViewClientAdapter.notifyDataSetChanged();
        return false;
    }
}
