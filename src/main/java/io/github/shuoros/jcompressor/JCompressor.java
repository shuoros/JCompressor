package io.github.shuoros.jcompressor;

import java.io.File;
import java.util.List;

public interface JCompressor {

    void setFile(File file);

    File getFile();

    void compress(List<File> files);

    void extract(File zipFile, File destinationFile);

}
