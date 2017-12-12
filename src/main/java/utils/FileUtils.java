package utils;

import java.io.File;

/**
 * Created by ktartanus on 12.12.2017.
 */
public class FileUtils {
    public static String getNameWithoutExtenstion(String fileName){
        return fileName.replaceFirst("[.][^.]+$", "");
    }

    public static String getFileParentAbsolutePath(String filePath){
        File userFile = new File(filePath);
        File parentFile = userFile.getParentFile();
        return parentFile.getAbsolutePath();
    }

    public static String getFileNameFromPath(String filePath){
        File userFile = new File(filePath);
        String name = FileUtils.getNameWithoutExtenstion(userFile.getName());
        return name;
    }
}
