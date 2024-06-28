package com.aluracursos.literalura_application.service;

public interface IConvertirDatos {
    <T> T obtenerDatos(String json, Class<T> clase);
}
