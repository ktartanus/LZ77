package compressors;

public class CompressionParams {

    private String compressionType;
    private String fileName;
    private int initialBytefileSize;
    private int compressedByteFileSize;
    private int compressionTimeInMilis;

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
}
