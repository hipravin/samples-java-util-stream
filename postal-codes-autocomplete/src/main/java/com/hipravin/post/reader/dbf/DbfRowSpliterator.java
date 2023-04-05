package com.hipravin.post.reader.dbf;

import com.linuxense.javadbf.DBFReader;
import com.linuxense.javadbf.DBFRow;

import java.util.Spliterator;
import java.util.function.Consumer;

public class DbfRowSpliterator implements Spliterator<DBFRow> {
    private final DBFReader reader;

    public DbfRowSpliterator(DBFReader reader) {
        this.reader = reader;
    }

    @Override
    public boolean tryAdvance(Consumer<? super DBFRow> action) {
        DBFRow nextRow = reader.nextRow();
        if (nextRow != null) {
            action.accept(nextRow);
            return true;
        }
        return false;
    }

    @Override
    public Spliterator<DBFRow> trySplit() {
        return null;
    }

    @Override
    public long estimateSize() {
        return Long.MAX_VALUE;
    }

    @Override
    public int characteristics() {
        return 0;
    }
}
