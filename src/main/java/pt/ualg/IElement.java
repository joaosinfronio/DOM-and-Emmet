package pt.ualg;

/**
 * An element that has a parent node and can be represented with indentation.
 */
public interface IElement<B extends IBlockElement<B, E>, E extends IElement<B,E>> {
    /**
     * Gets the parent node of the element.
     * @return The parent node of the element.
     */
    public B parentNode();

    /**
     * Gets the element's string representation with the given indentation.
     * @param indentation indentation level
     * @return the element's string representation.
     */
    public String toStringIndented(int indentation);
}
