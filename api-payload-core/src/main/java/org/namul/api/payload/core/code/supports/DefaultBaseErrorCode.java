package org.namul.api.payload.core.code.supports;

import org.namul.api.payload.core.code.BaseErrorCode;

public interface DefaultBaseErrorCode extends BaseErrorCode {

    String getCode();
    String getMessage();
}
