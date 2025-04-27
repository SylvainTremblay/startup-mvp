package com.csofdv.bmvpf.service;

import com.csofdv.bmvpf.dto.PredictionDto;
import com.csofdv.bmvpf.dto.PredictionMetadataDto;
import com.csofdv.bmvpf.dto.PredictionResponseDto;
import com.google.cloud.aiplatform.v1.EndpointName;
import com.google.cloud.aiplatform.v1.PredictResponse;
import com.google.cloud.aiplatform.v1.PredictionServiceClient;
import com.google.cloud.aiplatform.v1.PredictionServiceSettings;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.util.JsonFormat;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class PredictionService {

    @Value("${vertexai.project-id}")
    private String projectId;

    @Value("${vertexai.location}")
    private String location;

    @Value("${vertexai.endpoint}")
    private String endpoint;

    @Value("${vertexai.publisher}")
    private String publisher;

    @Value("${vertexai.model}")
    private String model;

    @Value("${vertexai.temperature}")
    private String temperature;

    @Value("${vertexai.max-output-tokens}")
    private String maxOutputTokens;

    private EndpointName endpointName;

    private PredictionServiceSettings predictionServiceSettings;

    private PredictionServiceClient predictionServiceClient;

    @PostConstruct
    void init() throws Exception {
        this.endpointName = EndpointName.ofProjectLocationPublisherModelName(projectId, location, publisher, model);
        this.predictionServiceSettings = PredictionServiceSettings.newBuilder().setEndpoint(endpoint).build();
        this.predictionServiceClient = PredictionServiceClient.create(predictionServiceSettings);
    }

    // Use Codey for Code Generation to generate a code function
    public PredictionResponseDto generateCode(String prompt, String parameters) throws PredictionException {

        try {
            com.google.protobuf.Value parameterValue = stringToValue(parameters);
            com.google.protobuf.Value instanceValue = stringToValue(prompt);
            List<com.google.protobuf.Value> promptValues = new ArrayList<>();
            promptValues.add(instanceValue);

            PredictResponse predictResponse =
                    predictionServiceClient.predict(endpointName, promptValues, parameterValue);
            System.out.println(predictResponse);

            PredictionResponseDto predictionResponseDto = new PredictionResponseDto();
            predictionResponseDto.setPredictionMetadataDto(extractMetadata(predictResponse.getMetadata()));
            predictionResponseDto.setPredictionList(extractPredictions(predictResponse.getPredictionsList()));
            return predictionResponseDto;
        } catch (Exception e) {
            throw new PredictionException(e);
        }
    }

    private PredictionMetadataDto extractMetadata(com.google.protobuf.Value metadata) {
        double[] outputTokenCounts = {0,0};
        double[] inputTokenCounts = {0,0};

        if (metadata != null && metadata.getStructValue() != null) {
            Map<String, com.google.protobuf.Value> fieldsMap = metadata.getStructValue().getFieldsMap();
            if (fieldsMap.containsKey("tokenMetadata")) {
                Map<String, com.google.protobuf.Value> tokenMetadataFieldsMap = fieldsMap.get("tokenMetadata").getStructValue().getFieldsMap();
                outputTokenCounts = extractTokenCount(tokenMetadataFieldsMap, "outputTokenCount");
                inputTokenCounts = extractTokenCount(tokenMetadataFieldsMap, "inputTokenCount");
            }
        }
        PredictionMetadataDto predictionMetadataDto = new PredictionMetadataDto();
        predictionMetadataDto.setInputCharacters(inputTokenCounts[0]);
        predictionMetadataDto.setInputTokens(inputTokenCounts[1]);
        predictionMetadataDto.setOutputCharacters(outputTokenCounts[0]);
        predictionMetadataDto.setOutputTokens(outputTokenCounts[1]);

        return predictionMetadataDto;
    }

    private List<PredictionDto> extractPredictions(List<com.google.protobuf.Value> valueList) {

        List<PredictionDto> predictionList = new ArrayList<>();
        for (com.google.protobuf.Value value : valueList) {
            PredictionDto prediction = new PredictionDto();
            predictionList.add(prediction);
            Map<String, com.google.protobuf.Value> fieldsMap = value.getStructValue().getFieldsMap();
            if (fieldsMap.containsKey("score")) {
                prediction.setScore(fieldsMap.get("score").getNumberValue());
            }
            if (fieldsMap.containsKey("content")) {
                prediction.setContent(fieldsMap.get("content").getStringValue());
            }
        }
        return predictionList;
    }

    private double[] extractTokenCount(Map<String, com.google.protobuf.Value> tokenMetadataFieldsMap, String tokenKey) {
        double[] tokens = new double[2];
        if (tokenMetadataFieldsMap.containsKey(tokenKey)) {
            Map<String, com.google.protobuf.Value> tokenFieldsMap = tokenMetadataFieldsMap.get(tokenKey).getStructValue().getFieldsMap();
            if (tokenFieldsMap.containsKey("totalBillableCharacters")) {
                tokens[0] = tokenFieldsMap.get("totalBillableCharacters").getNumberValue();
            }
            if (tokenFieldsMap.containsKey("totalTokens")) {
                tokens[1] = tokenFieldsMap.get("totalTokens").getNumberValue();
            }
        }
        return tokens;
    }
    private com.google.protobuf.Value stringToValue(String value) throws InvalidProtocolBufferException {
        com.google.protobuf.Value.Builder builder = com.google.protobuf.Value.newBuilder();
        JsonFormat.parser().merge(value, builder);
        return builder.build();
    }
}
