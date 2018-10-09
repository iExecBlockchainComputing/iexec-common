package com.iexec.common.result;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResultModel {

    private String taskId;
    private String image;
    private String cmd;
    private byte[] zip;

}