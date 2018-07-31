package com.banchan.domain.upload;

import com.banchan.domain.question.Reason;
import lombok.Data;

@Data
public class UploadResponse {

    public enum Type{
        SUCCESS, FAIL;
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

    public static ResponseBuilder fail(){
        return new ResponseBuilder(Type.FAIL);
    }

    public static class ResponseBuilder{
        private Type type;

        private ResponseBuilder(Type type){
            this.type = type;
        }

        public UploadResponse reason(Reason reason){
            return new UploadResponse(type, reason);
        }
    }
}
