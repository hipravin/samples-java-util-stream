package com.hipravin.post.reader.dbf;

import com.linuxense.javadbf.DBFRow;

public interface DbfRowMapper<T> {
    T map(DBFRow row);
}
