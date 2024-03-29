package pt.ualg;

/**
 * Class representing an Image {@link BlockElement} without closing de block
 */
public class Image extends BlockElement {

    public Image(String name) {
        super(name);
    }

    @Override
    public String toStringIndented (int indentation) {
        if(getPersonalizedAt() == null) {
            setPersonalizedAt("");
        }
        StringBuilder str = new StringBuilder();
        String start =  "<" + getName() + getId() + classToString() + getPersonalizedAt() + "/>";
        String close =  "";
        str.append(getIDENT().repeat(indentation) + start);
        if(getChildren().isEmpty()) {
            return str.toString() + close;
        }else {
            for (Element child : getChildren()) {
                str.append(" " + child.toStringIndented(0));
            }
        }
        return str.toString() + getIDENT().repeat(indentation) + close;
    }
}
