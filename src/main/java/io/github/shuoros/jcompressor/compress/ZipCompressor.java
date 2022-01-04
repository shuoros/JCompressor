package io.github.shuoros.jcompressor.compress;

import io.github.shuoros.jcompressor.JCompressor;
import io.github.shuoros.jcompressor.exception.NoFileToZipException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ZipCompressor implements JCompressor {

    private final File file;

    public ZipCompressor(){
        this(null);
    }

    public ZipCompressor(File file){
        this.file = file;
    }

    public void compress() {
        if (this.file == null)
            throw new NoFileToZipException();
        compress(this.file);
    }

    @Override
    public void compress(File file) {
        final Path sourceDir = file.toPath();
        String zipFileName = file.getName().concat(".zip");
        try {
            compressFileInZip(sourceDir, zipFileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void extract(File file) {

    }

    private void compressFileInZip(Path sourceDir, String zipFileName) throws IOException {
        final ZipOutputStream outputStream = new ZipOutputStream(new FileOutputStream(zipFileName));
        Files.walkFileTree(sourceDir, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attributes) {
                writeFileInOutPutStream(file, sourceDir, outputStream);
                return FileVisitResult.CONTINUE;
            }
        });
        outputStream.close();
    }

    private void writeFileInOutPutStream(Path file, Path sourceDir, ZipOutputStream outputStream) {
        try {
            Path targetFile = sourceDir.relativize(file);
            writeFileInZipEntry(file, outputStream, targetFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void writeFileInZipEntry(Path file, ZipOutputStream outputStream, Path targetFile) throws IOException {
        outputStream.putNextEntry(new ZipEntry(targetFile.toString()));
        byte[] bytes = Files.readAllBytes(file);
        outputStream.write(bytes, 0, bytes.length);
        outputStream.closeEntry();
    }

}
