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

@lombok.Getter
/**
 * Represents an error that occurs during a remote operation. Extends the {@link BadRequest} class
 * to provide additional context about the error response received from the remote service.
 */
public class RemoteError extends BadRequest {

    final ErrorResponse response;

    /**
     * Constructs a new {@code RemoteError} with the specified error response.
     *
     * @param resp the error response received from the remote service
     */
    public RemoteError(ErrorResponse resp) {
        super(resp.getCode(), resp.getMessage(), resp.getParams());
        this.response = resp;
    }
}
