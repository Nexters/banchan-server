package com.banchan.response;

import java.util.Map;
import java.util.Set;

public class ImageUploadResponse{

    private Set request;
    private Map response;
    // 나만의 응답 클래스를 만듬
    // 이미지 사이즈 0, 이미지가 null, 이미지 삽입 성공, 이미지 사이즈 초과
    // 각 리퀘스트가 성공했는지 실패했는지를 파악해서 보내줌
    // IMG_A: { type: success  ... }, IMG_Q : { type: fail, reason: outOfSize }
    // IMG_B: { type: fail, reason: sizeZero or imgNull }
    // 이미지가 다 보내지거나 아니면 안 보내지거나 할까? 그게 편한가? aws 에서 지원하는가?


}
