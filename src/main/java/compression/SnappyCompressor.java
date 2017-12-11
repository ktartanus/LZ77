package src.main.java.compression;

import org.xerial.snappy.Snappy;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class SnappyCompressor implements Compressor{
    public CompressionParams compressAndDecompressFile(String inputFilePath) {
        try {
            CompressionParams compressionParams = new CompressionParams();
            Path fileLocation = Paths.get(inputFilePath);
            byte[] data = Files.readAllBytes(fileLocation);
            long startCompressionTime = System.nanoTime();
            byte[] out = this.compress(data);
            long endCompressionTime = System.nanoTime();
            File userFile = new File(inputFilePath);
            String filename = userFile.getName();
            compressionParams.setFileName(filename);
            compressionParams.setCompressionType("SNAPPY");
            compressionParams.setInitialBytefileSize(data.length);
            compressionParams.setCompressedByteFileSize(out.length);
            compressionParams.setCompressionTimeInMilis((int)(endCompressionTime - startCompressionTime));

            FileOutputStream fos = new FileOutputStream("snappy.snap");
            fos.write(out);
            fos.close();

            byte[] decompressed = this.decompress(out);
            FileOutputStream test = new FileOutputStream("snappy.txt");
            test.write(decompressed);
            test.close();

            return compressionParams;
        } catch (IOException e) {

        }

        return null;
    }

    private byte[] compress(byte[] data) throws IOException {
        Snappy snappy = new Snappy();
        return snappy.compress(data);
    }

    private byte[] decompress(byte[] data) throws IOException {
        Snappy snappy = new Snappy();
        return snappy.uncompress(data);
    }
}
