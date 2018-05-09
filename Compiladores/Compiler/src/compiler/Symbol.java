package compiler;

import java.util.HashMap;
import java.util.Map;

public class Symbol {
    
    public String type;
    public String id;
    public boolean initialized;
    public boolean used;
    public int scope;
    public boolean params;
    public int params_position;
    public boolean vector;
    public boolean matriz;
    //public boolean reference;
    public boolean function;
    
    public String funcP;
    
    public Symbol() {
        this.type = "";
        this.id = "";
        this.initialized = false;
        this.used = false;
        this.scope = 0;
        this.params = false;
        this.params_position = 0;
        this.vector = false;
        this.matriz = false;
        this.function = false;
        this.funcP = "";
    }
}
