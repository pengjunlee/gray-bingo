package gray.bingo.common.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.WritableByteChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Slf4j
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


    public String put(String filename, InputStream inputStream) {
        File file = cn.hutool.core.io.FileUtil.newFile(filename);
        cn.hutool.core.io.FileUtil.writeFromStream(inputStream, file);
        return filename;
    }


    /**
     * 将本地图片文件通过响应体返回
     *
     * @param localImagePath
     * @param resp
     */
    private void getImage(String localImagePath, HttpServletResponse resp) {
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
            log.error(e.getMessage());
        }
    }

    private String probContentType(String filename) {
        if (filename.endsWith(".svg")) {
            return "image/svg+xml";
        }
        return MediaType.APPLICATION_OCTET_STREAM_VALUE;
    }
}
