package com.example.wyacheslav.testappforinternetfregat.database.DAO;


import com.example.wyacheslav.testappforinternetfregat.models.Man;
import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;
import java.util.List;

/**
 * ДАО объект человека
 * Created by wyacheslav on 13.12.17.
 */
public class ManDAO extends BaseDaoImpl<Man, Integer> {
    public ManDAO(ConnectionSource connectionSource, Class<Man> dataClass) throws SQLException {
        super(connectionSource, dataClass);
    }

    /**
     * Возвращает всех людей из таблицы
     *
     * @return список людей
     */
    public List<Man> getAllMan() throws SQLException {
        return this.queryForAll();
    }

    /**
     * Возвращает по id
     *
     * @param id - индекс в таблице
     * @return Объект человека
     */
    public Man getManByID(int id) throws SQLException {
        QueryBuilder<Man, Integer> queryBuilder = queryBuilder();
        queryBuilder.where().eq("id", id);
        Man man = query(queryBuilder.prepare()).get(0);
        return man;
    }

    /**
     * Ищет совпадения в фамилии, имени и отчестве
     *
     * @param string - строка, которая будет искаться
     * @return Список людей
     */
    public List<Man> getManByName(String string) throws SQLException {
        string = addFilterKey(string);
        QueryBuilder<Man, Integer> queryBuilder = queryBuilder();
        queryBuilder.where()
                .like(Man.MAN_NAME_FIELD_NAME, string)
                .or().like(Man.MAN_NAME_FIELD_SECOND_NAME, string)
                .or().like(Man.MAN_NAME_FIELD_PATRONYMIC, string);
        return query(queryBuilder.prepare());
    }

    /**
     * Добавляет к строке фильтр для поиска
     */
    private String addFilterKey(String string) {
        return "%" + string + "%";
    }
}
