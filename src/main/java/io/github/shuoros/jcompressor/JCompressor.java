package io.github.shuoros.jcompressor;

import java.io.File;

public interface JCompressor {

    void setFile(File file);

    File getFile();

    void compress(File file);

    void extract(File zipFile, File destinationFile);

}
