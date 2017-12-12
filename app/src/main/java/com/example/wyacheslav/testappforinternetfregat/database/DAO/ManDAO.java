package com.example.wyacheslav.testappforinternetfregat.database.DAO;


import com.example.wyacheslav.testappforinternetfregat.models.Man;
import com.j256.ormlite.dao.BaseDaoImpl;
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

    public List<Man> getAllRoles() throws SQLException {
        return this.queryForAll();
    }
}
