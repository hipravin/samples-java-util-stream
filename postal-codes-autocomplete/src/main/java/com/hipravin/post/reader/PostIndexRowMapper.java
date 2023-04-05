package com.hipravin.post.reader;

import com.hipravin.post.PostIndex;
import com.hipravin.post.reader.dbf.DbfRowMapper;
import com.linuxense.javadbf.DBFRow;

import java.util.Objects;

public class PostIndexRowMapper implements DbfRowMapper<PostIndex> {
    private static final String FIELD_INDEX = "INDEX";
    private static final String FIELD_NAME = "OPSNAME";
    private static final String FIELD_REGION = "REGION";
    private static final String FIELD_AUTONOM = "AUTONOM";
    private static final String FIELD_AREA = "AREA";
    private static final String FIELD_CITY = "CITY";
    private static final String FIELD_CITY_1 = "CITY_1";

    @Override
    public PostIndex map(DBFRow row) {
        Objects.requireNonNull(row);

        return new PostIndex(
                row.getString(FIELD_INDEX),
                row.getString(FIELD_NAME),
                row.getString(FIELD_REGION),
                row.getString(FIELD_AUTONOM),
                row.getString(FIELD_AREA),
                row.getString(FIELD_CITY),
                row.getString(FIELD_CITY_1)
        );
    }
}
