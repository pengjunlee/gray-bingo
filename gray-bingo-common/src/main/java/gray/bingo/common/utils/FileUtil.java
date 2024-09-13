package gray.bingo.common.utils;

public class FileUtil {

    /**
     * 从文件名中提取文件扩展名。
     *
     * @param fileName 文件名
     * @return 文件扩展名
     */
    public static String getFileExtension(String fileName) {
        int dotIndex = fileName.lastIndexOf('.');
        if (dotIndex > 0 && dotIndex < fileName.length() - 1) {
            return fileName.substring(dotIndex + 1);
        }
        return ""; // 没有扩展名
    }
}
