package bridge.domain;

import bridge.constants.Constant;
import java.util.List;

public class Validator {
    private static final String ERROR_PREFIX = "[ERROR] ";

    private Validator() {

    }

    public static class BridgeSizeValidator {
        private static final String ERROR_MESSAGE = "유효하지 않은 다리 길이";
        private static final int MIN_BRIDGE_SIZE = 3;
        private static final int MAX_BRIDGE_SIZE = 20;

        private BridgeSizeValidator() {
        }

        public static void validate(int bridgeSize) {
            if (bridgeSize > MAX_BRIDGE_SIZE || bridgeSize < MIN_BRIDGE_SIZE) {
                throw new IllegalArgumentException(ERROR_PREFIX + ERROR_MESSAGE);
            }
        }
    }

    public static class LocationValidator {
        private static final String ERROR_MESSAGE = "위치는 위(U), 아래(D)만 가능합니다.";
        private static final String INVALID_LOCATION_REGEX = "[^UD]";

        private LocationValidator() {
        }

        public static void validate(String location) {
            if (location.matches(INVALID_LOCATION_REGEX)) {
                throw new IllegalArgumentException(ERROR_PREFIX + ERROR_MESSAGE);
            }
        }
    }

    public static class MapSymbolValidator {
        private static final List<String> AVAILABLE_SYMBOLS = List.of("   ", " O ", " X ");
        private static final String ERROR_MESSAGE = "유효하지 않은 기호입니다.";

        private MapSymbolValidator() {
        }

        public static void validate(String symbol) {
            if (isNotAvailableSymbol(symbol)) {
                throw new IllegalArgumentException(ERROR_PREFIX + ERROR_MESSAGE);
            }
        }

        private static boolean isNotAvailableSymbol(String symbol) {
            return !AVAILABLE_SYMBOLS.contains(symbol);
        }
    }

    public static class MovingStepValidator {
        private static final String ERROR_MESSAGE = "유효하지 않은 이동 단계입니다.";

        private MovingStepValidator() {
        }

        public static void validate(int step, int bridgeLength) {
            if (step < Constant.ZERO || step > bridgeLength) {
                throw new IllegalArgumentException(ERROR_PREFIX + ERROR_MESSAGE);
            }
        }
    }
}
