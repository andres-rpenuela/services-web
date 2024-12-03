package org.tokio.spring.servicesweb.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.tokio.spring.servicesweb.core.constans.ErrorCode;
import org.tokio.spring.servicesweb.core.response.ResponseError;

@Data
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ErrorDTO {
    @Schema(name = "errorCode",description = "código de error http devuelto en la operación")
    private Long errorCode;
    @Schema(name = "message",description = "mensage de error http devuelto en la operación")
    private String message;

    public static ResponseError<ErrorDTO> noErrorResponse() {
        final ErrorDTO errorDTO = new ErrorDTO( (long) ErrorCode.NO_ERROR, ErrorCode.NO_MESSAGE);
        return  ResponseError.<ErrorDTO>builder().error(errorDTO).build();
    }

    public static ResponseError<ErrorDTO> errorResponse(int errorCodee, String message) {
        final ErrorDTO errorDTO = new ErrorDTO( (long) errorCodee, message);
        return  ResponseError.<ErrorDTO>builder().error(errorDTO).build();
    }
}
