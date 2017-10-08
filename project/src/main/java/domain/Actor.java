package domain;

public interface Actor {

    default String greet() {
        return String.format("Hi, I'm %s %s, and I'll play %s", firstName(), lastName(), characterName());
    }

    String firstName();

    String lastName();

    String characterName();

}
