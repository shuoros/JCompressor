package io.github.shuoros.jcompressor;

import java.io.File;

public interface JCompressor {

    void compress(File file);

    void extract(File file);

}
