package com.hipravin;

import com.linuxense.javadbf.DBFException;
import com.linuxense.javadbf.DBFField;
import com.linuxense.javadbf.DBFReader;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;

public class DbfReadingSample {
    public static void main(String args[]) throws URISyntaxException {

        File dbfFile = Path.of(ClassLoader.getSystemResource("data/PIndx05.dbf").toURI()).toFile();
        long limitRows = 100;
        DBFReader reader = null;
        try {
            reader = new DBFReader(new FileInputStream(dbfFile));

            int numberOfFields = reader.getFieldCount();
            for (int i = 0; i < numberOfFields; i++) {
                DBFField field = reader.getField(i);
                System.out.println(field.getName() + "\t" + field.getType().name());
            }
            Object[] rowObjects;

            long count = 0;
            while ((rowObjects = reader.nextRecord()) != null && count ++ < limitRows) {
                for (int i = 0; i < rowObjects.length; i++) {
                    System.out.print(rowObjects[i] + "\t");
                }
                System.out.println();
            }
        } catch (DBFException | IOException e) {
            throw new RuntimeException(e);
        } finally {
            if(reader != null) {
                reader.close();
            }
        }
    }
}