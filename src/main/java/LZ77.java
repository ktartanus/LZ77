
import compression.CompressionParams;
import compression.Compressor;
import compression.DeflateCompressor;
import compression.GZIPCompressor;
import utils.CompressionStatistics;

import java.util.ArrayList;
import java.util.List;

public class LZ77 {
    public static void main(String args[]){
        CompressionParams compressionParams = new CompressionParams();
        Compressor gzipCompressor = new GZIPCompressor();
//        zip.compressAndDecompressFile("testFile.txt");
        Compressor deflateCompressor = new DeflateCompressor();
        List<Compressor> compressors = new ArrayList<>();
        compressors.add(gzipCompressor);
        compressors.add(deflateCompressor);
        for (Compressor compressor : compressors){
            compressionParams = compressor.compressAndDecompressFile("C:\\Users\\ktartanus\\Desktop\\lz77\\LZ77\\Compressed\\testFile.txt");
            CompressionStatistics.showCompressionStatistics(compressionParams);
        }
    }


}

