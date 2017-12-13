package com.example.wyacheslav.testappforinternetfregat.models;

import android.content.Context;
import android.graphics.Bitmap;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Класс человека
 * Created by wyacheslav on 11.12.17.
 */
@DatabaseTable(tableName = "mans")
public class Man {
    /**
     * Класс, используемый для получения BitMap
     */
    private Context context;
    /**
     * Строка с названием столбца
     */
    public final static String MAN_NAME_FIELD_NAME = "name";

    /**
     * id
     * Имя
     * Фамилия
     * Отчество
     * Дата рождения
     * Адрес
     * Фото
     * Количество заказанных веников пользователя
     */
    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField(canBeNull = false, dataType = DataType.STRING, columnName = MAN_NAME_FIELD_NAME)
    private String name;

    @DatabaseField(dataType = DataType.STRING)
    private String secondName;

    @DatabaseField(dataType = DataType.STRING)
    private String patronymic;

    @DatabaseField(dataType = DataType.STRING)
    private String dateOfBirth;

    @DatabaseField(dataType = DataType.STRING)
    private String Address;

    @DatabaseField(dataType = DataType.STRING)
    private String Photo;

    @DatabaseField(dataType = DataType.INTEGER)
    private int numberOfBrooms;

    public Man() {
    }

    public Man(Context context) {
        this.context = context;
    }

    public String getFullName() {
        return isNullString(getSecondName()) + " " + isNullString(getName()) + " " + isNullString(getPatronymic());
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getPhoto() {
        return Photo;
    }

    public void setPhoto(String photo) {
        Photo = photo;
    }

    public Bitmap getBitmapIcon() {
        // TODO: доработать метод
        Bitmap bitmap = null;
        if (bitmap == null) {
          //  bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_launcher);
        }
        return bitmap;
    }

    public int getNumberOfBrooms() {
        return numberOfBrooms;
    }

    public void setNumberOfBrooms(int numberOfBrooms) {
        this.numberOfBrooms = numberOfBrooms;
    }

    private String isNullString(String string) {
        return string == null ? "" : string;
    }
}
