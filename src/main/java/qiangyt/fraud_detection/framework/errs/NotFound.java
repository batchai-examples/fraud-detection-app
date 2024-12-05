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
package qiangyt.fraud_detection.framework.errs;

import org.springframework.http.HttpStatus;

/** HTTP NOT_FOUND */
public class NotFound extends BaseError {

    public NotFound(ErrorCode code, String messageFormat, Object... params) {
        super(HttpStatus.NOT_FOUND, code, messageFormat, params);
    }

    public NotFound(ErrorCode code, String message) {
        super(HttpStatus.NOT_FOUND, code, message);
    }

    public NotFound(ErrorCode code) {
        super(HttpStatus.NOT_FOUND, code);
    }

    public NotFound(ErrorCode code, Throwable cause, String messageFormat, Object... params) {
        super(HttpStatus.NOT_FOUND, code, cause, messageFormat, params);
    }

    public NotFound(ErrorCode code, Throwable cause, String message) {
        super(HttpStatus.NOT_FOUND, code, cause, message);
    }

    public NotFound(ErrorCode code, Throwable cause) {
        super(HttpStatus.NOT_FOUND, code, cause);
    }
}
