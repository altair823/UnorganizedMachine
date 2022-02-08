package unorganized.machine.deliver;

import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * An interface that defines a delivery method.
 * All edges deliver state of previous unit to next unit through this interface method.
 */
@Getter
@NoArgsConstructor
public class StateDeliver {
    private boolean deliverWay = true;

    /**
     * Delivering method for edge class.
     * All edges can pass state to the head unit using the implementation of this function.
     * @param state state of tail unit
     * @return status to be delivered to the head unit
     */
    public boolean deliver(boolean state){
        if (this.deliverWay){
            return state;
        }else {
            return !state;
        }
    }

    /**
     * Method that reverses the way delivering state.
     */
    public void reverse(){
        this.deliverWay = !this.deliverWay;
    }

    /**
     * Copy method that copy state deliver deeply.
     * @param originalStateDeliver original state deliver
     * @return new state deliver
     */
    public static StateDeliver copy(StateDeliver originalStateDeliver){
        StateDeliver stateDeliver = new StateDeliver();
        stateDeliver.deliverWay = originalStateDeliver.deliverWay;
        return stateDeliver;
    }
}
