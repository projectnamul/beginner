package org.namul.api.payload.core.response;

import java.io.Serializable;

/**
 * The top-level contract for all standardized API response structures.
 * <p>
 * This interface must be implemented by classes that carry the actual response data
 * sent to the client. Developers should implement this interface to define the
 * specific fields required for their business requirements—such as status codes,
 * descriptive messages, timestamps, or the result payload itself.
 * <p>
 * By providing a unified structure through this interface, the application ensures
 * a consistent and predictable API contract for all consumers.
 */
public interface BaseResponse extends Serializable {
}