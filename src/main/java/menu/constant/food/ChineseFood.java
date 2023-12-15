package menu.constant.food;

import java.util.Arrays;
import java.util.List;

public enum ChineseFood {
    KKANPUNGHI("깐풍기"),
    FRIED_NOODLES("볶음면"),
    DONGPO_PORK("동파육"),
    BLACK_BEAN_NOODLES("짜장면"),
    JJAMPONG("짬뽕"),
    MAPO_TOFU("마파두부"),
    SWEET_AND_SOUR_PORK("탕수육"),
    STIR_FRIED_TOMATO_EGG("토마토 달걀볶음"),
    RED_PEPPER_JAPCHAE("고추잡채");

    private final String name;

    ChineseFood(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static List<String> getMenuList() {
        return Arrays.stream(ChineseFood.values())
                .map(ChineseFood::getName)
                .toList();
    }
}
