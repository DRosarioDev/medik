package com.rosariodev.medik.util;

import java.util.Comparator;

import com.rosariodev.medik.model.Appuntamento;

public class AppuntamentoComparator implements Comparator<Appuntamento> {

    @Override
    public int compare(Appuntamento a1, Appuntamento a2) {
        return a2.getDataInizio().compareTo(a1.getDataInizio());
    }
    
}
