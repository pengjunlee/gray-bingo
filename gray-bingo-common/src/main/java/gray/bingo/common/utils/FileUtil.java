package gray.bingo.common.utils;

import gray.bingo.common.Enums.ExceptionCodeEnum;
import gray.bingo.common.exceptions.BingoException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.WritableByteChannel;
import java.nio.file.*;
import java.security.MessageDigest;

@Slf4j
public class FileUtil {

    /**
     * 从文件名中提取文件扩展名。
     *
     * @param fileName 文件名
     * @return 文件扩展名
     */
    public static String getFileExtension(String fileName) {
        if (StringUtil.isBlank(fileName)) return "";
        int dotIndex = fileName.lastIndexOf('.');
        if (dotIndex > 0 && dotIndex < fileName.length() - 1) {
            return fileName.substring(dotIndex + 1);
        }
        return ""; // 没有扩展名
    }

    /**
     * 删除文件
     *
     * @param path
     * @return
     */
    public static void deleteFile(Path path) throws BingoException {
        try {
            // 删除文件
            Files.delete(path);
        } catch (NoSuchFileException e) {
            throw new BingoException(ExceptionCodeEnum.BINGO_ERROR.getCode(), "文件不存在: " + path);
        } catch (DirectoryNotEmptyException e) {
            throw new BingoException(ExceptionCodeEnum.BINGO_ERROR.getCode(), "目标目录不为空: " + path);
        } catch (IOException e) {
            throw new BingoException(ExceptionCodeEnum.BINGO_ERROR.getCode(), "删除文件时出错: " + path);
        }
    }

    /**
     * 保存文件到指定路径
     *
     * @param sourceFile      the file to be saved
     * @param destinationPath the target directory or file path to save the file
     * @throws IOException if an I/O error occurs
     */
    public static void saveFileToPath(File sourceFile, String destinationPath) throws IOException {
        // Ensure the source file exists
        if (!sourceFile.exists()) {
            throw new IOException("Source file does not exist: " + sourceFile.getAbsolutePath());
        }

        // Create target file object
        File destinationFile = new File(destinationPath);

        // Create parent directories if they do not exist
        File parentDir = destinationFile.getParentFile();
        if (parentDir != null && !parentDir.exists()) {
            parentDir.mkdirs();
        }

        // Use try-with-resources to ensure streams are closed properly
        try (FileInputStream fis = new FileInputStream(sourceFile);
             FileOutputStream fos = new FileOutputStream(destinationFile)) {

            byte[] buffer = new byte[1024 * 1024 * 10];
            int bytesRead;

            // Write the file in chunks
            while ((bytesRead = fis.read(buffer)) != -1) {
                fos.write(buffer, 0, bytesRead);
            }

            log.info("File saved successfully at: " + destinationFile.getAbsolutePath());
        } catch (IOException e) {
            throw new IOException("Error saving file to destination path: " + destinationPath, e);
        }
    }

    /**
     * 计算文件的 MD5 值
     *
     * @param path 文件路径
     * @return 文件的 MD5 值
     */
    public static String calculateMD5(Path path) {
        try {
            // 创建 MD5 摘要实例
            MessageDigest md = MessageDigest.getInstance("MD5");

            // 读取文件的输入流
            try (InputStream fis = Files.newInputStream(path)) {
                // 每次读取 50M 的数据块
                byte[] buffer = new byte[1024 * 1024 * 50];
                int bytesRead;

                // 逐块读取文件并更新 MD5 摘要
                while ((bytesRead = fis.read(buffer)) != -1) {
                    md.update(buffer, 0, bytesRead);
                }
            }

            // 获取计算后的 MD5 值
            byte[] md5Bytes = md.digest();

            // 转换成 16 进制字符串
            StringBuilder sb = new StringBuilder();
            for (byte b : md5Bytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (Exception e) {
            throw new BingoException(ExceptionCodeEnum.BINGO_ERROR.getCode(), "计算文件的 MD5 值错误: " + ExceptionUtil.getMessage(e));
        }
    }

    /**
     * 将本地图片文件通过响应体返回
     *
     * @param localImagePath
     * @param resp
     */
    private void getImage(String localImagePath, HttpServletResponse resp) throws IOException {
        Path file = Paths.get(localImagePath);
        try (FileChannel fileChannel = FileChannel.open(file); ServletOutputStream os = resp.getOutputStream()) {
            long size = fileChannel.size();
            String contentType = Files.probeContentType(file);
            if (contentType == null) {
                contentType = probContentType(localImagePath);
            }
            resp.setContentType(contentType);
            resp.setContentLengthLong(size);
            resp.setHeader(HttpHeaders.CACHE_CONTROL, "max-age=13600");

            long position = 0;
            WritableByteChannel channel = Channels.newChannel(os);
            while (size > 0) {
                long count = fileChannel.transferTo(position, size, channel);
                if (count > 0) {
                    position += count;
                    size -= count;
                }
            }
        } catch (IOException e) {
            throw new IOException("Error return response: ", e);
        }
    }

    private String probContentType(String filename) {
        if (filename.endsWith(".svg")) {
            return "image/svg+xml";
        }
        return MediaType.APPLICATION_OCTET_STREAM_VALUE;
    }
}
