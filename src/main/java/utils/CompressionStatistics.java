package utils;

import compression.CompressionParams;

/**
 * Created by ktartanus on 11.12.2017.
 */
public class CompressionStatistics {
    public static void showCompressionStatistics(CompressionParams compressionParams){
        StringBuilder sb = new StringBuilder();
        String tab = "\t";
        sb.append("Algorytm  " + compressionParams.getCompressionType() +"\n");
        sb.append("Parametry Kompresji :\n");
        sb.append("Czas Kompresji" + tab);
        sb.append("Czas Dekompresji" + tab);
        sb.append("Rozmiar wejściowy" + tab);
        sb.append("Rozmiar po kompresji");
    }
    private String showTableRow(int timeCompression, int timeDecompression, int fileSizeCompression, int fileSizeDecompression ){
        String tab = "\t";
        String singleRow = timeCompression + tab + timeDecompression + tab + fileSizeCompression + tab + fileSizeDecompression;
        return singleRow;
    }
}