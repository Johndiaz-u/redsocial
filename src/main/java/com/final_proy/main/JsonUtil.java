package com.final_proy.main;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import spark.ResponseTransformer;


public class JsonUtil {

    public static String toJson(Object object) {
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        return gson.toJson(object);

    }

    public static ResponseTransformer json() {
        return JsonUtil::toJson;
    }
}
