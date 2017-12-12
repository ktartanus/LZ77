
import compression.*;
import utils.CompressionStatistics;

import java.util.ArrayList;
import java.util.List;

public class LZ77 {
    public static void main(String args[]){
        List<String> inputFileList = new ArrayList<>();
        inputFileList.add("Compressed/testFile.txt");
        inputFileList.add("Compressed/testFile2.txt");
        inputFileList.add("Compressed/testFile3.txt");

        CompressionParams compressionParams;
        Compressor deflateCompressor = new DeflateCompressor();
        Compressor snappyCompressor = new SnappyCompressor();
        Compressor lz4Compressor = new LZ4Compressor();
        Compressor gzipCompressor = new GZIPCompressor();

        List<Compressor> compressors = new ArrayList<>();
        compressors.add(snappyCompressor);
        compressors.add(deflateCompressor);
        compressors.add(lz4Compressor);
        compressors.add(gzipCompressor);


        for(String inputFile : inputFileList){
            List<CompressionParams> compressionParamsList = new ArrayList<CompressionParams>();
            for (Compressor compressor : compressors){
                compressionParams = compressor.compressAndDecompressFile(inputFile);
                compressionParamsList.add(compressionParams);
            }
            CompressionStatistics.showCompressionStatistics(compressionParamsList);
        }
    }
}

