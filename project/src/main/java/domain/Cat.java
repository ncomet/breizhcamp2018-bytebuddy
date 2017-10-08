package domain;

import java.util.HashSet;
import java.util.Set;

public class Cat {

    private String name;
    private Set<String> stomach;

    public Cat() {
        this.stomach = new HashSet<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<String> getStomach() {
        return stomach;
    }

    public void feed(String food) {
        this.stomach.add(food);
    }

}
