package Controller.InitFact;

/**
 * Created by jotbills on 7/29/17.
 */
public abstract class IFactSubState extends IFactState {

    IFactState master;
    boolean isFinished;
    Object result;

    public IFactSubState(InitializerFactory IF, IFactState master) {
        super(IF);
        this.master = master;
        isFinished = false;
    }
}
