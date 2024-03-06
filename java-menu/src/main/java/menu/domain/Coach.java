package menu.domain;

import java.util.List;
import java.util.Objects;

public class Coach {
    private final CoachName name;
    private final MenusCantEat menusCantEat;

    private Coach(CoachName name, MenusCantEat menusCantEat) {
        this.name = name;
        this.menusCantEat = menusCantEat;
    }

    public static Coach valueOf(String name, List<String> menusCantEat) {
        return new Coach(new CoachName(name), new MenusCantEat(menusCantEat));
    }

    public String getName() {
        return name.getName();
    }

    public boolean cantEat(String menuName) {
        return menusCantEat.contains(menuName);
    }

    public void addMenuCantEat(String menuName) {
        menusCantEat.add(menuName);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Coach coach = (Coach) o;
        return Objects.equals(getName(), coach.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName());
    }
}
