package com.example.wyacheslav.testappforinternetfregat;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.wyacheslav.testappforinternetfregat.events.SetIconEvent;
import com.example.wyacheslav.testappforinternetfregat.fragments.ListManFragment;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Открытие фрагмента списка людей
        getSupportFragmentManager().beginTransaction().replace(R.id.fl_container, new ListManFragment()).commit();
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            try {
                // Создание события смены картинки
                EventBus.getDefault().post(new SetIconEvent(MediaStore.Images.Media.getBitmap(getContentResolver(), data.getData())));
            } catch (IOException e) {
                Log.i("TAG", "Some exception " + e);
            }
        }
    }
}