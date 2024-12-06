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

import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import qiangyt.fraud_detection.app.service.DetectionService;
import qiangyt.fraud_detection.framework.misc.UuidHelper;
import qiangyt.fraud_detection.framework.test.AbstractRestTest;
import qiangyt.fraud_detection.sdk.DetectionReqEntity;
import qiangyt.fraud_detection.sdk.DetectionResult;

/**
 * @author
 */
@Disabled
@ContextConfiguration(classes = DetectionRestController.class)
@WebMvcTest(DetectionRestController.class)
public class DetectionRestControllerTest extends AbstractRestTest {

    @MockBean DetectionService service;

    @Test
    void test_detect() {
        String id = UuidHelper.shortUuid();
        var entity = DetectionReqEntity.builder().id(id).build();
        var result = DetectionResult.builder().entity(entity).build();

        when(this.service.detect(entity)).thenReturn(result);

        getThenExpectOk(result, "/rest/detection");
    }
}