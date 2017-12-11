package compression;

import net.jpountz.lz4.LZ4Factory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class LZ4Compressor implements Compressor {
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

            FileOutputStream fos = new FileOutputStream("lz4.lz");
            fos.write(out);
            fos.close();

            long decompressStartTime = System.nanoTime();
            byte[] decompressed = this.decompress(out, data.length);
            long decompressEndTime = System.nanoTime();
            FileOutputStream test = new FileOutputStream("lz4.txt");
            test.write(decompressed);
            test.close();

            compressionParams.setFileName(filename);
            compressionParams.setCompressionType("LZ4");
            compressionParams.setInitialBytefileSize(data.length);
            compressionParams.setCompressedByteFileSize(out.length);
            compressionParams.setCompressionTimeInMilis((int)(endCompressionTime - startCompressionTime));
            compressionParams.setDecompressionTimeInMilis((int)(decompressEndTime - decompressStartTime));

            return compressionParams;
        } catch (IOException e) {

        }

        return null;
    }

    private byte[] compress(byte[] data) throws IOException {
        LZ4Factory factory = LZ4Factory.fastestInstance();
        net.jpountz.lz4.LZ4Compressor compressor = factory.fastCompressor();
        return compressor.compress(data);
    }

    private byte[] decompress(byte[] data, int destLen) throws IOException {
        LZ4Factory factory = LZ4Factory.fastestInstance();
        net.jpountz.lz4.LZ4FastDecompressor decompressor = factory.fastDecompressor();
        return decompressor.decompress(data, destLen);
    }
}
