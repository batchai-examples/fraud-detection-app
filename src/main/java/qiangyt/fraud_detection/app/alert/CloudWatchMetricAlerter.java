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
package qiangyt.fraud_detection.app.alert;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import qiangyt.fraud_detection.sdk.DetectionResult;
import software.amazon.awssdk.services.cloudwatch.CloudWatchClient;
import software.amazon.awssdk.services.cloudwatch.model.Dimension;
import software.amazon.awssdk.services.cloudwatch.model.MetricDatum;
import software.amazon.awssdk.services.cloudwatch.model.PutMetricDataRequest;
import software.amazon.awssdk.services.cloudwatch.model.StandardUnit;

/** Alerter implementation that sends metrics to AWS CloudWatch. */
@lombok.extern.slf4j.Slf4j
@lombok.Getter
@lombok.Setter
@Service
public class CloudWatchMetricAlerter implements Alerter {

    @Autowired CloudWatchClient client;

    @Autowired GroupedAlerter group;

    /** Initializes the alerter by registering it with the grouped alerter. */
    @PostConstruct
    void init() {
        getGroup().registerAlerter(this);
    }

    /**
     * Sends a detection result as a metric to AWS CloudWatch.
     *
     * @param result the detection result to be sent
     */
    @Override
    public void send(DetectionResult result) {
        var dim = Dimension.builder().name("id").value(result.getId()).build();

        var metric =
                MetricDatum.builder()
                        .metricName(result.getCategory().name())
                        .dimensions(dim)
                        .value(1.0)
                        .unit(StandardUnit.COUNT)
                        .build();
        var req = PutMetricDataRequest.builder().namespace("fraud").metricData(metric).build();

        getClient().putMetricData(req);
        // log.info("Metric sent to CloudWatch: " + JacksonHelper.to(1));
    }
}
