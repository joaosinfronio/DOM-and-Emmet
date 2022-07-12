package pt.ualg;

import java.io.BufferedReader;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.io.FileReader;
import java.io.IOException;
import java.util.function.Consumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Represents a BlockElement that extends the {@link Element} and implements {@link IBlockElement},
 * can have an id, a name, a Collection of classes, a personalized attribute, a Collection of events for this {@link BlockElement}, a Collection of abbreviations and a Collection of children.
 */
public class BlockElement extends Element implements IBlockElement<BlockElement,Element> {
    private String id;
    private String name;
    private List<Element> children = new ArrayList<Element>();
    private List<String> classList = new ArrayList<String>();
    private String personalizedAt;
    private Map<String, List<Consumer<BlockElement>>> events = new HashMap<String, List<Consumer<BlockElement>>>();
    private Map<String,String> abbreviations = new HashMap<String, String>();

    /**
     * Creates a new block element with the given name.
     */
    public BlockElement(String name) {
        if(abbreviations.containsKey(name)) {
            this.name = abbreviations.get(name);
        }else {
         this.name = name;
        }
    }

    public String getId() {
        if(id == null) {
            return "";
        }
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    private String idToString() {
        if (getId().equals("")) {
            return getId();
        }
        return " id = \"" + id + "\"";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<String, String> getAbbreviations() {
        return abbreviations;
    }

    @Override
    public List<Element> getChildren() {
        return children;
    }

    public Map<String, List<Consumer<BlockElement>>> getEvents() {
        return events;
    }

    /**
     * Get´s the personalized attribute string representations if exists.
     * @return The attribute string representation
     */
    public String getPersonalizedAt() {
        if (personalizedAt == null || personalizedAt== "") {
            personalizedAt = "";
            return personalizedAt;
        }
        return " "+ personalizedAt;
    }

    public void setPersonalizedAt(String personalizedAT) {
        this.personalizedAt = personalizedAT;
    }

    @Override
    public BlockElement getElementById(String id) {
        if(this.getId().equals(id)) {
            return this;
        }
        if(!(children.isEmpty())) {
            for(Element element : children) {
                if(element instanceof BlockElement) {
                    BlockElement childResult = ((BlockElement) element).getElementById(id);
                    if (childResult != null) {
                        return childResult;
                    }
                }
            }
        }
        return null;
    }

    @Override
    public Collection<BlockElement> getElementsByClassName(String name) {
        List<BlockElement> elements = new ArrayList<BlockElement>();
        if(!(classList.isEmpty())) {
            if(this.classList().contains(name)) {
                elements.add(this);
            }
        }
        if(!(children.isEmpty())) {
            for(Element child: children) {
                if(child instanceof BlockElement) {
                    elements.addAll(((BlockElement) child).getElementsByClassName(name));
                }
            }
        }
        return elements;
    }

    @Override
    public void appendChild(Element e) {
        e.setParent(this);
        children.add(e);
        if(parentNode() != null && parentNode().getAbbreviations()!=null) {
            this.abbreviations = parentNode().getAbbreviations();
        }
        if (e instanceof BlockElement && this.abbreviations.containsKey(((BlockElement) e).getName())) {
            ((BlockElement) e).setName(abbreviations.get(((BlockElement) e).getName()));
        }
    }

    @Override
    public void appendChildren(Collection<Element> c) {
        for (Element child: c) {
            ((BlockElement) this).appendChild(child);
        }
    }

    @Override
    public Element firstChild() {
        if(!(children.isEmpty())) {
            return children.get(0);
        }
        return null;
    }

    @Override
    public Element lastChild() {
        if(!(children.isEmpty())) {
            return children.get(children.size() - 1);
        }
        return null;
    }

    @Override
    public void removeChild(Element child) {
        children.remove(child);
        child.setParent(null);
    }

    @Override
    public void replaceChild(Element child, Element replacement) {
        int indexChild = children.indexOf(child);
        if(indexChild != -1) {
            children.set(indexChild, replacement);
            replacement.setParent(this);
            child.setParent(null);
        }
    }

    @Override
    public Element previousSibling() {
        int indexOfThis = parentNode().children.indexOf(this);
        if(indexOfThis == -1 || indexOfThis == 0){
            return null;
        }
        return parentNode().children.get(indexOfThis - 1);
    }

    @Override
    public Element nextSibling() {
        int indexOfThis = parentNode().children.indexOf(this);
        if(indexOfThis == -1 || parentNode().getChildren().size()-1 == indexOfThis){
            return null;
        }
        return parentNode().children.get(indexOfThis + 1);
    }

    @Override
    public List<String> classList() {
        return classList;
    }

    /**
     * Replaces the class in a {@link BlockElement} at a given index
     * @param old Class to replace for
     * @param newName Class to replace with
     */
    private void replaceClass( String old,String newName) {
        if(classList.indexOf(old) != -1) {
            this.classList.set(classList.indexOf(old), newName);
        }
    }

    /**
     * Makes the class String format for the BlockElement representation.
     * @return String class representation
     */
    public String classToString() {
        StringBuilder str = new StringBuilder();
        str.append(" class = \"");
        if(classList.isEmpty()) {
            return "";
        }
        for(String className : classList) {
            if(classList.indexOf(className) != classList.size()-1) {
            str.append(className +" ");
            }else{
              str.append(className +"\"");
            }
        }
        return str.toString();
    }

    /**
     * Adds a class to the class list wiht the given name
     * @param className
     */
    public void addClass(String className) {
        classList.add(className);
    }

    @Override
    public void addEventListener(String name, Consumer<BlockElement> listener) {
        List<Consumer<BlockElement>> listeners = new ArrayList<Consumer<BlockElement>>();
        if(this.events.get(name) == null) {
            listeners.add(listener);
            this.events.put(name, listeners);
        }else {
            this.events.get(name).add(listener);
        }
    }

    @Override
    public void triggerEvent(String name) {
        if(events.containsKey(name)) {
            for(Consumer<BlockElement> listener : this.events.get(name)) {
                listener.accept(this);
            }
        }
    }

    /**
     * Reads a file containing a list of abbreviations
     * @param file
     * @throws IOException
     */
    public void readAbbFile(String file) throws IOException {
        File abb = new File(file);
        try (BufferedReader br = new BufferedReader(new FileReader(abb))) {
            String st;
            while((st = br.readLine()) != null) {
                String [] parts = st.split(",");
                abbreviations.put(parts[0], parts[1]);
            }
            if(abbreviations.containsKey(this.name)) {
                this.name=abbreviations.get(this.name);
            }
        }
    }

    @Override
    public void addEmmet(String emmet) {
        String[] parts = emmet.split("(?=\\+)|(?=>)|(?=\\^+)",2);
        if(parts[0].charAt(0)=='^') {
            parts[0] = parts[0].substring(1);
        }
        List<BlockElement> blockElements = stringToElement(parts[0]);
            for(BlockElement blockElement : blockElements) {
                this.appendChild(blockElement);
                if(parts.length > 1 ) {
                    if(parts[1].charAt(0) == '>') {
                        blockElement.addEmmet(parts[1].substring(1));
                    }
                    if(parts[1].charAt(0) == '+') {
                        this.addEmmet(parts[1].substring(1));
                    }
                    if(parts[1].charAt(0) == '^') {
                    Element parenttemp = this;
                    int timesIdented = 0;
                            while(parts[1].charAt(timesIdented) == '^') {
                                timesIdented++;
                                if(parenttemp.parentNode() != null) {
                                    parenttemp = parenttemp.parentNode();
                                }
                        }
                         System.out.println(parts[1].substring(timesIdented));

                    ((BlockElement) parenttemp).addEmmet(parts[1].substring(timesIdented));
                    }
                }
            }
    }

    /**
     * Creates a List of {@link BlockElement} passing a string throw a regex matcher, dividing the functionality of each character.
     * @param emmet - String
     * @return a list of {@link BlockElement}
     */
    private List<BlockElement> stringToElement(String emmet) {
        BlockElement block;
        List<BlockElement> result = new ArrayList<BlockElement>();
        Pattern emmPattern = Pattern.compile("[a-z]+(\\.([a-z]|-|\\$)+)*(#([a-z]|-|\\$)+)?(\\[[^\\]]+\\])?(\\{[^\\}]+\\})?(\\*\\d*)?");
        Matcher eMatcher = emmPattern.matcher(emmet);
        if(eMatcher.find()) {
            StringBuilder name = new StringBuilder();
            for ( char c : emmet.toCharArray()) {
                if( c !='#' && c !='*' && c !='[' && c !='{' && c !='>' && c !='+' && c !='.' && c !='^') {
                    name.append(c);
                }else{
                    break;
                }
            }
            block = new BlockElement(name.toString());

            if(eMatcher.group(1)!= null) {
                String [] parts = emmet.split("\\.");
                String lastClass = eMatcher.group(1);
                for(int i = 1; i < parts.length-1; i++) {
                    if((eMatcher.group(7) == null)) {
                        parts[i]= parts[i].replace("$","");
                        lastClass = lastClass.replace("$","");
                    }
                    block.addClass(parts[i]);
                }
                block.addClass(lastClass.replace(".", ""));
            }
            if(eMatcher.group(3)!= null) {
                String id = eMatcher.group(3).replace("#", "");
                if((eMatcher.group(7) == null)) {
                    id = id.replace("$","");
                }
                block.setId(id);
            }
            if(eMatcher.group(5)!= null) {
                String personalized = eMatcher.group(5).substring(1, eMatcher.group(5).length() - 1);
                block.setPersonalizedAt(personalized);
            }
            if(eMatcher.group(6)!= null) {
                String childText = eMatcher.group(6).substring(1, eMatcher.group(6).length() - 1);
                Text childElement = new Text(childText);
                block.appendChild(childElement);
            }
            if(eMatcher.group(7)!= null) {
                int repeatitions = Integer.parseInt(eMatcher.group(7).substring(1));
                block.setId(block.getId().replace("$", String.valueOf(repeatitions)));
                for(String clas : block.classList()) {
                    block.replaceClass(clas, clas.replace("$", String.valueOf(repeatitions)));
                }
                block.setPersonalizedAt(block.getPersonalizedAt().replace("$", String.valueOf(repeatitions)));
                if(repeatitions>1) {
                    String newEmmet =emmet.replace(eMatcher.group(7).substring(1), String.valueOf(repeatitions-1));
                    List<BlockElement> blockEmmet = stringToElement(newEmmet);
                    result.addAll(blockEmmet);
                }
            }
            result.add(block);
            return result;
        }
        return null;
    }

    @Override
    public String toStringIndented (int indentation) {
        StringBuilder str = new StringBuilder();
        String start =  "<" + getName() + idToString() + classToString() + getPersonalizedAt() + ">";
        String close =  "</" + getName() + ">";
        if(children.isEmpty()) {
            str.append(getIDENT().repeat(indentation) + start);
            return str.toString() + close + "\n";
        }else {
            str.append(getIDENT().repeat(indentation) + start + "\n");
            for (Element child : children) {
                str.append(child.toStringIndented(indentation + 1));
            }
        }
        return str.toString() + getIDENT().repeat(indentation)+close +"\n";
    }

    @Override
    public String toString() {
        return toStringIndented(0) ;
    }
}



