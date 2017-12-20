package com.example.wyacheslav.testappforinternetfregat.fragments;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.ImageView;

import com.example.wyacheslav.testappforinternetfregat.R;
import com.example.wyacheslav.testappforinternetfregat.database.HelperFactory;
import com.example.wyacheslav.testappforinternetfregat.events.SetIconEvent;
import com.example.wyacheslav.testappforinternetfregat.models.Man;
import com.mobsandgeeks.saripaar.Rule;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Required;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.sql.SQLException;
import java.util.Calendar;

/**
 * Фрагмент с карточкой человека
 * Created by wyacheslav on 11.12.17.
 */
public class CardManFragment extends Fragment implements Validator.ValidationListener {
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
    private ImageView mImageViewIcon;

    /**
     * Контекст
     */
    private Context mContext;

    /**
     * Валидатор
     */
    private Validator mValidator;

    /**
     * Объект человека
     */
    private Man mMan;

    /**
     * Картинка пользователя
     */
    private Bitmap mBitmapIcon = null;

    /**
     * Поля ввода
     */
    @Required(order = 1)
    private TextInputEditText mEditTextName;
    @Required(order = 2)
    private TextInputEditText mEditTextSecondName;
    @Required(order = 3)
    private TextInputEditText mEditTextNumberOfBroom;
    private TextInputEditText mEditTextAddress;
    private TextInputEditText mEditTextPatronymic;

    /**
     * Переопределение метода родительского класса
     *
     * @return - View для контейнера
     */
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Создаваемый View
        View rootView = inflater.inflate(R.layout.fragment_card_man, container, false);

        // Получение контекста
        mContext = getContext();

        // id элемента в БД
        int position = getArguments().getInt("position");

        // Инициализация человека
        mMan = new Man(mContext);

        // Создание человека из ДАО объекта
        try {
            mMan = HelperFactory.getHelper().getInstanceManDAO().getManByID(position);
            mMan.setContext(mContext);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Инициализация валидатора
        mValidator = new Validator(this);
        mValidator.setValidationListener(this);

        // Подписка на события
        EventBus.getDefault().register(this);

        // Поля ввода
        final TextInputEditText editTextDOB = rootView.findViewById(R.id.et_dob);
        final FloatingActionButton floatingActionButtonEdit = rootView.findViewById(R.id.fab_add_edit);
        mImageViewIcon = rootView.findViewById(R.id.iv_icon);
        // Проверка получено ли разрешение на работу с внешним хранилищем
        if (!(ContextCompat.checkSelfPermission(mContext, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) && (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)) {
            mImageViewIcon.setVisibility(View.GONE);
        }
        mEditTextName = rootView.findViewById(R.id.et_name);
        mEditTextSecondName = rootView.findViewById(R.id.et_second_name);
        mEditTextPatronymic = rootView.findViewById(R.id.et_patronymic);
        mEditTextAddress = rootView.findViewById(R.id.et_address);
        mEditTextNumberOfBroom = rootView.findViewById(R.id.et_number_of_brooms);

        // Заполнение полей
        editTextDOB.setText(mMan.getDateOfBirth());
        mEditTextName.setText(mMan.getName());
        mEditTextSecondName.setText(mMan.getSecondName());
        mEditTextPatronymic.setText(mMan.getPatronymic());
        mEditTextAddress.setText(mMan.getAddress());
        mEditTextNumberOfBroom.setText(mMan.getNumberOfBroomsString());
        mImageViewIcon.setImageBitmap(mMan.getIconBitmap());

        // Обработка нажатия на текстовое поле
        editTextDOB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Текущая дата
                Calendar calendarToday = Calendar.getInstance();

                // Заполнение старым значением
                mCalendarDateOfBirth.setTimeInMillis(mMan.getTimeInMillisecond());

                // Выбор значения
                calendarToday = mCalendarDateOfBirth.getTimeInMillis() > 0 ? mCalendarDateOfBirth : calendarToday;

                // Создание диалогового окна с выбором даты
                mDatePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int mouth, int day) {
                        // Сохранение даты
                        mCalendarDateOfBirth.set(Calendar.YEAR, year);
                        mCalendarDateOfBirth.set(Calendar.MONTH, mouth);
                        mCalendarDateOfBirth.set(Calendar.DAY_OF_MONTH, day);

                        mMan.setTimeInMillisecond(mCalendarDateOfBirth.getTimeInMillis());
                        editTextDOB.setText(DateUtils.formatDateTime(getActivity(), mCalendarDateOfBirth.getTimeInMillis(), DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_SHOW_YEAR));
                    }
                }, calendarToday.get(Calendar.YEAR), calendarToday.get(Calendar.MONTH), calendarToday.get(Calendar.DAY_OF_MONTH));

                // Открытие диалогового окна
                mDatePickerDialog.show();
            }
        });

        // Обработка нажатия на иконку профиля
        mImageViewIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Открытие активити для выбора фото
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, 1);
            }
        });

        // Обработка нажатия на кнопку
        floatingActionButtonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mValidator.validate();
            }
        });

        return rootView;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // Отписка от событий
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(SetIconEvent event) {
        mImageViewIcon.setImageBitmap(event.getBitmapIcon());
        mBitmapIcon = event.getBitmapIcon();
    }

    @Override
    public void onValidationSucceeded() {
        // Заполнение информацией из полей
        mMan.setAddress(mEditTextAddress.getText().toString());
        mMan.setName(mEditTextName.getText().toString());
        mMan.setSecondName(mEditTextSecondName.getText().toString());
        mMan.setPatronymic(mEditTextPatronymic.getText().toString());
        mMan.setNumberOfBrooms(Integer.parseInt(mEditTextNumberOfBroom.getText().toString()));
        mMan.setPathToBitmap(mMan.saveIconBitmap(mBitmapIcon));

        // Создание ДАО человека, для сохранение в БД
        try {
            HelperFactory.getHelper().getInstanceManDAO().update(mMan);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Открытие предыдущего фрагмента
        getFragmentManager().popBackStack();

        // Скрытие клавиатуры
        View view = getActivity().getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    @Override
    public void onValidationFailed(View failedView, Rule<?> failedRule) {
        // Вывод сообщения при непройденной валидации
        TextInputEditText failed = (TextInputEditText) failedView;
        failed.requestFocus();
        failed.setError(getText(R.string.required_field));
    }
}

