package Controller.InitFact;



/**
 * Created by jotbills on 7/26/17.
 */
public abstract class IFactState  implements  IFact{

    InitializerFactory IF;

    public IFactState(InitializerFactory IF) {
        this.IF = IF;
    }
}
