package org.bdj;

import org.luaj.vm2.Globals;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.jse.JsePlatform;
import org.luaj.vm2.lib.jse.CoerceJavaToLua;

/**
 * LuaRunner class for executing Lua scripts via LuaJ after BD-JB exploit.
 * This class provides integration between Java and Lua environments,
 * exposing the Status class to Lua scripts for logging and output.
 */
public class LuaRunner {
    
    private static Globals globals;
    
    /**
     * Initialize the Lua environment and expose the Status class.
     * This should be called after successful exploit when security restrictions are lifted.
     */
    private static void initLuaEnvironment() {
        if (globals == null) {
            // Create standard Lua environment
            globals = JsePlatform.standardGlobals();
            
            // Expose the Status class to Lua
            LuaValue statusClass = CoerceJavaToLua.coerce(Status.class);
            globals.set("Status", statusClass);
        }
    }
    
    /**
     * Run a Lua script string.
     * 
     * @param script The Lua script to execute as a string
     */
    public static void runScript(String script) {
        try {
            initLuaEnvironment();
            
            Status.println("Executing Lua script: " + script);
            
            // Execute the Lua script
            LuaValue result = globals.load(script).call();
            
            Status.println("Lua script executed successfully");
            
        } catch (Exception e) {
            Status.printStackTrace("Error executing Lua script: ", e);
        }
    }
    
    /**
     * Run a Lua script from a file.
     * 
     * @param filename Path to the Lua script file
     */
    public static void runScriptFile(String filename) {
        try {
            initLuaEnvironment();
            
            Status.println("Executing Lua script file: " + filename);
            
            // Load and execute the Lua script file
            LuaValue result = globals.loadfile(filename).call();
            
            Status.println("Lua script file executed successfully");
            
        } catch (Exception e) {
            Status.printStackTrace("Error executing Lua script file: ", e);
        }
    }
}