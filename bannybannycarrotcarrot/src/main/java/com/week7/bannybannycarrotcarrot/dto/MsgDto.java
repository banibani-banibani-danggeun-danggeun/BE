package com.week7.bannybannycarrotcarrot.dto;

import java.util.List;

public class MsgDto {




    public record ResponseDto(String msg, int statusCode) {
    }

    public record DataResponseDto(String msg, int statusCode, Object data) {

    }
}