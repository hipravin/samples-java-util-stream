package com.hipravin.post.reader.dbf;

import com.linuxense.javadbf.DBFReader;
import com.linuxense.javadbf.DBFRow;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class DbfRowIterator implements Iterator<DBFRow> {
    private final DBFReader reader;

    boolean valueReady = false;
    private DBFRow nextRow;

    public DbfRowIterator(DBFReader reader) {
        this.reader = reader;
    }

    @Override
    public boolean hasNext() {
        if (!valueReady) {
            nextRow = reader.nextRow();
            valueReady = true;
        }

        return nextRow != null;
    }

    @Override
    public DBFRow next() {
        if (!valueReady && !hasNext()) {
            throw new NoSuchElementException();
        } else {
            valueReady = false;

            DBFRow row = nextRow;
            nextRow = null;
            return row;
        }
    }
}
