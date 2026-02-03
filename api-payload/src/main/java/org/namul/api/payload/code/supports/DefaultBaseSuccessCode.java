package org.namul.api.payload.code.supports;

import org.namul.api.payload.code.BaseSuccessCode;

public interface DefaultBaseSuccessCode extends BaseSuccessCode {

    String getCode();
    String getMessage();
}
