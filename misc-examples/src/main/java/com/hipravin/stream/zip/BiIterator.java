package com.hipravin.stream.zip;

import java.util.Iterator;

public record BiIterator<T1, T2>(
        Iterator<T1> i1,
        Iterator<T2> i2
) implements Iterator<Tuple2<T1,T2>>{
    @Override
    public boolean hasNext() {
        return i1.hasNext() && i2.hasNext();
    }

    @Override
    public Tuple2<T1, T2> next() {
        T1 t1Next = i1.hasNext() ? i1.next() : null;
        T2 t2Next = i2.hasNext() ? i2.next() : null;
        return new Tuple2<>(t1Next, t2Next);
    }
}
