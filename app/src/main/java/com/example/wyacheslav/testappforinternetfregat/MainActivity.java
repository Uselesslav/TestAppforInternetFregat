package com.example.wyacheslav.testappforinternetfregat;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.example.wyacheslav.testappforinternetfregat.events.SetIconEvent;
import com.example.wyacheslav.testappforinternetfregat.fragments.ListManFragment;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    /**
     * Код ответа
     */
    public static final int REQUEST_WRITE_STORAGE = 1;

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
            // Проверка изображение с камеры или из галереи
            if (data.getExtras() != null) {
                // Создание события смены картинки
                EventBus.getDefault().post(new SetIconEvent((Bitmap) data.getExtras().get("data")));
            } else {
                try {
                    // Создание события смены картинки
                    EventBus.getDefault().post(new SetIconEvent(MediaStore.Images.Media.getBitmap(getContentResolver(), data.getData())));
                } catch (IOException e) {
                    Log.i("TAG", "Some exception " + e);
                }
            }
        }
    }


    /**
     * Обработка изменения соглашений
     *
     * @param requestCode  - ответ
     * @param permissions  - соглашение
     * @param grantResults - результат
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        // Проверка принятого соглашения
        switch (requestCode) {
            case REQUEST_WRITE_STORAGE: {
                // Если соглашение отклонено, вывод сообщения с информацией
                if (!(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    Toast.makeText(this, getString(R.string.info_about_permission), Toast.LENGTH_LONG).show();
                }
            }
        }

    }
}