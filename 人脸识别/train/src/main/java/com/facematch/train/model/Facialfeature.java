package com.facematch.train.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @description:
 * @website: http://www.ziroom.com
 * @author: zhouhy
 * @email: zhouhy@ziroom.com
 * @date: 2020-05-03 23:37
 * @since: 1.0.0
 */
@Builder(toBuilder = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Facialfeature implements Serializable {
    private static final long serialVersionUID = -2450302711737102401L;

    private String eyesType;

    private String mouthType;

    private BigDecimal goldenTriangle;

    private String righteyeEmptyResult;

    private String lefteyeEmptyResult;

    private String eyeinResult;

    private String jawType;

    private String faceupResult;

    private String facedownResult;

    private String facemidResult;

    private  String noseType;

    private String eyebrowType;

    private String faceType;
}
