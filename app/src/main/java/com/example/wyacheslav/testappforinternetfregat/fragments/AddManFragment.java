package com.example.wyacheslav.testappforinternetfregat.fragments;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.wyacheslav.testappforinternetfregat.R;
import com.example.wyacheslav.testappforinternetfregat.events.SetIconEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Calendar;

/**
 * Фрагмент с карточкой добавления клиента
 * Created by wyacheslav on 11.12.17.
 */
public class AddManFragment extends Fragment {
    /**
     * Диалоговое окно календаря
     */
    private DatePickerDialog mDatePickerDialog;

    /**
     * Дата рождения
     */
    private Calendar mCalendarDateOfBirth = Calendar.getInstance();

    /**
     * Картинка пользователя
     */
    private ImageView imageViewIconProfile;

    /**
     * Переопределение метода родительского класса
     *
     * @return - View для контейнера
     */
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Создаваемый View
        View rootView = inflater.inflate(R.layout.fragment_add_man, container, false);

        // Подписка на события
        EventBus.getDefault().register(this);

        // Текстовое поле с датой рождения
        final TextView textViewDOB = rootView.findViewById(R.id.tv_dob);
        imageViewIconProfile = rootView.findViewById(R.id.iv_icon);

        // Обработка нажатия на текстовое поле
        textViewDOB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDatePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int mouth, int day) {
                        // Сохранение даты
                        mCalendarDateOfBirth.set(Calendar.YEAR, year);
                        mCalendarDateOfBirth.set(Calendar.MONTH, mouth);
                        mCalendarDateOfBirth.set(Calendar.DAY_OF_MONTH, day);

                        // Обновление текста
                        textViewDOB.setText(DateUtils.formatDateTime(getActivity(), mCalendarDateOfBirth.getTimeInMillis(), DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_SHOW_YEAR));
                    }
                }, mCalendarDateOfBirth.get(Calendar.YEAR), mCalendarDateOfBirth.get(Calendar.MONTH), mCalendarDateOfBirth.get(Calendar.DAY_OF_MONTH));

                // Показывает диалоговое окно
                mDatePickerDialog.show();
            }
        });

        // Обработка нажатия на иконку профиля
        imageViewIconProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, 1);
            }
        });
        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // Отписка от событий
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(SetIconEvent event) {
        imageViewIconProfile.setImageBitmap(event.getBitmapIcon());
    }
}
