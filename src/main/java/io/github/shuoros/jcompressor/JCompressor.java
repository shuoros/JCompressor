package io.github.shuoros.jcompressor;

import java.io.File;
import java.util.List;

public interface JCompressor {

    void setFiles(List<File> files);

    List<File> getFiles();

    void compress(List<File> files);

    void extract(File zipFile, File destinationFile);

}
