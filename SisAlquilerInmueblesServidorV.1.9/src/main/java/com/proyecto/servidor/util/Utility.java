package com.proyecto.servidor.util;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Utility {
//tiene  que ser  fecha de  registro
    public List<Integer> getYears(){
        List<Integer> months = new ArrayList<>();
        int currentYear = Calendar.getInstance().get(Calendar.MONTH);

        for(Integer i=2000; i<= currentYear; i++){
        	months.add(i);
        }
        return months;
    }
}
