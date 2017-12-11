package compression;

public class CompressionParams {

    private String compressionType;
    private String fileName;
    private int initialBytefileSize;
    private int compressedByteFileSize;
    private int compressionTimeInMilis;
    private int decompressionTimeInMilis;

    public CompressionParams(){};

    public CompressionParams(String compressionType, String fileName, int initialBytefileSize, int compressedByteFileSize, int compressionTimeInMilis, int decompressionTimeInMilis) {
        this.compressionType = compressionType;
        this.fileName = fileName;
        this.initialBytefileSize = initialBytefileSize;
        this.compressedByteFileSize = compressedByteFileSize;
        this.compressionTimeInMilis = compressionTimeInMilis;
        this.decompressionTimeInMilis = decompressionTimeInMilis;
    }

    public String getCompressionType() {
        return compressionType;
    }

    public void setCompressionType(String compressionType) {
        this.compressionType = compressionType;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public int getInitialBytefileSize() {
        return initialBytefileSize;
    }

    public void setInitialBytefileSize(int initialBytefileSize) {
        this.initialBytefileSize = initialBytefileSize;
    }

    public int getCompressedByteFileSize() {
        return compressedByteFileSize;
    }

    public void setCompressedByteFileSize(int compressedByteFileSize) {
        this.compressedByteFileSize = compressedByteFileSize;
    }

    public int getCompressionTimeInMilis() {
        return compressionTimeInMilis;
    }

    public void setCompressionTimeInMilis(int compressionTimeInMilis) {
        this.compressionTimeInMilis = compressionTimeInMilis;
    }

    public int getDecompressionTimeInMilis() {
        return decompressionTimeInMilis;
    }

    public void setDecompressionTimeInMilis(int decompressionTimeInMilis) {
        this.decompressionTimeInMilis = decompressionTimeInMilis;
    }
}
