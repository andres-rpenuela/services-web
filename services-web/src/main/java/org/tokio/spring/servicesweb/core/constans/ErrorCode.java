package org.tokio.spring.servicesweb.core.constans;

/**
 * Class with pairs of <code,message> of errors
 *
 * More info: <a href='https://developer.mozilla.org/es/docs/Web/HTTP/Status'>
 */
public interface ErrorCode {
    int NO_ERROR = 0;
    String NO_MESSAGE = "";

    int INTERNAL_ERROR = 500;
    String INTERNAL_ERROR_MESSAGE = "Internal Error";

    int NOT_FOUND = 400;
    String NOT_FOUND_MESSAGE = "Not Found";
}
