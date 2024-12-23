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
package qiangyt.fraud_detection.app.controller;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import qiangyt.fraud_detection.app.service.DetectionService;
import qiangyt.fraud_detection.sdk.DetectionReq;
import qiangyt.fraud_detection.sdk.DetectionReqEntity;
import qiangyt.fraud_detection.sdk.DetectionResult;

/**
 * REST controller for handling fraud detection requests. Provides endpoints for submitting
 * detection requests both asynchronously and synchronously.
 */
@Validated
@lombok.Getter
@lombok.Setter
@RestController
@RequestMapping(path = "/rest/detection", produces = APPLICATION_JSON_VALUE)
@Tag(name = "detection", description = "the detection RESTful API")
public class DetectionRestController {

    @Autowired DetectionService service;

    /**
     * Submits a detection request for asynchronous processing. Use POST verb because it will send
     * something to a queue.
     *
     * @param req the detection request
     * @return the detection request entity
     */
    @PostMapping()
    public DetectionReqEntity submit(@Valid @RequestBody DetectionReq req) {
        return getService().submit(req);
    }

    /**
     * Submits a detection request for synchronous processing. Only for test purposes to help with
     * integration testing.
     *
     * <p>Uses GET verb because it is just a pure calculation, doesn't change any state, that is,
     * idempotent.
     *
     * @param entity the detection request entity
     * @return the detection result
     */
    @GetMapping()
    public DetectionResult detect(@Valid @RequestBody DetectionReqEntity entity) {
        return getService().detect(entity);
    }
}
