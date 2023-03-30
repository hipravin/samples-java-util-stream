package com.hipravin.post;

import com.linuxense.javadbf.DBFField;
import com.linuxense.javadbf.DBFReader;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Stream;

public class PostIndexReaderImpl implements PostIndexReader {
    private final Path indexFilePath;

    public PostIndexReaderImpl(Path indexFilePath) {
        this.indexFilePath = indexFilePath;
    }

    @Override
    public Stream<PostIndex> readAll() {
        return null;
    }

    @Override
    public List<PostIndex> readAllList() {
        List<PostIndex> result = new ArrayList<>();
        try (BufferedInputStream bis = new BufferedInputStream(Files.newInputStream(indexFilePath));
             DBFReader reader = new DBFReader(bis)) {

            var postIndexFileStructure = PostIndexDbfFileStructure.fromReader(reader);

            Object[] rowObjects;
            while ((rowObjects = reader.nextRecord()) != null) {
                result.add(postIndexFileStructure.rawRecordToPostIndex(rowObjects));
            }
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }

        return result;
    }

    @Override
    public void readAll(Consumer<? super PostIndex> handler) {

    }

    record PostIndexDbfFileStructure(
            Map<String, Integer> fieldsByName) {

        static final String FIELD_INDEX = "INDEX";
        static final String FIELD_NAME = "OPSNAME";
        static final String FIELD_REGION = "REGION";
        static final String FIELD_AUTONOM = "AUTONOM";
        static final String FIELD_AREA = "AREA";
        static final String FIELD_CITY = "CITY";
        static final String FIELD_CITY_1 = "CITY_1";

        public static PostIndexDbfFileStructure fromReader(DBFReader dbfReader) {
            Map<String, Integer> fieldsByName = new LinkedHashMap<>();
            for (int i = 0; i < dbfReader.getFieldCount(); i++) {
                DBFField field = dbfReader.getField(i);
                fieldsByName.merge(field.getName(), i, (a, b) -> {
                    throw new IllegalArgumentException("Duplicated field: " + a);
                });
            }
            return new PostIndexDbfFileStructure(Map.copyOf(fieldsByName));
        }

        String getFieldValueAsString(Object[] rowObjects, String fieldName) {
            if (!fieldsByName.containsKey(fieldName)) {
                throw new IllegalArgumentException("Field not found: " + fieldName);
            }
            int fieldIndex = fieldsByName.get(fieldName);
            Objects.checkIndex(fieldIndex, rowObjects.length);

            Object value = rowObjects[fieldIndex];

            if (value == null || value instanceof String) {
                return (String) value;
            } else {
                return String.valueOf(value);
            }
        }

        public PostIndex rawRecordToPostIndex(Object[] rowObjects) {
            return new PostIndex(
                    getFieldValueAsString(rowObjects, FIELD_INDEX),
                    getFieldValueAsString(rowObjects, FIELD_NAME),
                    getFieldValueAsString(rowObjects, FIELD_REGION),
                    getFieldValueAsString(rowObjects, FIELD_AUTONOM),
                    getFieldValueAsString(rowObjects, FIELD_AREA),
                    getFieldValueAsString(rowObjects, FIELD_CITY),
                    getFieldValueAsString(rowObjects, FIELD_CITY_1)
            );

        }
    }
}
