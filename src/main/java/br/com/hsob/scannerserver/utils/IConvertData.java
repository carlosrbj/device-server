package br.com.hsob.scannerserver.utils;

/**
 * @author carlos
 */
public interface IConvertData {
    <T> T mapperToJson(String json, Class<T> tClass);
}
