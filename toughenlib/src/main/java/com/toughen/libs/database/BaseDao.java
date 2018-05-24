package com.toughen.libs.database;

import android.database.sqlite.SQLiteDatabase;

import java.lang.reflect.Field;

public abstract class BaseDao {
    protected abstract void createTable(SQLiteDatabase db);

    protected abstract void dropTable(SQLiteDatabase db);

    private ThreadLocal<SQLiteDatabase> threadLocal = new ThreadLocal<>();

    public void setLocalThreadDB(SQLiteDatabase database) {
        if (database == null) return;
        if (threadLocal.get() == null) {
            threadLocal.set(database);
        }
    }

    protected SQLiteDatabase getLocalDB() {
        return threadLocal.get();
    }

    public BaseDao() {
        MySQLiteOpenHelper.addTables(this);
    }

    public String getCreateTableSQL(Class tableCalss) {
        if (tableCalss == null) return null;
        String tableName = tableCalss.getSimpleName().toUpperCase();
        StringBuffer SQL = new StringBuffer("CREATE TABLE IF NOT EXISTS ").append(tableName);
        Field[] fields = tableCalss.getDeclaredFields();
        if (fields != null && fields.length > 0) {
            SQL.append("(");
            for (int i = 0; i < fields.length; i++) {
                String type = fields[i].getType().toString();
                String name = fields[i].getName();
                if ("int".equalsIgnoreCase(type) || "integer".equalsIgnoreCase(type))
                    type = "INTEGER";
                else type = "TEXT";
                SQL.append(" ").append(name.toUpperCase()).append(" ").append(type);
                if ("id".equalsIgnoreCase(name)) SQL.append(" PRIMARY KEY");
                SQL.append(" NOT NULL").append(i == fields.length - 1 ? ");" : ",");
            }
        }
        return SQL.toString();
    }

    public String getDropTableSQL(Class tableCalss) {
        if (tableCalss == null) return null;
        String tableName = tableCalss.getSimpleName().toUpperCase();
        StringBuffer SQL = new StringBuffer("DROP TABLE IF EXISTS ").append(tableName);
        return SQL.toString();
    }
}
