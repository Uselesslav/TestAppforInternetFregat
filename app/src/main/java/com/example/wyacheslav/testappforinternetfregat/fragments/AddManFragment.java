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
 * Фрагмент с карточкой добавления клиента
 * Created by wyacheslav on 11.12.17.
 */
public class AddManFragment extends Fragment implements Validator.ValidationListener {
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
     * Валидатор
     */
    private Validator mValidator;

    /**
     * Переопределение метода родительского класса
     *
     * @return - View для контейнера
     */
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Создаваемый View
        View rootView = inflater.inflate(R.layout.fragment_add_man, container, false);

        // Инициализация валидатора
        mValidator = new Validator(this);
        mValidator.setValidationListener(this);

        // Подписка на события
        EventBus.getDefault().register(this);

        // Поля ввода
        final TextView textViewDOB = rootView.findViewById(R.id.tv_dob);
        final Button buttonAdd = rootView.findViewById(R.id.button_add);
        imageViewIconProfile = rootView.findViewById(R.id.iv_icon);
        mMaterialEditTextName = rootView.findViewById(R.id.et_name);
        mMaterialEditTextSecondName = rootView.findViewById(R.id.et_second_name);
        mMaterialEditTextPatronymic = rootView.findViewById(R.id.et_patronymic);
        mMaterialEditTextAddress = rootView.findViewById(R.id.et_address);
        mMaterialEditTextNumberOfBroom = rootView.findViewById(R.id.et_number_of_brooms);

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

        // Обработка нажатия на кнопку
        buttonAdd.setOnClickListener(new View.OnClickListener() {
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
        imageViewIconProfile.setImageBitmap(event.getBitmapIcon());
    }

    @Override
    public void onValidationSucceeded() {
        Man man = new Man(getContext());
        man.setAddress(mMaterialEditTextAddress.getText().toString());
        man.setDateOfBirth(mCalendarDateOfBirth.toString());
        man.setName(mMaterialEditTextName.getText().toString());
        man.setSecondName(mMaterialEditTextSecondName.getText().toString());
        man.setPatronymic(mMaterialEditTextPatronymic.getText().toString());
        // TODO: Добавить обработку
        man.setPhoto("fff");
        try {
            HelperFactory.getHelper().getManDAO().create(man);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        getFragmentManager().popBackStack();
    }

    @Override
    public void onValidationFailed(View failedView, Rule<?> failedRule) {
        if (failedView instanceof MaterialEditText) ;
        MaterialEditText failed = (MaterialEditText) failedView;
        failed.requestFocus();
        failed.setError(getText(R.string.required_field));
    }
}

