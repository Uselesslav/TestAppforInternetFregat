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
import android.widget.EditText;
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
public class ListManFragment extends Fragment {
    /**
     * Массив людей
     */
    private List<Man> mManModelsList;

    /**
     * Адаптер списка
     */
    private RecyclerViewListManAdapter mRecyclerViewClientAdapter;

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

        // Информационная строка
        final TextView textViewEmptyList = rootView.findViewById(R.id.tv_empty_list);

        // Поле ввода и кнопка для поиска
        TextView textViewFind = rootView.findViewById(R.id.tv_find);
        final EditText editTextFind = rootView.findViewById(R.id.et_find);

        // Инициализация адаптера
        mRecyclerViewClientAdapter = new RecyclerViewListManAdapter(mManModelsList, getFragmentManager(), getContext());

        // FAB добавления человека
        FloatingActionButton floatingActionButtonAddMan = rootView.findViewById(R.id.fab_add_man);

        // Список
        RecyclerView recyclerViewMans = rootView.findViewById(R.id.rv_mans);

        // Настройка списка
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        RecyclerView.ItemAnimator itemAnimator = new DefaultItemAnimator();
        recyclerViewMans.setAdapter(mRecyclerViewClientAdapter);
        recyclerViewMans.setLayoutManager(layoutManager);
        recyclerViewMans.setItemAnimator(itemAnimator);

        // Если список пуст, будет выведена строка
        if (mManModelsList.isEmpty()) {
            textViewEmptyList.setVisibility(View.VISIBLE);
        }

        // Обработчик нажатия на кнопку добавить человека
        floatingActionButtonAddMan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Открытие фрагмента с добавлением клиента
                getFragmentManager().beginTransaction().replace(R.id.fl_container, new AddManFragment()).addToBackStack(null).commit();
            }
        });

        // Обработчик нажатия на кнопку "Найти"
        textViewFind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Отчистка массива
                mManModelsList.clear();

                // Заполнение массива из БД
                try {
                    mManModelsList
                            .addAll(HelperFactory
                                    .getHelper()
                                    .getInstanceManDAO()
                                    .getManByName(editTextFind
                                            .getText()
                                            .toString()));
                } catch (SQLException e) {
                    e.printStackTrace();
                }

                // Если не найдено соответствий - вывод сообщения
                if (mManModelsList.isEmpty()) {
                    textViewEmptyList.setVisibility(View.VISIBLE);
                    textViewEmptyList.setText(R.string.not_find_result);
                }

                // Обновление списка
                mRecyclerViewClientAdapter.notifyDataSetChanged();
            }
        });

        return rootView;
    }
}
