package pt.ualg;

public class Text extends Element {
    public String description;

    public Text( String description) {
        this.description = description;
    }

    @Override
    public String toStringIndented(int indentation) {
       return super.toStringIndented(indentation) + description +"\n";
    }

}
