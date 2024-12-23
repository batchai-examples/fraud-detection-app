/*
 * fraud-detection-app - fraud detection app
 * Copyright © 2024 Yiting Qiang (qiangyt@wxcount.com)
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package qiangyt.fraud_detection.app.service;

import jakarta.annotation.PreDestroy;
import jakarta.validation.constraints.NotNull;
import java.util.concurrent.CompletableFuture;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import qiangyt.fraud_detection.app.alert.Alerter;
import qiangyt.fraud_detection.app.engine.DetectionEngine;
import qiangyt.fraud_detection.app.queue.DetectionRequestQueue;
import qiangyt.fraud_detection.framework.json.Jackson;
import qiangyt.fraud_detection.sdk.DetectionApi;
import qiangyt.fraud_detection.sdk.DetectionReq;
import qiangyt.fraud_detection.sdk.DetectionReqEntity;
import qiangyt.fraud_detection.sdk.DetectionResult;

/** Service for handling fraud detection requests. */
@Service
@lombok.Getter
@lombok.Setter
@lombok.extern.slf4j.Slf4j
public class DetectionService implements DetectionApi {

    @Autowired DetectionRequestQueue requestQueue;

    @Autowired DetectionEngine engine;

    @Autowired ThreadPoolTaskExecutor detectionTaskExecutor;

    @Autowired Jackson jackson;

    @Autowired Alerter alertor;

    /** Shuts down the task executor. */
    @PreDestroy
    public void shutdown() {
        log.info("Shut down task executor: begin");
        getDetectionTaskExecutor().shutdown();
        log.info("Shut down task executor: done");
    }

    /**
     * Submits a detection request.
     *
     * @param req the detection request
     * @return the detection request entity
     */
    @Override
    public @NotNull DetectionReqEntity submit(@NotNull DetectionReq req) {
        var entity = req.toEntity();
        getRequestQueue().send(entity);
        return entity;
    }

    /**
     * Detects fraud and sends an alert if fraud is detected.
     *
     * @param entity the detection request entity
     * @return a CompletableFuture containing the detection result
     */
    public @NotNull CompletableFuture<DetectionResult> detectThenAlert(
            @NotNull DetectionReqEntity entity) {
        // uses dedicated task pool to execute engine
        var result =
                CompletableFuture.supplyAsync(() -> detect(entity), getDetectionTaskExecutor());

        // use default pool to log and send alert
        return result.thenApplyAsync(
                (prevResult) -> {
                    if (prevResult.getCategory().yes) {
                        log.warn("fraud detected: " + jackson.str(prevResult));
                        getAlertor().send(prevResult);
                    }
                    return prevResult;
                });
    }

    /**
     * Detects fraud based on the given detection request entity.
     *
     * @param entity the detection request entity
     * @return the detection result
     */
    @Override
    public DetectionResult detect(DetectionReqEntity entity) {
        var category = getEngine().detect(entity);
        return DetectionResult.from(entity, category);
    }
}
