
import compression.*;
import utils.CompressionStatistics;

import java.util.ArrayList;
import java.util.List;

public class LZ77 {
    public static void main(String args[]){
        CompressionParams compressionParams = new CompressionParams();
        Compressor deflateCompressor = new DeflateCompressor();
        Compressor snappyCompressor = new SnappyCompressor();
        Compressor lz4Compressor = new LZ4Compressor();
        Compressor gzipCompressor = new GZIPCompressor();

        List<Compressor> compressors = new ArrayList<>();
        compressors.add(snappyCompressor);
        compressors.add(deflateCompressor);
        compressors.add(lz4Compressor);
        compressors.add(gzipCompressor);

        List<CompressionParams> compressionParamsList = new ArrayList<CompressionParams>();
        for (Compressor compressor : compressors){
            compressionParams = compressor.compressAndDecompressFile("C:\\Users\\ktartanus\\Desktop\\lz77\\LZ77\\Compressed\\testFile.txt");
            compressionParamsList.add(compressionParams);
        }
        CompressionStatistics.showCompressionStatistics(compressionParamsList);

    }


}

