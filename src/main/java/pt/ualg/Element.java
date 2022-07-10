package pt.ualg;

import java.util.List;

/**
 * Class representation of a Element that implements{@link IElement} can be appended to a parent {@link BlockElement}, as a Identation for the String representation.
 */
public class Element implements IElement <BlockElement, Element> {
    private static final String IDENT = "   ";
    private BlockElement parent;

    public String getIDENT() {
        return IDENT;
    }

    public void setParent(BlockElement parent) {
        this.parent = parent;
    }

    @Override
    public BlockElement parentNode() {
        // if( this.parent == null ) {
        //     return (BlockElement) this;
        // }
        return parent;
    }

    @Override
    public String toStringIndented (int indentation) {
        return IDENT.repeat(indentation);
    }

    @Override
    public String toString() {
        return toStringIndented(0) ;
    }

    public static void main(String[] args) {
        BlockElement div = new BlockElement("div");
        Image img = new Image("img");
        Text imageText  = new Text("image of a cat drunk");
        Text texto = new Text("Hello");
        Text text = new Text("OLA MEU OES");
        BlockElement p = new BlockElement("p");
        BlockElement div2 = new BlockElement("span");
        BlockElement p2 = new BlockElement("p");
        BlockElement span = new BlockElement("span");
        Document document = new Document("Document");
        BlockElement html = new BlockElement("html");
        Document document2 = new Document("Document2");
        Document document3 = new Document("Document3");
        Document document4 = new Document("Document4");
        BlockElement html2 = new BlockElement("html");



        // Consumer <BlockElement> consumer = BlockElement::removeChild(this);
        // consumer.accept(div);
        // html.addEventListener("document load", consumer);

        html.appendChild(img);
        img.appendChild(imageText);
        html.appendChild(texto);
        div.appendChild(text);
        // div.appendChild(p);
        p.appendChild(div2);
        div2.appendChild(p2);
        html.appendChild(div);
         html.triggerEvent("document load");
        div.addClass("Class1");
        div.addClass("Class2");
        p.addClass("Class1");
        p.setId("id");
        div.setId("hello");
        document.appendChild(html);
        html.appendChild(span);





        // System.out.println(document.toStringIndented(10));
        // System.out.println(document.getElementById(""));
        // System.out.println(html.firstChild());
        // System.out.println(document.lastChild());
        String emmet = "li.clasa-$.classb.classc.classd#wdsd-$[desable]{Hello}*3>div.clasa.classb+span>p#id";

        String emmet1 = "html.clasa$.classb--$.classc-$.classd-$#wdsd-$[diesable]{Hello}>diversao.a#e>span.clasa.classb{Hola}+div.$*1>p#id^^^^^^^pai>q.$#$*10";
        String emmet2 = "html>div.clasa.classb.classc-$*0";


        // String emmet2 = ">div.clasa.classb+span>p";

        //  BlockElement v = document.stringToElement(emmet);


        //  System.out.println(v.toStringIndented(0));
        // System.out.println(document.toStringIndented(0));

        // for(String parts : emmet1.split("(?=\\+)|(?=>)|(?=\\^+)",2)){
        //     System.out.println(parts);
        // }


        document2.addEmmet(emmet);
        System.out.println(document2);
        // System.out.println(document2.getElementById("id"));

        // System.out.println(document2.parentNode());
        document3.addEmmet(emmet1);
        document4.addEmmet(emmet2);

        // document3.getElementById("id");
        // document3.elementID();
        System.out.println(document3);
        System.out.println(document4);
        // document2.addEmmet("htm>div");
        // System.out.println(document2.getChildren());
        // List<Element> blockElements = document2.getChildren();
        // document3.appendChildren(blockElements);
        // System.out.println(document3.getChildren());
        // System.out.println(p.parentNode());



    //     System.out.println( html2.parentName());
    //     html2.parentName();
    //    System.out.println(document.parentName());
    //     Element a = html2.getChildren().get(0);
    //     System.out.println( ((BlockElement) a).getChildren() );


        // BlockElement aa=(BlockElement) document3.getChildren().get(0);
        // // System.out.println(aa);
        // BlockElement bb=(BlockElement) aa.getChildren().get(1);
        // System.out.println(bb);



        // // System.out.println(document.getElementsByClassName("Class1"));
        // // System.out.println(document.getElementsByClassName("Class2"));
        // System.out.println(span.previousSibling());
        // // System.out.println(div);
        // String a = "li>li^^^^li>li";
        // String emmet3 = "^^^^^^^pai";
        // String[] parts = emmet3.split("(?=\\+)|(?=>)|(?=\\^+)", 2);
        // for (int i = 0; i < parts.length; i++) {
        //     System.out.println(parts[i] + "\n");

        // }

        // System.out.println(parts[0]+ "," + parts[1]);
        // String [] b =  parts[1].split("(?=\\+)|(?=>)|(?=\\^+)", 2);
        // System.out.println(b[0]+ "," + b[1]);
        // String [] c =  b[1].split("(?=\\+)|(?=>)|(?=\\^+)", 2);
        // System.out.println(c[0]+ "," + c[1]);


    }

}
