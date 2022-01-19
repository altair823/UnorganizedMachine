package unorganized.machine.deliver;

/**
 * An interface that defines a delivery method.
 * All edges deliver state of previous unit to next unit through this interface method.
 */
public class StateDeliver {
    private boolean deliverWay;

    /**
     * Constructor of state deliver class.
     */
    public StateDeliver(){
        this.deliverWay = true;
    }

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
}
