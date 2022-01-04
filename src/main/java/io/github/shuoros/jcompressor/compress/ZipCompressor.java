package io.github.shuoros.jcompressor.compress;

import io.github.shuoros.jcompressor.JCompressor;
import io.github.shuoros.jcompressor.exception.NoFileToExtractException;
import io.github.shuoros.jcompressor.exception.NoFileToZipException;

import java.io.*;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class ZipCompressor implements JCompressor {

    private final List<File> files;

    public ZipCompressor() {
        this((List<File>) null);
    }

    public ZipCompressor(File file) {
        this(List.of(file));
    }

    public ZipCompressor(List<File> files) {
        this.files = files;
    }

    public void compress() {
        if (this.files == null)
            throw new NoFileToZipException();
        compress(this.files);
    }

    public void compress(List<File> files) {
        compress(files, getZipFileDestinationFile(files.get(0)));
    }

    @Override
    public void compress(List<File> files, File destinationFile) {
        final Path sourceDir = getSourceDirDestinationPath(files.get(0));
        final String zipFileName = (destinationFile.getName().endsWith(".zip")) ?//
                destinationFile.getPath() : destinationFile.getPath().concat(".zip");
        try {
            createATempFolderToCompressFilesFrom(sourceDir, files);
            compressFileInZip(sourceDir, zipFileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void extractToFolder() {
        if (this.files == null)
            throw new NoFileToExtractException();
        extract(this.files.get(0), getZipFileDestinationFile(this.files.get(0)));
    }

    public void extractToHere() {
        if (this.files == null)
            throw new NoFileToExtractException();
        extract(this.files.get(0), getZipFileDestinationFile(this.files.get(0)).getParentFile());
    }

    public void extract(File destinationFile) {
        if (this.files == null)
            throw new NoFileToExtractException();
        extract(this.files.get(0), destinationFile);
    }

    @Override
    public void extract(File compressedFile, File destinationFile) {
        byte[] buffer = new byte[1024];
        try {
            ZipInputStream zis = new ZipInputStream(new FileInputStream(compressedFile));
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
        Files.walkFileTree(sourceDir, new SimpleFileVisitor<>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attributes) {
                writeFileInOutPutStream(file, sourceDir, outputStream);
                return FileVisitResult.CONTINUE;
            }
        });
        outputStream.close();
        delete(sourceDir);
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

    private Path getSourceDirDestinationPath(File file) {
        return getZipFileDestinationFile(file).toPath().getParent().resolve(//
                "tmp".concat(getZipFileDestinationFile(file).toPath().getFileName().toString()));
    }

    private File getZipFileDestinationFile(File file) {
        return file.getParentFile().toPath().resolve(//
                Paths.get(file.getName().replaceFirst("[.][^.]+$", ""))).toFile();
    }

    private void createATempFolderToCompressFilesFrom(Path sourceDir, List<File> files) throws IOException {
        if (!sourceDir.toFile().mkdir())
            throw new IOException();
        for (File file : files) {
            copyTo(file, new File(sourceDir.toString().concat("/").concat(file.getName())));
        }
    }

    private void copyTo(File source, File destination) throws IOException {
        if (source.isFile())
            copyFile(source, destination);
        else
            copyFolder(source, destination);
    }

    private void copyFolder(File source, File destination) throws IOException {
        if (!destination.mkdir())
            throw new IOException();
        for (String file : Objects.requireNonNull(source.list())) {
            if (new File(source.getPath().concat("/").concat(file)).isFile())
                copyFile(//
                        new File(source.getPath().concat("/").concat(file))//
                        , new File(destination.getPath().concat("/").concat(file)));
            else
                copyFolder(//
                        new File(source.getPath().concat("/").concat(file))//
                        , new File(destination.getPath().concat("/").concat(file)));
        }
    }


    private void copyFile(File source, File destination) throws IOException {
        if (!destination.createNewFile())
            throw new IOException();
        InputStream is = new FileInputStream(source);
        OutputStream os = new FileOutputStream(destination);
        writeFromInputStreamToOutputStream(is, os);
    }

    private void delete(Path file) throws IOException {
        Files.walk(file)
                .sorted(Comparator.reverseOrder())
                .map(Path::toFile)
                .forEach(java.io.File::delete);
    }

    private void writeFromInputStreamToOutputStream(InputStream is, OutputStream os) throws IOException {
        byte[] buffer = new byte[1024];
        int length;
        while ((length = is.read(buffer)) > 0) {
            os.write(buffer, 0, length);
        }

        is.close();
        os.close();
    }

}
