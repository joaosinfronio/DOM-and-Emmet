package pt.ualg;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class ElementTest {

    @Test
    void testGetIDENT() {
        Document document = new Document("document");
        BlockElement block = new BlockElement("html");
        document.appendChild(block);
        assertEquals(block.getIDENT(), "   ");
    }
}
