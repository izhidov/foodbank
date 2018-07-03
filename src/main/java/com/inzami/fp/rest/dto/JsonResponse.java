package com.inzami.fp.rest.dto;

import com.inzami.fp.enums.ResponseType;
import lombok.Data;

@Data
public class JsonResponse<G> {

    private G result;
    private ResponseType responseType;

    public static <G> JsonResponse<G> success(G response) {
        JsonResponse<G> jsonResponse = new JsonResponse<>();
        jsonResponse.setResponseType(ResponseType.SUCCESS);
        jsonResponse.setResult(response);
        return jsonResponse;
    }

    public static <G> JsonResponse<G> error() {
        JsonResponse<G> jsonResponse = new JsonResponse<>();
        jsonResponse.setResponseType(ResponseType.ERROR);
        return jsonResponse;
    }

    public static <G> JsonResponse<G> formError(G error) {
        JsonResponse<G> jsonResponse = new JsonResponse<>();
        jsonResponse.setResponseType(ResponseType.FORM_ERROR);
        jsonResponse.setResult(error);
        return jsonResponse;
    }
}
