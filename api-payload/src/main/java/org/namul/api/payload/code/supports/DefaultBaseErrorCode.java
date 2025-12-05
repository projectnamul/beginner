package org.namul.api.payload.code.supports;

import org.namul.api.payload.code.BaseErrorCode;

public interface DefaultBaseErrorCode extends BaseErrorCode {

    String getCode();
    String getMessage();
}
