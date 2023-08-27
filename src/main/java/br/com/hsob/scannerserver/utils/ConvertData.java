package br.com.hsob.scannerserver.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

/**
 * @author carlos
 */
@Service
public class ConvertData implements IConvertData {
    private final ObjectMapper mapper = new ObjectMapper();


    @Override
    public <T> T mapperToJson(String json, Class<T> tClass) {
        try {
            return mapper.readValue(json, tClass);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public String normalizeTitle(String title) {
        String[] words = title.split(" ");
        StringBuilder result = new StringBuilder();
        for (String word : words) {
            if (!word.isEmpty()) {
                // Transforma a primeira letra de cada palavra em maiúscula
                result.append(Character.toUpperCase(word.charAt(0)));
                result.append(word.substring(1));
                result.append(" ");
            }
        }
        // Remove o espaço em branco extra no final, se houver
        if (result.length() > 0) result.setLength(result.length() - 1);
        return result.toString();
    }
}
