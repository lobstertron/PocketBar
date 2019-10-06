package com.core.database;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "bar")
public class Bar {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "name")
    private String name;

    public Bar(String name) {
        this.name = name;
    }

    @NonNull
    public String getName() {
        return name;
    }
}
