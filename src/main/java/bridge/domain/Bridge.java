package bridge.domain;

import bridge.domain.Validator.BridgeSizeValidator;
import bridge.domain.Validator.LocationValidator;
import bridge.domain.Validator.MovingStepValidator;
import bridge.util.BridgeMaker;
import java.util.List;

public class Bridge {
    private final List<String> locationsCanMove;

    public Bridge(List<String> locationsCanMove) {
        BridgeSizeValidator.validate(locationsCanMove);
        locationsCanMove.forEach(LocationValidator::validate);

        this.locationsCanMove = locationsCanMove;
    }

    public static Bridge from(BridgeMaker bridgeMaker, int size) {
        BridgeSizeValidator.validate(size);
        return new Bridge(bridgeMaker.makeBridge(size));
    }

    public boolean canMove(String location, int step) {
        LocationValidator.validate(location);
        MovingStepValidator.validate(step, locationsCanMove.size());

        String answer = locationsCanMove.get(step);
        return answer.equals(location);
    }

    public int getBridgeSize() {
        return locationsCanMove.size();
    }
}
