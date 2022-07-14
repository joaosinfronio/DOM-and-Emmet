package pt.ualg;

/**
 * Class representation of a {@link Element} that implements{@link IElement} can be appended to a parent {@link BlockElement}, as a Identation for the String representation.
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
        return parent;
    }

    @Override
    public String toStringIndented (int indentation) {
        return IDENT.repeat(indentation);
    }

    @Override
    public String toString() {
        return this.toStringIndented(0);
    }
}

