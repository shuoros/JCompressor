package io.github.shuoros.jcompressor.compress;

import io.github.shuoros.jcompressor.JCompressor;
import io.github.shuoros.jcompressor.exception.NoFileToZipException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class ZipCompressor implements JCompressor {

    private File file;

    public ZipCompressor() {
        this(null);
    }

    public ZipCompressor(File file) {
        this.file = file;
    }

    public void setFile(File file) {
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

    public void extract(File destinationFile) {
        extract(this.file, destinationFile);
    }

    @Override
    public void extract(File zipFile, File destinationFile) {
        byte[] buffer = new byte[1024];
        try {
            ZipInputStream zis = new ZipInputStream(new FileInputStream(zipFile));
            ZipEntry zipEntry = zis.getNextEntry();
            while (zipEntry != null) {
                zipEntry = extractZipEntryAndReturnNextEntry(destinationFile, buffer, zis, zipEntry);
            }
            zis.closeEntry();
            zis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private ZipEntry extractZipEntryAndReturnNextEntry(File destinationFile, byte[] buffer, ZipInputStream zis, ZipEntry zipEntry) throws IOException {
        File newFile = extractFileFromZip(destinationFile, zipEntry);
        if (zipEntry.isDirectory()) {
            if (!newFile.isDirectory() && !newFile.mkdirs()) {
                throw new IOException("Failed to create directory " + newFile);
            }
        } else {
            checkForWindowsCreatedArchives(newFile);
            writeInFileOutPutStream(buffer, zis, newFile);
        }
        return zis.getNextEntry();
    }

    private File extractFileFromZip(File destinationDir, ZipEntry zipEntry) throws IOException {
        File destFile = new File(destinationDir, zipEntry.getName());

        String destDirPath = destinationDir.getCanonicalPath();
        String destFilePath = destFile.getCanonicalPath();

        if (!destFilePath.startsWith(destDirPath + File.separator)) {
            throw new IOException("Entry is outside of the target dir: " + zipEntry.getName());
        }

        return destFile;
    }

    private void checkForWindowsCreatedArchives(File newFile) throws IOException {
        File parent = newFile.getParentFile();
        if (!parent.isDirectory() && !parent.mkdirs()) {
            throw new IOException("Failed to create directory " + parent);
        }
    }

    private void writeInFileOutPutStream(byte[] buffer, ZipInputStream zis, File newFile) throws IOException {
        FileOutputStream fos = new FileOutputStream(newFile);
        int len;
        while ((len = zis.read(buffer)) > 0) {
            fos.write(buffer, 0, len);
        }
        fos.close();
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
