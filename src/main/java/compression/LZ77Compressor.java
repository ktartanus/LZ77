package compression;

import utils.FileUtils;

import java.io.*;
import java.nio.file.Files;

public class LZ77Compressor implements Compressor {
    private Reader inputFile;
    private int inputBuforSize = 1024;
    private PrintWriter outputFile;
    private StringBuffer dictionarySize;

    @Override
    public CompressionParams compressAndDecompressFile(String inputFilePath) {
        try{
            File userFile = new File(inputFilePath);
            String filename = userFile.getName();

            CompressionParams compressionParams = new CompressionParams();
            long startCompressionTime = System.nanoTime();
            byte[] compressed = this.compress(inputFilePath);
            long endCompressionTime = System.nanoTime();

            long startDecompressionTime = System.nanoTime();
            byte[] decompressed = this.decompress(inputFilePath);
            long endDecompressionTime = System.nanoTime();

            byte[] initFile = Files.readAllBytes(new File(inputFilePath).toPath());

            compressionParams.setFileName(filename);
            compressionParams.setCompressionType("LZ77");
            compressionParams.setInitialBytefileSize(initFile.length);
            compressionParams.setCompressedByteFileSize(compressed.length);
            compressionParams.setCompressionTimeInMilis((int)(endCompressionTime - startCompressionTime));
            compressionParams.setDecompressionTimeInMilis((int)(endDecompressionTime - startDecompressionTime));

            return compressionParams;
        } catch (IOException e) {

        }

        return null;
    }

    private void trimDictionarySize() {
        if (dictionarySize.length() > inputBuforSize) {
            dictionarySize = dictionarySize.delete(0,  dictionarySize.length() - inputBuforSize);
        }
    }

    /* filename - nazwa pliku bez rozszerzenia */
    private byte[] compress(String inputFilePath) throws IOException {
        dictionarySize = new StringBuffer(inputBuforSize);
        String filename = new File(inputFilePath).getName();
        inputFile = new BufferedReader(new FileReader(inputFilePath));

        String compressedFilePath = FileUtils.getFileParentAbsolutePath(inputFilePath) + '/' + FileUtils.getNameWithoutExtenstion(filename) + "-lz77.lz77";
        outputFile = new PrintWriter(new BufferedWriter(new FileWriter(compressedFilePath)));

        int nextChar;
        int matchIndex = 0;
        int tempIndex;
        String currentMatch = "";

        /*
         * czytamy kolejne znaki dopóki wystąpił kolejny
         * i szukamy dopasowania w słowniku
         */
        while ((nextChar = inputFile.read()) != -1) {
            tempIndex = dictionarySize.indexOf(currentMatch + (char)nextChar);
            /* jeśli znaleziono dopasowanie (indeks inny niż -1) */
            if (tempIndex != -1) {
                currentMatch += (char)nextChar; // dodajemy dopasowany znak
                matchIndex = tempIndex; //ustawiamy nowy indeks dopasowania
            } else {
                /*
                 * koniec dopasowania, zapisujemy znaleziony ciąg za pomocą trójki:
                 * Indeks ostatnio dopasowanego znaku - matchIndex
                 * Dlugosc dopasowanego znaku - currentMatch.length()
                 * Pierwszy niedopasowany znak - nextChar
                 */
                String compressedString = "~"+matchIndex+"~"+currentMatch.length()+"~"+(char)nextChar;
                String concat = currentMatch + (char)nextChar;
                /*
                  * Zapisz skompresowana trójke jeśli jest krótsza niż dopasowany tekst
                 * np. dopasowanie jedynki jest krótsze niż zapisanie w postaci trójki.
                 */
                if (compressedString.length() <= concat.length()) {
                    outputFile.print(compressedString);
                    dictionarySize.append(concat);
                    currentMatch = ""; //zerowanie dopasowania
                    matchIndex = 0; // zerowanie indeksu
                } else {
                    /*
                     * Jeżeli dopasowanie było niekorzystne - sprawdz czy nie mozemy dopasować podciagu
                     * 1. Zapisz pierwszy znak dopasowania na wyjście i dodaj bo słownika
                     * 2. Usuń pierwszy znak z dopasowania
                     * 3. Spróbuj dopasować nowy podciąg w słowniku.
                     */
                    currentMatch = concat; matchIndex = -1;
                    while (currentMatch.length() > 1 && matchIndex == -1) {
                        outputFile.print(currentMatch.charAt(0));
                        dictionarySize.append(currentMatch.charAt(0));
                        currentMatch = currentMatch.substring(1, currentMatch.length());
                        matchIndex = dictionarySize.indexOf(currentMatch);
                    }
                }
                trimDictionarySize();
            }
        }

        /*
         * Po wyjściu z pętli należy sprawdzić czy ostatnia operacja nie była dopasowaniem ciągu
         * i postąpić standardowo
         */
        if (matchIndex != -1) {
            String compressedString = "~"+matchIndex+"~"+currentMatch.length();
            if (compressedString.length() <= currentMatch.length()) {
                outputFile.print("~"+matchIndex+"~"+currentMatch.length());
            } else {
                outputFile.print(currentMatch);
            }
        }

        inputFile.close();
        outputFile.flush();
        outputFile.close();

        byte[] bFile = Files.readAllBytes(new File(compressedFilePath).toPath());

        return bFile;
    }

