package smart.authority.common.model;

import smart.authority.common.exception.ErrorCode;
import lombok.Data;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

@Data
public class ApiResponse<T> {
    private int code;
    private String chMessage;
    private String enMessage;
    private T data;
    private boolean success;

    private ApiResponse(int code, String chMessage, String enMessage, T data, boolean success) {
        this.code = code;
        this.chMessage = chMessage;
        this.enMessage = enMessage;
        this.data = data;
        this.success = success;
    }

    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(ErrorCode.SUCCESS.getCode(), 
            ErrorCode.SUCCESS.getChMessage(), 
            ErrorCode.SUCCESS.getEnMessage(), 
            data, 
            true);
    }

    public static <T> ApiResponse<T> error(ErrorCode errorCode) {
        return new ApiResponse<>(errorCode.getCode(), 
            errorCode.getChMessage(), 
            errorCode.getEnMessage(), 
            null, 
            false);
    }

    public static <T> ApiResponse<T> error(ErrorCode errorCode, String chMessage, String enMessage) {
        return new ApiResponse<>(errorCode.getCode(), chMessage, enMessage, null, false);
    }

    public static <T> ApiResponse<T> error(int code, String chMessage, String enMessage) {
        return new ApiResponse<>(code, chMessage, enMessage, null, false);
    }

    public static <T> ApiResponse<T> localized(ErrorCode errorCode, MessageSource messageSource) {
        String chMessage = messageSource.getMessage("error." + errorCode.name().toLowerCase() + ".ch", null, LocaleContextHolder.getLocale());
        String enMessage = messageSource.getMessage("error." + errorCode.name().toLowerCase() + ".en", null, LocaleContextHolder.getLocale());
        return new ApiResponse<>(errorCode.getCode(), chMessage, enMessage, null, false);
    }
}