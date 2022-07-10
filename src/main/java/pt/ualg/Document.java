package pt.ualg;

public class Document extends BlockElement {

    public Document(String name) {
        super(name);
    }

    @Override
    public String toStringIndented (int indentation) {
        StringBuilder str = new StringBuilder();
        if(getChildren().isEmpty()) {
            return "";
        }else {
            indentation = indentation -1;
            for (Element child : getChildren()) {
                str.append(child.toStringIndented(indentation + 1));
            }
        }
        return str.toString();
    }
}
