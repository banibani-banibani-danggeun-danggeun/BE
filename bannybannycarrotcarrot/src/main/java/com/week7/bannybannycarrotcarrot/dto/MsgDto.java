package com.week7.bannybannycarrotcarrot.dto;

import java.util.List;

public class MsgDto {
    public record ResponseDto(String msg, int statusCode) {
    }

//    public record PostModifiedResponseDto(String msg, int statusCode, List<String> data) {
//
//    }
}