package com.example.wyacheslav.testappforinternetfregat;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.wyacheslav.testappforinternetfregat.fragments.ListManFragment;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Открытие фрагмента списка людей
        getSupportFragmentManager().beginTransaction().add(R.id.fl_container, new ListManFragment()).commit();
    }

    /**
     * Обработка нажатия на кнопку "Назад"
     */
    @Override
    public void onBackPressed() {
        // Проверка есть ли фрагменты в стеке
        if (0 != getSupportFragmentManager().getBackStackEntryCount()) {
            // Открытие верхнего фрагмента из стека
            getSupportFragmentManager().popBackStack();
        } else {
            // Закрытие активити
            this.finish();
        }
    }
}