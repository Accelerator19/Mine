package com.facematch.train.web;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.facematch.train.model.Facialfeature;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


@Slf4j
@RestController
public class UploadController {



    @Value("${urlPath}")
    private String urlPath;

    @Value("${facePlusPlus.api_key}")
    private String apiKey;


    @Value("${facePlusPlus.api_secret}")
    private String apiSecret;


    @Value("${facePlusPlus.facialfeaturesUrl}")
    private String facialfeaturesUrl;


    @PostMapping("/upload")
    public String upload(@RequestParam("multipartFiles") MultipartFile[] files) {
        JSONObject jsonObject = new JSONObject();
        if (files.length == 0) {
            return jsonObject.toJSONString();
        }
        String result = null;
        List<Facialfeature> list = new ArrayList<>();

        if (null != files && files.length > 0) {
            for (MultipartFile file : files) {
                SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
                factory.setBufferRequestBody(false);
                RestTemplate restTemplate = new RestTemplate(factory);
                MultiValueMap<String, Object> multiValueMap = new LinkedMultiValueMap<>();
                HttpHeaders header = new HttpHeaders();
                header.setContentType(MediaType.MULTIPART_FORM_DATA);

                HttpHeaders fileHeader = new HttpHeaders();
                fileHeader.setContentType(MediaType.parseMediaType(file.getContentType()));
                fileHeader.setContentDispositionFormData("image_file", file.getOriginalFilename());
                try {
                    HttpEntity<ByteArrayResource> fileEntity = new HttpEntity<>(new ByteArrayResource(file.getBytes()),
                            fileHeader);
                    multiValueMap.add("api_key", apiKey);
                    multiValueMap.add("api_secret", apiSecret);
                    multiValueMap.add("image_file", fileEntity);


                    HttpEntity<MultiValueMap<String, Object>> httpEntity = new HttpEntity<>(multiValueMap, header);
                    ResponseEntity<String> postForEntity = restTemplate.postForEntity(facialfeaturesUrl, httpEntity, String.class);

                    String response = postForEntity.getBody();
                    JSONObject responseJson = JSON.parseObject(response);
                    String image_id = responseJson.getString("image_id");
                    JSONObject resultJson = responseJson.getJSONObject("result");
                    JSONObject eyesJson = resultJson.getJSONObject("eyes");
                    String eyes_type = eyesJson.getString("eyes_type");
                    String mouth_type = resultJson.getJSONObject("mouth").getString("mouth_type");
                    BigDecimal golden_triangle = resultJson.getBigDecimal("golden_triangle");
                    String righteye_empty_result = resultJson.getJSONObject("five_eyes").getJSONObject("one_eye").getString("righteye_empty_result");
                    String lefteye_empty_result = resultJson.getJSONObject("five_eyes").getJSONObject("five_eye").getString("lefteye_empty_result");
                    String eyein_result = resultJson.getJSONObject("five_eyes").getJSONObject("three_eye").getString("eyein_result");
                    String jaw_type = resultJson.getJSONObject("jaw").getString("jaw_type");
                    String faceup_result = resultJson.getJSONObject("three_parts").getJSONObject("one_part").getString("faceup_result");
                    String facedown_result = resultJson.getJSONObject("three_parts").getJSONObject("three_part").getString("facedown_result");
                    String facemid_result = resultJson.getJSONObject("three_parts").getJSONObject("two_part").getString("facemid_result");
                    String nose_type = resultJson.getJSONObject("nose").getString("nose_type");
                    String eyebrow_type = resultJson.getJSONObject("eyebrow").getString("eyebrow_type");
                    String face_type = resultJson.getJSONObject("face").getString("face_type");

                    Facialfeature facialfeature = Facialfeature.builder().
                            eyesType(eyes_type)
                            .mouthType(mouth_type)
                            .goldenTriangle(golden_triangle)
                            .righteyeEmptyResult(righteye_empty_result)
                            .lefteyeEmptyResult(lefteye_empty_result)
                            .eyeinResult(eyein_result)
                            .jawType(jaw_type)
                            .faceupResult(faceup_result)
                            .facedownResult(facedown_result)
                            .facemidResult(facemid_result)
                            .noseType(nose_type)
                            .eyebrowType(eyebrow_type)
                            .faceType(face_type)
                            .build();

                    list.add(facialfeature);

                    Thread.sleep(1000);
                } catch (Exception e) {
                    log.error("出现异常", e);
                    result = "";
                    break;
                }
            }

            if (files.length > 1 && list.size() > 1) {
                JSONObject compareResult = new JSONObject();
                log.info("图片一面部特征值为:{}，图片二面部特征值为:{}", JSON.toJSONString(list.get(0)), JSON.toJSONString(list.get(1)));
                Facialfeature one = list.get(0);
                Facialfeature two = list.get(1);
                String eyesTypeOne = one.getEyesType();
                String eyesTypeTwo = two.getEyesType();
                if (eyesTypeOne.equals(eyesTypeTwo)) {
                    compareResult.put("eyesType", "眼睛像");
                }

                String mouthTypeOne = one.getMouthType();
                String mouthTypeTwo = two.getMouthType();
                if (mouthTypeOne.equals(mouthTypeTwo)) {
                    compareResult.put("mouthType", "嘴唇像");
                }


                BigDecimal goldenTriangleOne = one.getGoldenTriangle();
                BigDecimal goldenTriangleTwo = two.getGoldenTriangle();
                if (goldenTriangleOne.compareTo(goldenTriangleTwo) < 0 || goldenTriangleOne.compareTo(goldenTriangleTwo) == 0) {
                    BigDecimal bigNum = goldenTriangleOne.divide(goldenTriangleTwo, 2);
                    int flag = bigNum.compareTo(BigDecimal.valueOf(0.98));
                    if (flag > 0 || flag == 0) {
                        compareResult.put("goldenTriangle", "黄金三角度数接近");
                    }
                }

                String righteyeEmptyResultOne = one.getRighteyeEmptyResult();
                String righteyeEmptyResultTwo = two.getRighteyeEmptyResult();
                if (righteyeEmptyResultOne.equals(righteyeEmptyResultTwo)) {
                    compareResult.put("righteyeEmptyResult", "五眼右侧像");
                }


                String lefteyeEmptyResultOne = one.getLefteyeEmptyResult();
                String lefteyeEmptyResultTwo = two.getLefteyeEmptyResult();
                if (lefteyeEmptyResultOne.equals(lefteyeEmptyResultTwo)) {
                    compareResult.put("lefteyeEmptyResult", "五眼左侧像");
                }


                String eyeinResultOne = one.getEyeinResult();
                String eyeinResultTwo = two.getEyeinResult();
                if (eyeinResultOne.equals(eyeinResultTwo)) {
                    compareResult.put("eyeinResult", "内眼角间距像");
                }


                String jawTypeOne = one.getJawType();
                String jawTypeTwo = two.getJawType();
                if (jawTypeOne.equals(jawTypeTwo)) {
                    compareResult.put("jawType", "下巴像");
                }


                String faceupResultOne = one.getFaceupResult();
                String faceupResultTwo = two.getFaceupResult();
                if (faceupResultOne.equals(faceupResultTwo)) {
                    compareResult.put("faceupResult", "上庭像");
                }

                String facedownResultOne = one.getFacedownResult();
                String facedownResultTwo = two.getFacedownResult();
                if (facedownResultOne.equals(facedownResultTwo)) {
                    compareResult.put("facedownResult", "下庭像");
                }


                String facemidResultOne = one.getFacemidResult();
                String facemidResultTwo = two.getFacemidResult();
                if (facemidResultOne.equals(facemidResultTwo)) {
                    compareResult.put("facemidResult", "中庭像");
                }


                String noseTypeOne = one.getNoseType();
                String noseTypeTwo = two.getNoseType();
                if (noseTypeOne.equals(noseTypeTwo)) {
                    compareResult.put("noseType", "鼻子像");
                }


                String eyebrowTypeOne = one.getEyebrowType();
                String eyebrowTypeTwo = two.getEyebrowType();
                if (eyebrowTypeOne.equals(eyebrowTypeTwo)) {
                    compareResult.put("eyebrowType", "眉毛像");
                }

                String faceTypeOne = one.getFaceType();
                String faceTypeTwo = two.getFaceType();
                if (faceTypeOne.equals(faceTypeTwo)) {
                    compareResult.put("faceType", "脸型像");
                }
                result = compareResult.toJSONString();
            }

        } else {
            result = new JSONObject().toJSONString();
        }


        return result;

    }


}