package com.banchan.model.response;

import lombok.Data;

@Data
public class UploadResponse {

    public enum Type{
        SUCCESS, FAIL;
    }

    // 이야기 해서 실패한 사유를 적어서 프론트에서 처리할지
    // 아니면
    public enum Reason{
        OUT_OF_SIZE, NO_FILE, UNKNOWN;
    }

    private Type type;
    private Reason reason;

    private UploadResponse(Type type, Reason reason) {
        this.type = type;
        this.reason = reason;
    }

    public static UploadResponse success(){
        return new UploadResponse(Type.SUCCESS, null);
    }

    public static UploadResponse fail(Reason reason){
        return new UploadResponse(Type.FAIL, reason);
    }
}
