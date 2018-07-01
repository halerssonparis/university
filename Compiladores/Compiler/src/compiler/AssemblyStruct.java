package compiler;

public class AssemblyStruct {
    
    public String command;
    public String id;
    public String type;
    
    public int posVector;
    
    
    public AssemblyStruct(String command, String id) {
        this.command = command;
        this.id = id;
    }
    
    public AssemblyStruct() {
        this.command = "";
        this.id = "";
    }
}
