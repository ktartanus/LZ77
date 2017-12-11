
import compression.*;
import utils.CompressionStatistics;

import java.util.ArrayList;
import java.util.List;

public class LZ77 {
    public static void main(String args[]){
        CompressionParams compressionParams = new CompressionParams();
//        Compressor gzipCompressor = new GZIPCompressor();
//        zip.compressAndDecompressFile("testFile.txt");
        Compressor deflateCompressor = new DeflateCompressor();
        Compressor snappyCompressor = new SnappyCompressor();
        List<Compressor> compressors = new ArrayList<>();
        compressors.add(snappyCompressor);
        compressors.add(deflateCompressor);
        List<CompressionParams> compressionParamsList = new ArrayList<CompressionParams>();
        for (Compressor compressor : compressors){
            compressionParams = compressor.compressAndDecompressFile("C:\\Users\\ktartanus\\Desktop\\lz77\\LZ77\\Compressed\\testFile.txt");
            compressionParamsList.add(compressionParams);
        }
        CompressionStatistics.showCompressionStatistics(compressionParamsList);

    }


}

