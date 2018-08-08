package com.banchan.model.response;

import com.google.common.collect.Maps;
import lombok.Data;
import lombok.Getter;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;

@Data
public class CommonResponse<T> {

    // static success 는 not thread-safe
    public static final CommonResponse SUCCESS = new CommonResponse(Type.SUCCESS);
    public static final CommonResponse FAIL = new CommonResponse(Type.FAIL);

    private Type type;
    private String reason;
    private T data;

    private CommonResponse(Type type) {
        this.type = type;
    }

    private CommonResponse(Type type, T data) {
        this.type = type;
        this.data = data;
    }

    private CommonResponse(Type type, String reason, T data) {
        this.type = type;
        this.reason = reason;
        this.data = data;
    }

    public static <T> CommonResponse<T> success(T data){
        return new CommonResponse<>(Type.SUCCESS, data);
    }

    public static ResponseBuilder fail(){
        return new ResponseBuilder(Type.FAIL);
    }

    public static ResponseBuilder fail(String reason){
        return new ResponseBuilder(Type.FAIL, reason);
    }

    public static class ResponseBuilder{
        private Type type;
        private String reason;

        private ResponseBuilder(Type type) {
            this.type = type;
        }

        private ResponseBuilder(Type type, String reason) {
            this.type = type;
            this.reason = reason;
        }

        public ResponseBuilder reason(String reason){
            this.reason = reason;
            return this;
        }

        public <T> CommonResponse<T> Data(T data){
            return new CommonResponse<>(this.type, this.reason, data);
        }
    }

    private enum Type{
        SUCCESS(1), FAIL(2);

        @Getter private Integer value;
        private static final Map<Integer, Type> map = Maps.newHashMap();

        Type(Integer value) {
            this.value = value;
        }

        static {
            Arrays.stream(Type.values())
                    .forEach(type -> map.put(type.value, type));
        }

        public static Type valueOf(Integer value){
            return Optional.ofNullable(map.get(value))
                    .orElseThrow(IllegalArgumentException::new);
        }
    }
}
