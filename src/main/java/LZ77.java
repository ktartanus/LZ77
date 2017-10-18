package src.main.java;

import src.main.java.compression.Compressor;
import src.main.java.compression.GZIPCompressor;

public class LZ77 {
    public static void main(String args[]){
        Compressor zip = new GZIPCompressor();
//        zip.compressAndDecompressFile("testFile.txt");
    }
}

