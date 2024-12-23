package com.rahul.journal_app.api.response;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
@Data
@NoArgsConstructor
public class WeatherResponse{
    private Current current;


    @Getter
    @Setter
    public class Current{

        private int temperature;
        @JsonProperty("weather_descriptions")
        private List<String> weatherDescriptions;
        private int feelslike;

    }



}





