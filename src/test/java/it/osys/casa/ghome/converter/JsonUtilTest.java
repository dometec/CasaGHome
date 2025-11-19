package it.osys.casa.ghome.converter;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtilTest {

    @Test
    public void mapToObjectNode_basicTypes() {
        ObjectMapper mapper = new ObjectMapper();

        Map<String, Object> data = Map.of(
                "str", "value",
                "num", 42,
                "bool", true,
                "arr", List.of("a", 1, Map.of("inner", "x"))
        );

        ObjectNode node = JsonUtil.mapToObjectNode(data, mapper);

        assertNotNull(node);
        assertEquals("value", node.get("str").asText());
        assertEquals(42, node.get("num").asInt());
        assertTrue(node.get("bool").asBoolean());
        assertTrue(node.get("arr").isArray());
        assertTrue(node.get("arr").get(2).has("inner"));
    }

}