    private byte[] decompress(String inputFilePath) throws IOException {
        dictionarySize = new StringBuffer(inputBuforSize);

        String filename = new File(inputFilePath).getName();
        String compressedFilePath = FileUtils.getFileParentAbsolutePath(inputFilePath) + '/' + FileUtils.getNameWithoutExtenstion(filename) + "-lz77.lz77";

        inputFile = new BufferedReader(new FileReader(compressedFilePath));

        String decompressedFilePath = FileUtils.getFileParentAbsolutePath(inputFilePath) + '/' + FileUtils.getNameWithoutExtenstion(filename) + "-lz77.txt";

        outputFile = new PrintWriter(new BufferedWriter(new FileWriter(decompressedFilePath)));
        StreamTokenizer streamTokenizer = new StreamTokenizer(inputFile);

        /* Dodanie znaków specjalnych tokenizera, aby były traktowane jako słowa */
        streamTokenizer.ordinaryChar((int)' ');
        streamTokenizer.ordinaryChar((int)'.');
        streamTokenizer.ordinaryChar((int)'-');
        streamTokenizer.ordinaryChar((int)'\n');
//        streamTokenizer.wordChars((int)'\n', (int)'\n');
        streamTokenizer.wordChars((int)' ', (int)'}');

        int offset, length;
        /* dopóki możemy pobrać kolejny token (słowo lub numer ~ są pomijane) */
        while (streamTokenizer.nextToken() != StreamTokenizer.TT_EOF) {
            switch (streamTokenizer.ttype) {
                /* jesli tokenem jest slowo - zapisujemy na wyjscie */
                case StreamTokenizer.TT_WORD:
                    dictionarySize.append(streamTokenizer.sval);
                    outputFile.print(streamTokenizer.sval);
                    trimDictionarySize();
                    break;
                case StreamTokenizer.TT_EOL:
                    outputFile.print('\n');
                    break;
                /* jesli tokenem jest liczba to sprawdzamy czy przystąpić do dekompresji */
                case StreamTokenizer.TT_NUMBER:
                    offset = (int)streamTokenizer.nval;
                    streamTokenizer.nextToken();
                    /* jeśli pierwszy token był liczbą a kolejny jest słowem, to znaczy że liczba była częscią słowa */
                    if (streamTokenizer.ttype == StreamTokenizer.TT_WORD) {
                        /* zapisujemy na wyjście */
                        dictionarySize.append(offset+streamTokenizer.sval);
                        outputFile.print(offset+streamTokenizer.sval);
                        break;
                    }
                    /* jesli kolejny token jest liczbą to przystępujemy do dekompresji */
                    streamTokenizer.nextToken(); // get the length
                    length = (int)streamTokenizer.nval;
                    /* znajdujemy dopasowany ciąg za pomocą indeksu i długości ciągu i zapisujemy zdekompresowany ciag*/
                    if(dictionarySize.length() >= offset+length){
                        String output = dictionarySize.substring(offset, offset+length);
                        outputFile.print(output);
                        dictionarySize.append(output);
                        trimDictionarySize();
                    }
                    break;
                default:
            }
        }
        inputFile.close();
        outputFile.flush();
        outputFile.close();

        byte[] bFile = Files.readAllBytes(new File(decompressedFilePath).toPath());

        return bFile;
    }
}
