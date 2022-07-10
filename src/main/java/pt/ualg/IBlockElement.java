package pt.ualg;

import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;

/**
 * Interface for a block element. It tries to emulate some of the features of the DOM API.
 *
 * Block elements' equality is strict equality. That is, two block elements are equal if and only if they have the same Object.
 */
public interface IBlockElement<B extends IBlockElement<B, E>, E extends IElement<B, E>> {

    /**
     * Gets a with the given ID nested in this element.
     * @param id The ID of the node.
     * @return The node with the given ID or null, if it isn't found.
    */
    public B getElementById(String id);

    /**
     * Gets all the elements with the given class nested in this element.
     * @param name The class name.
     * @return A collection of elements with the given class.
     */
    public Collection<B> getElementsByClassName(String name);

    /**
     * Appends a child element to the element.
     * @param e The child element.
     */
    public void appendChild(E e);

    /**
     * Appends multiple child elements to the element.
     * @param c The child elements.
     */
    public void appendChildren(Collection<E> c);

    /**
     * Gets the children of the element.
     * @return The children of the element.
     */
    public List<E> getChildren();

    /**
     * Gets the first child of the element.
     * @return The first child of the element or null if there is none.
     */
    public E firstChild();

    /**
     * Gets the last child of the element.
     * @return The last child of the element or null if there is none.
     */
    public E lastChild();

    /**
     * Removes the given child element from the element.
     * @param child The child element to be removed.
     */
    public void removeChild(E child);

    /**
     * Replaces the given child element with the given element.
     * @param child The child element to be replaced.
     * @param replacement The element to replace the child element with.
     */
    public void replaceChild(E child, E replacement);

    /**
     * Gets the previous sibling of the element.
     * @return The previous sibling of the element or null if there is none.
     */
    public E previousSibling();

    /**
     * Gets the next sibling of the element.
     * @return The next sibling of the element or null if there is none.
     */
    public E nextSibling();

    /**
     * Gets the element's classes.
     * @return The element's classes.
     */
    public List<String> classList();

    /**
     * Adds an event listener to the element.
     * @param name The name of the event.
     * @param listener The listener to be added.
     */
    public void addEventListener(String name, Consumer<B> listener);

    /**
     * Triggers the given event.
     * @param name The name of the event.
     */
    public void triggerEvent(String name);

    /**
     * Creates new element(s) with the given Emmet string.
     * @param emmet The Emmet string.
     */
    public void addEmmet(String emmet);
}
