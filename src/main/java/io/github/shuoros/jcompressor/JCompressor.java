package io.github.shuoros.jcompressor;

import java.io.File;
import java.util.List;

public interface JCompressor {

    void setFile(List<File> files);

    List<File> getFile();

    void compress(List<File> files);

    void extract(File zipFile, File destinationFile);

}
