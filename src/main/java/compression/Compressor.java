package src.main.java.compression;

public interface Compressor {
    public CompressionParams compressAndDecompressFile(String inputFilePath);
}
