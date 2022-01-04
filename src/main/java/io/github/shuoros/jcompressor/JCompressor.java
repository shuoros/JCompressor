package io.github.shuoros.jcompressor;

import java.io.File;
import java.util.List;

/**
 * @author Soroush Shemshadi
 * @version 0.1.0
 * @see <a href="https://github.com/shuoros/JCompressor">JCompressor</a>
 * @since 0.1.0
 */
public interface JCompressor {

    void compress(List<File> files);

    void extract(File zipFile, File destinationFile);

}
