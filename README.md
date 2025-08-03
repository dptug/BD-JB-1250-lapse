# BD-JB-1250
BD-JB for up to PS4 12.50

~~This might be the exploit that was reported by TheFlow and patched at 12.52~~

Nope TheFlow just confirmed this is not his exploit.

lmao

Just take my early Christmas gift :)


No this won't work at PS5.

# Lua Payload Integration

This version includes LuaJ integration for running Lua scripts after the BD-JB exploit is successful. The LuaRunner class provides a bridge between Java and Lua environments, allowing for dynamic script execution post-exploit.

## Features

- **LuaJ Integration**: Uses LuaJ 3.0.1 for Lua script execution
- **Status Class Exposure**: The Java Status class is exposed to Lua scripts for logging and output
- **Post-Exploit Execution**: Lua scripts run automatically after successful sandbox escape
- **Script File Support**: Can execute both inline Lua scripts and external Lua files

## Usage

After a successful exploit, the system automatically executes:
```lua
Status.println('Hello from Lua via lapse!')
```

You can modify `src/org/bdj/InitXlet.java` to run custom Lua scripts:

```java
// Run inline Lua script
LuaRunner.runScript("Status.println('Custom Lua message')");

// Run Lua script from file  
LuaRunner.runScriptFile("path/to/script.lua");
```

## Lua Script Examples

Since the Status class is exposed to Lua, you can use it for logging:

```lua
-- Simple logging
Status.println("Message from Lua script")

-- Error reporting with exception handling
if some_condition then
    Status.println("Operation successful")
else
    Status.println("Operation failed")
end
```

# Notes
Change Status.java ip address for network logging.

Default is 192.168.2.1

For compiling I recommend using john-tornblom's bdj-sdk

https://github.com/john-tornblom/bdj-sdk/

# Credits
[TheFlow](https://github.com/theofficialflow) - for his BD-JB documentation and sources for native code execution

[hammer-83](https://github.com/hammer-83) - for his PS5 Remote JAR Loader, it helped me a lot to learn how BD-J works

[john-tornblom](https://github.com/john-tornblom) - for his BDJ-SDK, I couldn't have compiled PS4 BD-J without his BDJ-SDK



