package com.hipravin;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class TooManyOpenSample {
    public static void main(String[] args) throws IOException {
        //no success reproducing error
        Path root = Paths.get("C:/dev");
        List<Path> files = findFilesRecursively(root, 100000);

        System.out.println(files.size());
        long counter = 0;
        for (Path file : files) {
            Files.lines(file);

            if(counter ++ % 100 == 0 ) {
                System.out.println(counter);
            }
        }
    }

    public static List<Path> findFilesRecursively(Path searchRoot, long limit) throws IOException {
        List<Path> result = new ArrayList<>();

        PathMatcher text = FileSystems.getDefault().getPathMatcher("glob:*.{java,txt,js,html,md,xml,json}");

        Predicate<Path> isText = p -> text.matches(p.getFileName());

        SearchFileVisitor searchFileVisitor = new SearchFileVisitor(result, isText, limit);
        Files.walkFileTree(searchRoot, searchFileVisitor);

        return result;

    }

    public static class SearchFileVisitor extends SimpleFileVisitor<Path> {
        private final List<Path> foundFiles;
        private final Predicate<? super Path> filePredicate;
        private final long limit;

        public SearchFileVisitor(List<Path> foundFiles, Predicate<? super Path> filePredicate, long limit) {
            this.foundFiles = foundFiles;
            this.filePredicate = filePredicate;
            this.limit = limit;
        }

        @Override
        public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
            if (foundFiles.size() >= limit) {
                return FileVisitResult.TERMINATE;
            } else if (filePredicate.test(file)) {
                foundFiles.add(file);
            }
            return FileVisitResult.CONTINUE;
        }
    }
}
