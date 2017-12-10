package com.example.wyacheslav.testappforinternetfregat.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.wyacheslav.testappforinternetfregat.R;

/**
 * Фрагмент со списком людей
 * Created by bonda on 11.12.2017.
 */
public class ListManFragment extends Fragment {
    /**
     * Переопределение метода родительского класса
     *
     * @return - View для контейнера
     */
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Создаваемый View
        View rootView = inflater.inflate(R.layout.fragment_list_man, container, false);

        return rootView;
    }
}
