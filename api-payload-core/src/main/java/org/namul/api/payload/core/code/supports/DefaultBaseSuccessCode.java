package org.namul.api.payload.core.code.supports;

import org.namul.api.payload.core.code.BaseSuccessCode;

public interface DefaultBaseSuccessCode extends BaseSuccessCode {

    String getCode();
    String getMessage();
}
