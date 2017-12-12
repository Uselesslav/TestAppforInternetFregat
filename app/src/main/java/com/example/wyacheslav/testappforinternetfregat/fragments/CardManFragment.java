package com.example.wyacheslav.testappforinternetfregat.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.wyacheslav.testappforinternetfregat.R;

/**
 * Фрагмент с карточкой человека
 * Created by wyacheslav on 11.12.17.
 */
public class CardManFragment extends Fragment {
    /**
     * Переопределение метода родительского класса
     *
     * @return - View для контейнера
     */
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Создаваемый View
        View rootView = inflater.inflate(R.layout.fragment_card_man, container, false);

        // id элемента в БД
        int position = getArguments().getInt("position");

        return rootView;
    }
}

