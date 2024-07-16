package com.proyecto.literalura.mapper;

public interface iConvierteDatos {

    <T> T obtenerDatos(String json, Class<T> clase);

}