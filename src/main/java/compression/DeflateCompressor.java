package src.main.java.compression;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

public class DeflateCompressor implements Compressor{

    public CompressionParams compressAndDecompressFile(String inputFilePath) {
        try {
            Path fileLocation = Paths.get(inputFilePath);
            byte[] data = Files.readAllBytes(fileLocation);
            byte[] out = this.compress(data);
            FileOutputStream fos = new FileOutputStream("deflate.def");
            fos.write(out);
            fos.close();

            byte[] decompressed = this.decompress(out);
            String text = new String(decompressed);
            FileOutputStream test = new FileOutputStream("deflate.txt");
            test.write(decompressed);
            test.close();
        } catch (IOException e) {

        } catch (DataFormatException e) {

        }

        return null;
    }

    private byte[] compress(byte[] data) throws IOException {
        Deflater deflater = new Deflater();
        deflater.setInput(data);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        deflater.finish();
        byte[] buffer = new byte[1024];
        while (!deflater.finished()) {
            int count = deflater.deflate(buffer); // returns the generated code... index
            outputStream.write(buffer, 0, count);
        }
        outputStream.close();
        byte[] output = outputStream.toByteArray();
        return output;
    }

    private byte[] decompress(byte[] data) throws IOException, DataFormatException {
        Inflater inflater = new Inflater();
        inflater.setInput(data);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] buffer = new byte[1024];
        while (!inflater.finished()) {
            int count = inflater.inflate(buffer);
            outputStream.write(buffer, 0, count);
        }
        outputStream.close();
        byte[] output = outputStream.toByteArray();
        return output;
    }
}
