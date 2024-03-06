package menu.domain;

import java.util.List;
import java.util.Objects;

public class MenusCantEat {
    private final List<String> menuNames;

    public MenusCantEat(List<String> menuNames) {
        this.menuNames = menuNames;
    }

    public List<String> getMenuNames() {
        return menuNames;
    }

    public void add(String menuName) {
        menuNames.add(menuName);
    }

    public boolean contains(String menuName) {
        return menuNames.contains(menuName);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        MenusCantEat that = (MenusCantEat) o;
        return Objects.equals(getMenuNames(), that.getMenuNames());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getMenuNames());
    }
}
