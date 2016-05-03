package com.enjoyor.healthhouse.dao;

/**
 * Created by Administrator on 2016/4/25.
 */

import android.content.Context;
import android.util.Log;

import com.enjoyor.healthhouse.bean.User;
import com.enjoyor.healthhouse.db.DatabaseHelper;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;

public class UserDao
{
    private Context context;
    private Dao<User, Integer> userDaoOpe;
    private DatabaseHelper helper;

    public UserDao(Context context)
    {
        this.context = context;
        try
        {
            helper = DatabaseHelper.getHelper(context);
            userDaoOpe = helper.getDao(User.class);
            Log.i("zxw","----------> db dao");
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * ����һ���û�
     * @param user
     */
    public void add(User user)
    {
        Log.i("zxw","----------> db created");
        try
        {
            userDaoOpe.create(user);
            Log.i("zxw","----------> db created");
        } catch (SQLException e)
        {
            e.printStackTrace();
            Log.i("zxw", "----------> db created  XX");
            Log.i("zxw", "----------> db created  XX"+e.getMessage());
        }

    }
    public User get(){
        User user = null;
        try
        {
            user = userDaoOpe.queryForId(1);
            Log.i("zxw","----------> db geted");

        } catch (SQLException e)
        {
            e.printStackTrace();
        }
        return user;
    }


}
