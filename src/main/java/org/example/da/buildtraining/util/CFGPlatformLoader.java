package org.example.da.buildtraining.util;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import lombok.SneakyThrows;
import org.example.da.buildtraining.map.Platform;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Type;

import java.util.List;

public class CFGPlatformLoader {
    private Gson gson = new Gson();

    @SneakyThrows
    public List<Platform> loadPlatformCFG(InputStream jsonFile){
        try (Reader reader = new InputStreamReader(jsonFile)){
            Type listType = new TypeToken<List<Platform>>() {}.getType();
            return gson.fromJson(reader, listType);
        }
    }
}
