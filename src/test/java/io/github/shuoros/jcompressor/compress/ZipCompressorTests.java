package io.github.shuoros.jcompressor.compress;

import io.github.shuoros.jcompressor.JCompressor;
import io.github.shuoros.jcompressor.exception.NoFileToExtractException;
import io.github.shuoros.jcompressor.exception.NoFileToZipException;
import org.junit.jupiter.api.*;

import java.io.File;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ZipCompressorTests {

    private static String resources;
    private static File file;
    private JCompressor jCompressor;

    @BeforeAll
    public static void beforeAll() throws Exception {
        resources = Paths.get(Objects.requireNonNull(ZipCompressorTests.class.getResource("/")).toURI()).toFile().getPath();
        file = new File(resources.concat("/JCompressorTest.txt"));
        file.createNewFile();
    }

    @BeforeEach
    public void beforeEach() {
        jCompressor = new ZipCompressor();
    }

    @Test
    @Order(1)
    public void whenZipCompressorHasNoFileSetsTheCompressMethodMustThrowNoFileToZipException() {
        // When
        ZipCompressor zipCompressor = new ZipCompressor();

        // Then
        assertThrows(NoFileToZipException.class, zipCompressor::compress);
    }

    @Test
    @Order(2)
    public void whenCompressAFileItMustBeCompressed() {
        // When
        jCompressor.compress(List.of(file));

        // Then
        assertTrue(new File(resources.concat("/JCompressorTest.zip")).exists());

        // After
        file.delete();
    }

    @Test
    @Order(3)
    public void whenZipCompressorHasNoFileSetsTheExtractMethodMustThrowNoFileToExtractException() {
        // When
        ZipCompressor zipCompressor = new ZipCompressor();

        // Then
        assertThrows(NoFileToExtractException.class, zipCompressor::extractToHere);
    }

    @Test
    @Order(4)
    public void whenExtractAZipFileItMustBeExtracted() {
        // Given
        File zipFile = new File(resources.concat("/JCompressorTest.zip"));

        // When
        jCompressor.extract(zipFile, file.getParentFile());

        // Then
        assertTrue(file.exists());

        // After
        file.delete();
        zipFile.delete();
    }
}
