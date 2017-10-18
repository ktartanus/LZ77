package compressors;

public interface Compressor {
    public CompressionParams compressAndDecompressFile(String inputFilePath);
}
