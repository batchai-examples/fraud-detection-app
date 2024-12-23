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
package qiangyt.fraud_detection.app.engine;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import qiangyt.fraud_detection.sdk.DetectionReqEntity;
import qiangyt.fraud_detection.sdk.FraudCategory;

/** Unit test for the DroolsRuleEngine class. */
public class DroolsRuleEngineTest {

    /**
     * Tests the detect method of DroolsRuleEngine. Ensures that a DetectionReqEntity with default
     * values results in FraudCategory.NONE.
     */
    @Test
    public void testDetect() {
        DroolsRuleEngine engine = new DroolsRuleEngine();
        DetectionReqEntity entity = new DetectionReqEntity();

        // Set up entity with test data
        FraudCategory result = engine.detect(entity);

        // Assert that the result is FraudCategory.NONE
        assertEquals(FraudCategory.NONE, result);
    }
}
