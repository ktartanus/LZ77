package utils;

import compression.CompressionParams;
import java.util.List;

/**
 * Created by ktartanus on 11.12.2017.
 */
public class CompressionStatistics {
    public static void showCompressionStatistics(List<CompressionParams> compressionParams){
        System.out.println("Plik : " + compressionParams.get(0).getFileName());
        System.out.format("%25s%25s%25s%25s%25s", "Algorytm", "Czas Kompresji[ns] ", "Czas Dekompresji[ns] ", "Rozmiar wejściowy[byte] ", "Rozmiar po kompresji[byte] ");
        System.out.println("\n");
        for(CompressionParams singleParam : compressionParams){
            showTableRow(singleParam.getCompressionType(), singleParam.getCompressionTimeInMilis(),singleParam.getDecompressionTimeInMilis(),
                    singleParam.getInitialBytefileSize(), singleParam.getCompressedByteFileSize());
        }
    }
    private static void showTableRow(String algorythmName, Integer timeCompression, Integer timeDecompression, Integer fileSizeCompression, Integer fileSizeDecompression ){
          System.out.format("%22s%22s%22s%22s%22s", algorythmName, timeCompression.toString() , timeDecompression.toString(),
                  fileSizeCompression.toString(), fileSizeDecompression.toString());;
        System.out.println("\n");
    }
}