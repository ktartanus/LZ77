package compression;

import sun.misc.IOUtils;
import utils.FileUtils;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class GZIPCompressor implements Compressor {

    byte[] buffer = new byte[1024];

    public CompressionParams compressAndDecompressFile(String inputFilePath) {
        try {
            CompressionParams compressionParams = new CompressionParams();




            String compressedFilePath = FileUtils.getFileParentAbsolutePath(inputFilePath) + '/' + FileUtils.getFileNameFromPath(inputFilePath) + "-gzip.zip";

            Path fileLocation = Paths.get(inputFilePath);
            byte[] data = Files.readAllBytes(fileLocation);
            long startCompressionTime = System.nanoTime();
            byte[] out = compress(inputFilePath, compressedFilePath);
            long endCompressionTime = System.nanoTime();

            long startDecompressionTime = System.nanoTime();
            File inputFile = new File(inputFilePath);
            String outputFolder = FileUtils.getFileParentAbsolutePath(compressedFilePath);
            decompress(compressedFilePath, outputFolder);
            long endDecompressionTime = System.nanoTime();
            File userFile = new File(inputFilePath);
            String filename = userFile.getName();
            compressionParams.setFileName(filename);
            compressionParams.setCompressionType("GZIP");
            compressionParams.setInitialBytefileSize(data.length);
            compressionParams.setCompressedByteFileSize(out.length);
            compressionParams.setCompressionTimeInMilis((int)(endCompressionTime - startCompressionTime));
            compressionParams.setDecompressionTimeInMilis((int)(endDecompressionTime - startDecompressionTime));

            return compressionParams;
        } catch (IOException e) {

        }
        return null;
    }

    public byte[] compress(String inputFilePath, String outputFilePath) {


        try {
            File inputFile = new File(inputFilePath);
            String fileName = inputFile.getName();
            FileOutputStream fos = new FileOutputStream(outputFilePath);
            ZipOutputStream zos = new ZipOutputStream(fos);
            ZipEntry ze = new ZipEntry(fileName);
            zos.putNextEntry(ze);
            FileInputStream in = new FileInputStream(inputFile);

            int len;
            while ((len = in.read(buffer)) > 0) {
                zos.write(buffer, 0, len);
            }

            in.close();
            zos.closeEntry();
            zos.close();

            Path path = Paths.get(outputFilePath);
            return Files.readAllBytes(path);

        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public byte[] decompress(String zipFile, String outputFolder) {


        byte[] buffer = new byte[1024];

        try{

            File folder = new File(outputFolder);
            if(!folder.exists()){
                folder.mkdir();
            }

            ZipInputStream zis =
                    new ZipInputStream(new FileInputStream(zipFile));
            ZipEntry ze = zis.getNextEntry();

            while(ze!=null){

                String fileName = FileUtils.getNameWithoutExtenstion(ze.getName());
                String newFilePath = outputFolder + File.separator + fileName + "-gzip"+".txt";
                File newFile = new File(newFilePath);

                System.out.println("file unzip : "+ newFile.getAbsoluteFile());

                new File(newFile.getParent()).mkdirs();

                FileOutputStream fos = new FileOutputStream(newFile);

                int len;
                while ((len = zis.read(buffer)) > 0) {
                    fos.write(buffer, 0, len);
                }

                fos.close();
                ze = zis.getNextEntry();

                Path path = Paths.get(newFilePath);
                return Files.readAllBytes(path);

            }

            zis.closeEntry();
            zis.close();
        }catch(IOException ex){
            ex.printStackTrace();
        }
        return buffer;
    }

}
