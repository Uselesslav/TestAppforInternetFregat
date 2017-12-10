package com.example.wyacheslav.testappforinternetfregat.models;

/**
 * Класс человека
 * Created by wyacheslav on 11.12.17.
 */
public class ManModel {
    /**
     * Имя
     * Фамилия
     * Отчество
     * Дата рождения
     * Адрес
     * Фото
     * Количество заказанных веников пользователя
     */
    private String name;
    private String secondName;
    private String patronymic;
    private String dateOfBirth;
    private String Address;
    private String Photo;
    private int numberOfBrooms;

    public String getFullName() {
        return isNullString(getSecondName()) + " " + isNullString(getName()) + " " + isNullString(getPatronymic());
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