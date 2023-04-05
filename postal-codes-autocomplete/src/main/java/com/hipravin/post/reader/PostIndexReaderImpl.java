package com.hipravin.post.reader;

import com.hipravin.post.PostIndex;
import com.hipravin.post.reader.dbf.DbfRowIterator;
import com.hipravin.post.reader.dbf.DbfRowMapper;
import com.hipravin.post.reader.dbf.DbfRowSpliterator;
import com.linuxense.javadbf.DBFReader;
import com.linuxense.javadbf.DBFRow;

import java.io.BufferedInputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class PostIndexReaderImpl implements PostIndexReader {
    private final Path indexFilePath;
    private final DbfRowMapper<PostIndex> postIndexRowMapper = new PostIndexRowMapper();

    public PostIndexReaderImpl(Path indexFilePath) {
        this.indexFilePath = indexFilePath;
    }

    @Override
    public Stream<PostIndex> readAll() {
        try {
            BufferedInputStream bis = new BufferedInputStream(Files.newInputStream(indexFilePath));
            DBFReader reader = new DBFReader(bis);

            Iterator<DBFRow> dbfRowIterator = new DbfRowIterator(reader);
            return StreamSupport.stream(
                            Spliterators.spliteratorUnknownSize(dbfRowIterator, 0), false)
                    .map(postIndexRowMapper::map)
                    .onClose(() -> closeQuietly(bis))
                    .onClose(() -> closeQuietly(reader));
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    public Stream<PostIndex> readAllStreamSpliterator() {
        try {
            BufferedInputStream bis = new BufferedInputStream(Files.newInputStream(indexFilePath));
            DBFReader reader = new DBFReader(bis);

            return StreamSupport.stream(new DbfRowSpliterator(reader), false)
                    .map(postIndexRowMapper::map)
                    .onClose(() -> closeQuietly(bis))
                    .onClose(() -> closeQuietly(reader));
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    /**
     * Incorrect implementation that close resources with help of try-with-resources block.
     * It throws an exception once client tries to run terminal operation on stream, because then
     * already closed DBFReader tries to read from InputStream that is also already closed.
     * <p>
     * {@code readAll} provides correct implementation with usage of {@code Stream.onClose} method.
     */
    public Stream<PostIndex> readAllStreamNaive() {
        try (BufferedInputStream bis = new BufferedInputStream(Files.newInputStream(indexFilePath));
             DBFReader reader = new DBFReader(bis)) {

            Iterator<DBFRow> dbfRowIterator = new DbfRowIterator(reader);
            return StreamSupport.stream(
                            Spliterators.spliteratorUnknownSize(dbfRowIterator, 0), false)
                    .map(postIndexRowMapper::map);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    //looks good, but subtly incorrect. Supplier should be stateless
    public Stream<PostIndex> readAllStreamHackingGenerate() {
        try {
            BufferedInputStream bis = new BufferedInputStream(Files.newInputStream(indexFilePath));
            DBFReader reader = new DBFReader(bis);

            return Stream.generate(() -> reader.nextRow())
                    .takeWhile(r -> r != null)
                    .map(postIndexRowMapper::map)
                    .onClose(() -> closeQuietly(bis))
                    .onClose(() -> closeQuietly(reader));
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    @Override
    public List<PostIndex> readAllList() {
        List<PostIndex> result = new ArrayList<>();
        try (BufferedInputStream bis = new BufferedInputStream(Files.newInputStream(indexFilePath));
             DBFReader reader = new DBFReader(bis)) {

            DBFRow dbfRow;
            while ((dbfRow = reader.nextRow()) != null) {
                result.add(postIndexRowMapper.map(dbfRow));
            }
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }

        return result;
    }

    @Override
    public void readAll(Predicate<? super PostIndex> acceptPostIndex, Consumer<? super PostIndex> handler) {
        try (BufferedInputStream bis = new BufferedInputStream(Files.newInputStream(indexFilePath));
             DBFReader reader = new DBFReader(bis)) {

            DBFRow dbfRow;
            while ((dbfRow = reader.nextRow()) != null) {
                PostIndex postIndex = postIndexRowMapper.map(dbfRow);
                if (acceptPostIndex.test(postIndex)) {
                    handler.accept(postIndex);
                }
            }
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    static void closeQuietly(Closeable c) {
        try {
            if (c != null) {
                c.close();
            }
        } catch (RuntimeException | IOException e) {
            //no op
        }
    }
}
