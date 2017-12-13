package com.example.wyacheslav.testappforinternetfregat.database.DAO;


import com.example.wyacheslav.testappforinternetfregat.models.Man;
import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by wyacheslav on 13.12.17.
 */
public class ManDAO extends BaseDaoImpl<Man, Integer> {

    public ManDAO(ConnectionSource connectionSource, Class<Man> dataClass) throws SQLException {
        super(connectionSource, dataClass);
    }

    public List<Man> getAllMan() throws SQLException {
        return this.queryForAll();
    }

    public List<Man> getManByName(String name) throws SQLException {
        QueryBuilder<Man, Integer> queryBuilder = queryBuilder();
        queryBuilder.where().eq(Man.MAN_NAME_FIELD_NAME, name);
        PreparedQuery<Man> preparedQuery = queryBuilder.prepare();
        List<Man> manList = query(preparedQuery);
        return manList;
    }

    public Man getManByID(int id) throws SQLException {
        QueryBuilder<Man, Integer> queryBuilder = queryBuilder();
        queryBuilder.where().eq("id", id);
        Man man = query(queryBuilder.prepare()).get(0);
        return man;
    }
}
