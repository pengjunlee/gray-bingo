package gray.bingo.starter.validation.file;

import gray.bingo.common.utils.FileUtil;
import gray.bingo.common.utils.StringUtil;
import org.apache.tika.Tika;
import org.apache.tika.io.TikaInputStream;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class FileValidator implements ConstraintValidator<ValidFile, MultipartFile> {
    private final Tika tika = new Tika();
    private List<String> extensions;
    private List<String> mimeTypes;
    private long maxSize;

    @Override
    public void initialize(ValidFile constraintAnnotation) {
        extensions = Arrays.asList(constraintAnnotation.extensions());
        mimeTypes = Arrays.asList(constraintAnnotation.mimeTypes());
        maxSize = constraintAnnotation.maxSize();
    }

    @Override
    public boolean isValid(MultipartFile file, ConstraintValidatorContext context) {
        if (file == null || file.isEmpty()) {
            return true;
        }
        // 1. 文件大小验证
        if (file.getSize() > maxSize) {
            return false;
        }
        // 2. 文件扩展名验证
        String fileName = file.getOriginalFilename();
        // String fileExtension = fileName.substring(fileName.lastIndexOf(".") + 1);
        String fileExtension = FileUtil.getFileExtension(file.getOriginalFilename());
        if (StringUtil.isNotBlank(fileExtension) && extensions.contains(fileExtension.toLowerCase())) {
            return true;
        }
        // 3. 这里使用apache tika验证文件mime,实际是通过文件头内容中的魔法数来验证的
        String detect = null;
        try {
            detect = tika.detect(TikaInputStream.get(file.getInputStream()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return mimeTypes.contains(detect);
    }
}