package com.example.restaurante.util;

public final class TipoComidaUtil {

    private static final String[] TIPOS_BD = {"food", "drink", "complement"};

    private TipoComidaUtil() {
    }

    public static String obtenerTipoBD(int posicionSpinner) {
        if (posicionSpinner >= 0 && posicionSpinner < TIPOS_BD.length) {
            return TIPOS_BD[posicionSpinner];
        }
        return TIPOS_BD[0];
    }

    public static int obtenerPosicionSpinner(String tipoBD) {
        for (int i = 0; i < TIPOS_BD.length; i++) {
            if (TIPOS_BD[i].equals(tipoBD)) {
                return i;
            }
        }
        return 0;
    }

    public static String obtenerTipoBDDesdeTab(int posicionTab) {
        return obtenerTipoBD(posicionTab);
    }
}
