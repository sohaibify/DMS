package qatar.bank.app.config;

import feign.RetryableException;
import feign.Retryer;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * author: sohaib yasir
 * Retry configuration customized. Can be externalized later.
 */
@Slf4j
@Component
@NoArgsConstructor
public class PassCommentRetryConfig implements Retryer {

    @Value("${post.client.retryMaxAttempt}")
    private int retryMaxAttempt;
    @Value("${post.client.retryInterval}")
    private long retryInterval;
    private int attempt = 1;

    @Override
    public void continueOrPropagate(RetryableException e) {
        log.info("Retry No: {} due to {}", attempt, e.getMessage());
        if(attempt++ == retryMaxAttempt) {
            throw e;
        }
        try {
            Thread.sleep(retryInterval);
        } catch (InterruptedException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public Retryer clone() {
        return new PassCommentRetryConfig();
    }
}
