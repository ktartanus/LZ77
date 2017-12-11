package compression;

public interface Compressor {
    public CompressionParams compressAndDecompressFile(String inputFilePath);
}
