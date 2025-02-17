package me.kasper.profile;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Collections;
import java.util.List;

@Data
@AllArgsConstructor
public class Profile {
    private String name;
    private List<Double> times;

    public Double getBestTime(){
        if(times.isEmpty()) return 0.0;
        return Collections.min(times);
    }

    public void addTime(double time){
        times.add(time);
    }

    public void toNullRecord(){
        times.clear();
    }
}
