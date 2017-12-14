package com.example.wyacheslav.testappforinternetfregat.models;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.text.format.DateUtils;

import com.example.wyacheslav.testappforinternetfregat.R;
import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Calendar;
import java.util.Random;

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
     * Дата рождения
     */
    private Calendar calendarDoB = Calendar.getInstance();

    /**
     * Строки с названиями столбца
     */
    public final static String MAN_NAME_FIELD_NAME = "name";
    public final static String MAN_NAME_FIELD_SECOND_NAME = "second_name";
    public final static String MAN_NAME_FIELD_PATRONYMIC = "patronymic";

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
    @DatabaseField(dataType = DataType.STRING, columnName = MAN_NAME_FIELD_SECOND_NAME)
    private String secondName;
    @DatabaseField(dataType = DataType.STRING, columnName = MAN_NAME_FIELD_PATRONYMIC)
    private String patronymic;
    @DatabaseField(dataType = DataType.STRING)
    private String timeInMillisecond;
    @DatabaseField(dataType = DataType.STRING)
    private String Address;
    @DatabaseField(dataType = DataType.STRING)
    private String pathToBitmap;
    @DatabaseField(dataType = DataType.INTEGER)
    private int numberOfBrooms;

    /**
     * Пустой конструктор для ДАО объекта
     */
    public Man() {
    }

    public Man(Context context) {
        this.context = context;
    }

    public void setContext(Context context) {
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
        return DateUtils.formatDateTime(context, calendarDoB.getTimeInMillis(), DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_SHOW_YEAR);
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getPathToBitmap() {
        return pathToBitmap;
    }

    public void setPathToBitmap(String pathToBitmap) {
        this.pathToBitmap = pathToBitmap;
    }

    public int getNumberOfBroomsInt() {
        return numberOfBrooms;
    }

    public String getNumberOfBroomsString() {
        return Integer.toString(numberOfBrooms);
    }

    public void setNumberOfBrooms(int numberOfBrooms) {
        this.numberOfBrooms = numberOfBrooms;
    }

    private String isNullString(String string) {
        return string == null ? "" : string;
    }

    public void setTimeInMillisecond(long timeInMillisecond) {
        this.timeInMillisecond = String.valueOf(timeInMillisecond);
    }

    public long getTimeInMillisecond() {
        return Long.valueOf(timeInMillisecond);
    }


    /**
     * Сохранение картинки на внутреннее хранилище
     *
     * @param bitmap - картинка
     * @return - путь к файлу
     */
    public String saveIconBitmap(Bitmap bitmap) {
        // Путь
        String path = null;

        if (bitmap != null) {
            // Путь к хранилищу
            String root = Environment.getExternalStorageDirectory().toString();

            // Создание папки
            File myDir = new File(root + "/saved_images");
            myDir.mkdirs();

            // Создание названия
            Random generator = new Random();
            int n = 10000;
            n = generator.nextInt(n);
            String fileName = "Image-" + n + ".jpg";

            // Создание файла
            File file = new File(myDir, fileName);
            if (file.exists()) file.delete();

            // Сохранение картинки
            try {
                FileOutputStream out = new FileOutputStream(file);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
                out.flush();
                out.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

            // Путь
            path = root + "/saved_images/" + fileName;
        }

        return path;
    }

    /**
     * ВОзвращает иконку пользователя из внутреннего хранилища
     *
     * @return - иконка
     */
    public Bitmap getIconBitmap() {
        Bitmap bitmap = null;
        if (getPathToBitmap() == null) {
            bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_launcher);
        } else {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inPreferredConfig = Bitmap.Config.ARGB_8888;
            bitmap = BitmapFactory.decodeFile(getPathToBitmap(), options);
        }
        return bitmap;
    }
}
