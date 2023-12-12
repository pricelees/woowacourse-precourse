package bridge.domain;

import bridge.constants.Constant;
import bridge.domain.DomainInputValidator.LocationValidator;
import bridge.domain.DomainInputValidator.MapSymbolValidator;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class BridgeMap {

    private static final String EMPTY_MAP_SYMBOL = "   ";

    private final List<String> symbolsOfUPSide;
    private final List<String> symbolsOfDOWNSide;

    public BridgeMap(List<String> symbolsOfUPSide, List<String> symbolsOfDOWNSide) {
        symbolsOfUPSide.forEach(MapSymbolValidator::validate);
        symbolsOfDOWNSide.forEach(MapSymbolValidator::validate);

        this.symbolsOfUPSide = symbolsOfUPSide;
        this.symbolsOfDOWNSide = symbolsOfDOWNSide;
    }

    public BridgeMap() {
        this.symbolsOfUPSide = new ArrayList<>();
        this.symbolsOfDOWNSide = new ArrayList<>();
    }

    public void updateMap(String location, String symbol) {
        LocationValidator.validate(location);
        MapSymbolValidator.validate(symbol);

        if (location.equals(Constant.UP)) {
            symbolsOfUPSide.add(symbol);
            symbolsOfDOWNSide.add(EMPTY_MAP_SYMBOL);
        }
        if (location.equals(Constant.DOWN)) {
            symbolsOfUPSide.add(EMPTY_MAP_SYMBOL);
            symbolsOfDOWNSide.add(symbol);
        }
    }

    public List<String> getSymbolsOfUPSide() {
        return symbolsOfUPSide;
    }

    public List<String> getSymbolsOfDOWNSide() {
        return symbolsOfDOWNSide;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        BridgeMap bridgeMap = (BridgeMap) o;
        return Objects.equals(getSymbolsOfUPSide(), bridgeMap.getSymbolsOfUPSide()) && Objects.equals(
                getSymbolsOfDOWNSide(),
                bridgeMap.getSymbolsOfDOWNSide());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getSymbolsOfUPSide(), getSymbolsOfDOWNSide());
    }
}
