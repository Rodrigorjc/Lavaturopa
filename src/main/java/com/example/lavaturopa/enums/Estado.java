package com.example.lavaturopa.enums;

public enum Estado {
    PENDIENTE, PROCESANDO, ENTREGADO;

    public static boolean isValid(String value) {
        for (Estado estado : Estado.values()) {
            if (estado.name().equals(value)) {
                return true;
            }
        }
        return false;
    }
}
