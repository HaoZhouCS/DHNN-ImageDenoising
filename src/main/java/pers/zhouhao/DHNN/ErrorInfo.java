package pers.zhouhao.DHNN;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErrorInfo {

    private ErrorCode code = ErrorCode.IS_OK;
    private String info = "";

    public void setCode(ErrorCode _code) {
        code = _code;
        info = _code.toString();
    }

    public enum ErrorCode {
        IS_OK,
        INIT_STUDY_INPUTNODE_NUM_ERROR,
        INIT_STUDY_OUTPUTNODE_NUM_ERROR,
        INIT_NODE_NUM_ERROR,
        SAMPLE_NODE_NUM_ERROR;
    }
}

