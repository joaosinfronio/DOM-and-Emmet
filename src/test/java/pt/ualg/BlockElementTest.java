package pt.ualg;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BlockElementTest {


    Document document;
    Document document1;
    Document document2;
    Document document3;
    Document document4;
    Document document5;
    BlockElement html;
    BlockElement div;
    BlockElement span;
    BlockElement p;
    BlockElement adr;
    String emmet1;


    @BeforeEach
    void setUp() throws IOException  {
        document = new Document("Document");
        document1 = new Document("Document1");
        document2 = new Document("Document2");
        document3 = new Document("Document3");
        document4 = new Document("Document4");
        document5 = new Document("Document5");
        html = new BlockElement("html");
        div = new BlockElement("div");
        span = new BlockElement("span");
        p = new BlockElement("p");
        adr = new BlockElement("adr");
        emmet1 = "html.clasa$.classb--$.classc-$.classd-$#wdsd-$[diesable]{Hello}>diversao.a#e>span.clasa.classb{Hola}+div.$*1>p#id^^^^^^^pai>q.$#$*10";
    }

    @Test
    void testAddClass() {
        assertTrue(html.classList().isEmpty());
        html.addClass("test");
        assertTrue(html.classList().indexOf("test")!=-1);
        assertEquals(" class=\"test\"", html.classToString());
    }

    @Test
    void testAddEmmet() {
        document.addEmmet("html");
        assertEquals(document.getChildren().get(0).toString(), html.toString());
        document1.addEmmet("html>div");
        document2.addEmmet("html>div.class.class-$*1");
        assertEquals("<html>\n"+ document.getIDENT()+"<div class=\"class class-1\"></div>\n</html>\n", document2.toString());
        document3.addEmmet("html>div#id-$*2");
        assertEquals("<html>\n"+ document.getIDENT()+"<div id=\"id-1\"></div>\n"+ document.getIDENT()+"<div id=\"id-2\"></div>\n</html>\n", document3.toString());
        document4.addEmmet(emmet1);
        String a =
        "<html id=\"wdsd-\" class=\"clasa classb-- classc- classd-\" diesable>\n"
        + document.getIDENT()+ "Hello\n"
        + document.getIDENT()+ "<diversao id=\"e\" class=\"a\">\n"
        + document.getIDENT()+document.getIDENT()+"<span class=\"clasa classb\">\n"
        + document.getIDENT()+document.getIDENT()+document.getIDENT() + "Hola\n"
        + document.getIDENT()+document.getIDENT()+  "</span>\n"
        + document.getIDENT()+document.getIDENT()+"<div class=\"1\">\n"
        + document.getIDENT()+document.getIDENT()+document.getIDENT() +"<p id=\"id\"></p>\n"
        + document.getIDENT()+document.getIDENT()+"</div>\n"
        + document.getIDENT()+"</diversao>\n"+
        "</html>\n"+
        "<pai>\n"
        + document.getIDENT()+"<q id=\"1\" class=\"1\"></q>\n"
        + document.getIDENT()+"<q id=\"2\" class=\"2\"></q>\n"
        + document.getIDENT()+"<q id=\"3\" class=\"3\"></q>\n"
        + document.getIDENT()+"<q id=\"4\" class=\"4\"></q>\n"
        + document.getIDENT()+"<q id=\"5\" class=\"5\"></q>\n"
        + document.getIDENT()+"<q id=\"6\" class=\"6\"></q>\n"
        + document.getIDENT()+"<q id=\"7\" class=\"7\"></q>\n"
        + document.getIDENT()+"<q id=\"8\" class=\"8\"></q>\n"
        + document.getIDENT()+"<q id=\"9\" class=\"9\"></q>\n"
        + document.getIDENT()+"<q id=\"10\" class=\"10\"></q>\n"+
        "</pai>\n";
        /*
            <html id = "wdsd-" class = "clasa classb-- classc- classd-" diesable>
            Hello
            <diversao id = "e" class = "a">
                <span class = "clasa classb">
                    Hola
                </span>
                <div class = "1">
                    <p id = "id"></p>
                </div>
            </diversao>
            </html>
            <pai>
                <q id = "1" class = "1"></q>
                <q id = "2" class = "2"></q>
                <q id = "3" class = "3"></q>
                <q id = "4" class = "4"></q>
                <q id = "5" class = "5"></q>
                <q id = "6" class = "6"></q>
                <q id = "7" class = "7"></q>
                <q id = "8" class = "8"></q>
                <q id = "9" class = "9"></q>
                <q id = "10" class = "10"></q>
            </pai>
         */
        assertEquals(a,document4.toString());
    }

    @Test
    void testAddEventListener() {
        html.appendChild(div);
        assertTrue(html.getChildren().indexOf(div)>=0);
        Consumer<BlockElement> a = (n) -> {n.removeChild(n.firstChild());;};
        html.addEventListener("removeFirstChild",a );
        assertTrue(html.getEvents().get("removeFirstChild").indexOf(a) > -1);
    }

    @Test
    void testAppendChild() {
        assertFalse(html.parentNode()==document);
        document.appendChild(html);
        assertTrue(html.parentNode()==document);
        assertEquals(document.getChildren().get(0), html);
    }

    @Test
    void testAppendChildren() {
        document.appendChild(div);
        document.appendChild(html);
        assertTrue(document.getChildren().get(0)==div && document.getChildren().get(1)==html);
        document1.appendChildren(document.getChildren());
        assertEquals(document1.getChildren(), document.getChildren());
    }

    @Test
    void testClassList() {
        assertTrue(html.classList().isEmpty());
        html.addClass("test");
        List<String> list = new ArrayList<String>();
        list.add("test");
        assertEquals(html.classList(),list );
        assertTrue(html.classList().size() == list.size());
        assertTrue(list.toString().equals(html.classList().toString()));
    }

    @Test
    void testFirstChild() {
        document.appendChild(html);
        document.appendChild(div);
        assertEquals(document.firstChild(), html);
    }

    @Test
    void testGetElementById() {
        document.appendChild(html);
        document.appendChild(div);
        document.appendChild(span);
        span.setId("id");
        assertEquals(document.getElementById("id"), span);
    }

    @Test
    void testGetElementsByClassName() {
        document.appendChild(html);
        document.appendChild(div);
        document.appendChild(span);
        document.appendChild(p);
        p.addClass("class");
        span.addClass("class");
        assertTrue(document.getElementsByClassName("class").contains(p));
        assertTrue(document.getElementsByClassName("class").contains(span));
        assertFalse(document.getElementsByClassName("class").contains(div));
        assertEquals(document.getElementsByClassName("class").size(), 2);
    }

    @Test
    void testLastChild() {
        document.appendChild(html);
        document.appendChild(div);
        document.appendChild(span);
        assertEquals(document.lastChild(), span);
    }

    @Test
    void testNextSibling() {
        document.appendChild(html);
        document.appendChild(div);
        document.appendChild(span);
        assertEquals(div, html.nextSibling());
        assertEquals(null, span.nextSibling());
    }

    @Test
    void testPreviousSibling() {
        document.appendChild(html);
        document.appendChild(div);
        document.appendChild(span);
        assertEquals(null, html.previousSibling());
        assertEquals(div, span.previousSibling());
    }

    @Test
    void testRemoveChild() {
        document.appendChild(html);
        assertTrue(html.parentNode()==document);
        document.removeChild(html);
        assertFalse(html.parentNode()==document);
        assertTrue(html.parentNode()==null);
        assertTrue(document.getChildren().isEmpty());
    }

    @Test
    void testReplaceChild() {
        document.appendChild(html);
        assertTrue(html.parentNode()==document);
        assertTrue(div.parentNode()==null);
        assertTrue(document.getChildren().indexOf(html)!=-1);
        assertTrue(document.getChildren().indexOf(div)==-1);
        document.replaceChild(html, div);
        assertTrue(html.parentNode()==null);
        assertTrue(div.parentNode()==document);
        assertTrue(document.getChildren().indexOf(html)==-1);
        assertTrue(document.getChildren().indexOf(div)!=-1);
    }

    @Test
    void testToStringIndented() {
        document.addEmmet("html>div>span");
        String a =
        "<html>\n"
        + document.getIDENT()+"<div>\n"
        + document.getIDENT()+document.getIDENT()+"<span>"+"</span>\n"
        + document.getIDENT()+"</div>\n"
        + "</html>\n";
        assertEquals(a, document.toStringIndented(0));
    }

    @Test
    void testTriggerEvent() {
        html.appendChild(div);
        assertTrue(html.getChildren().indexOf(div)>=0);
        Consumer<BlockElement> a = (n) -> {n.removeChild(n.firstChild());;};
        html.addEventListener("removeFirstChild",a );
        html.triggerEvent("removeFirstChild");
        assertTrue(html.getChildren().indexOf(div)<0);
    }

    @Test
    void testAbreviations() throws IOException {
        document.addEmmet("adr+art");
        document1.appendChild(adr);
        assertTrue(document.getChildren().size()>=0);
        assertEquals(((BlockElement) document.firstChild()).getName(),"address");
        assertEquals(((BlockElement) document.lastChild()).getName(),"article");
        assertEquals(((BlockElement) document1.lastChild()).getName(),"address");
    }
}
