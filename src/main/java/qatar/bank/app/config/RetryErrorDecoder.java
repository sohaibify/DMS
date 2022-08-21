package qatar.bank.app.config;

import feign.Response;
import feign.RetryableException;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

/**
 * author: sohaib yasir
 * Custom RetryErrorDecoder instead of default. Implemented retry on every 5XX exception.
 */
@Slf4j
@Component
public class RetryErrorDecoder implements ErrorDecoder {
    private final ErrorDecoder defaultErrorDecoder = new Default();

    @Override
    public Exception decode(String s, Response response) {
        Exception exception = defaultErrorDecoder.decode(s, response);
        if(exception instanceof RetryableException){
            return exception;
        }

        if(HttpStatus.valueOf(response.status()).is5xxServerError()){
            RetryableException retryableException = new RetryableException(0, "5XX error", response.request().httpMethod(), null, null);
            return retryableException;
        }

        return exception;
    }
}

