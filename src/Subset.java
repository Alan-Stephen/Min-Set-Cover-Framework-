import java.util.ArrayList;

public class Subset {

    public int getId() {
        return id;
    }

    public int getSize() {
        return elements.size();
    }

    public int getElement(int index) {
        return elements.get(index);
    }

    private final int id;
    private ArrayList<Integer> elements;
    Subset(int id,ArrayList<Integer> elements){
        this.id = id;
        this.elements = elements;
    }

    @Override
    public String toString() {
        return "Subset{" +
                "id=" + id +
                ", elements=" + elements +
                '}';
    }
}
