package com.example.wyacheslav.testappforinternetfregat.fragments;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.wyacheslav.testappforinternetfregat.R;
import com.example.wyacheslav.testappforinternetfregat.database.HelperFactory;
import com.example.wyacheslav.testappforinternetfregat.events.SetIconEvent;
import com.example.wyacheslav.testappforinternetfregat.models.Man;
import com.mobsandgeeks.saripaar.Rule;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Required;
import com.rengwuxian.materialedittext.MaterialEditText;

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
    private ImageView mImageViewIconProfile;

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
    private MaterialEditText mMaterialEditTextName;
    @Required(order = 1)
    private MaterialEditText mMaterialEditTextSecondName;
    @Required(order = 1)
    private MaterialEditText mMaterialEditTextNumberOfBroom;
    @Required(order = 1)
    private MaterialEditText mMaterialEditTextAddress;
    private MaterialEditText mMaterialEditTextPatronymic;

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
        final TextView textViewDOB = rootView.findViewById(R.id.tv_dob);
        final Button buttonEdit = rootView.findViewById(R.id.button_edit);
        mImageViewIconProfile = rootView.findViewById(R.id.iv_icon);
        mMaterialEditTextName = rootView.findViewById(R.id.et_name);
        mMaterialEditTextSecondName = rootView.findViewById(R.id.et_second_name);
        mMaterialEditTextPatronymic = rootView.findViewById(R.id.et_patronymic);
        mMaterialEditTextAddress = rootView.findViewById(R.id.et_address);
        mMaterialEditTextNumberOfBroom = rootView.findViewById(R.id.et_number_of_brooms);

        // Заполнение полей
        textViewDOB.setText(mMan.getDateOfBirth());
        mMaterialEditTextName.setText(mMan.getName());
        mMaterialEditTextSecondName.setText(mMan.getSecondName());
        mMaterialEditTextPatronymic.setText(mMan.getPatronymic());
        mMaterialEditTextAddress.setText(mMan.getAddress());
        mMaterialEditTextNumberOfBroom.setText(mMan.getNumberOfBroomsString());
        mImageViewIconProfile.setImageBitmap(mMan.getIconBitmap());

        // Обработка нажатия на текстовое поле
        textViewDOB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Создание диалогового окна с выбором даты
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

                // Открытие диалогового окна
                mDatePickerDialog.show();
            }
        });

        // Обработка нажатия на иконку профиля
        mImageViewIconProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Открытие активити для выбора фото
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, 1);
            }
        });

        // Обработка нажатия на кнопку
        buttonEdit.setOnClickListener(new View.OnClickListener() {
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
        mImageViewIconProfile.setImageBitmap(event.getBitmapIcon());
        mBitmapIcon = event.getBitmapIcon();
    }

    @Override
    public void onValidationSucceeded() {
        // Заполнение информацией из полей
        mMan.setAddress(mMaterialEditTextAddress.getText().toString());
        mMan.setName(mMaterialEditTextName.getText().toString());
        mMan.setSecondName(mMaterialEditTextSecondName.getText().toString());
        mMan.setPatronymic(mMaterialEditTextPatronymic.getText().toString());
        mMan.setNumberOfBrooms(Integer.parseInt(mMaterialEditTextNumberOfBroom.getText().toString()));
        mMan.setTimeInMillisecond(mCalendarDateOfBirth.getTimeInMillis());
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
        if (failedView instanceof MaterialEditText) {
            MaterialEditText failed = (MaterialEditText) failedView;
            failed.requestFocus();
            failed.setError(getText(R.string.required_field));
        }
    }
}

