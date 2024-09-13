package gray.bingo.starter.validation.file;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = FileValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidFile {

    /**
     * @PostMapping("/upload")
     * public ResponseEntity<?> uploadFile(
     * @RequestParam("file") @NotNull @ValidFile(extensions = {"jpg", "png"},
     * mimeTypes = {"image/jpeg", "image/png"},
     * maxSize = 2 * 1024 * 1024) MultipartFile file)
     * @return
     */
    String message() default "{constraints.ValidFileMimeType.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String[] extensions() default {};

    String[] mimeTypes() default {};

    long maxSize() default 1024 * 1024; // 默认最大1MB
}