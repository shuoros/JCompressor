package io.github.shuoros.jcompressor;

import java.io.File;
import java.util.List;

/**
 * JCompressor is an easy-to-use library in Java that can be used to compress different files and
 * folders by various methods such as zip, Gzip, rar, Stufflt, 7z etc., and extract files and
 * folders from those different compressed files. This interface contains two of the key methods
 * that any compression algorithm needs to implement. A compressor method that, by receiving a list
 * of files and a destination address, compresses the files with the desired algorithm and saves them
 * in the destination address. And the second is the extractor method, which receives a zip file and
 * a destination address, extracts the contents of that file and stores it in the destination address.
 *
 * @author Soroush Shemshadi
 * @version 0.1.0
 * @see <a href="https://github.com/shuoros/JCompressor">JCompressor</a>
 * @since 0.1.0
 */
public interface JCompressor {

    /**
     * This method compresses a list of files you want to compress and saves them in to your desired
     * destination.
     *
     * @param files           A list of {@link java.io.File}s that you want to compress.
     * @param destinationFile Destination where you want your compressed file to be saved.
     */
    void compress(List<File> files, File destinationFile);

    /**
     * This method extracts your compressed file and save its contents in your desired destination.
     *
     * @param compressedFile  A Compressed which file you want to extract the contents of it.
     * @param destinationFile Destination where your compress file will be extracted in.
     */
    void extract(File compressedFile, File destinationFile);

}
