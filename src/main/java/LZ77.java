import compressors.Compressor;
import compressors.GZIPCompressor;

public class LZ77 {
    public static void main(String args[]){
        Compressor zip = new GZIPCompressor();
        zip.compressAndDecompressFile("testFile.txt");
    }
}

