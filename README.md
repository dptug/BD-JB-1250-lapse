# BD-JB-1250
BD-JB for up to PS4 12.50

~~This might be the exploit that was reported by TheFlow and patched at 12.52~~

Nope TheFlow just confirmed this is not his exploit.

lmao

Just take my early Christmas gift :)


No this won't work at PS5.

# USB Payload Loader
(probably not working)

This version includes a robust USB payload injection system that allows users to easily load HEN/GoldHEN payloads from a USB stick after the BD-JB exploit is successful.

## Features

- **USB Payload Loading**: Automatically loads payload.bin from USB stick after exploit
- **LuaJ Integration**: Uses LuaJ 3.0.1 for dynamic Lua script execution
- **PayloadBridge**: Java-to-Lua bridge for kernel payload injection
- **Robust Error Handling**: Clear status messages for all operations
- **User-Friendly**: Simple USB setup with clear feedback

## Setup Instructions

### Step 1: Prepare Your USB Stick

1. **Format USB**: Use a FAT32-formatted USB stick
2. **Copy Files**: Copy the following files to the **root** of your USB stick:
   - `payload.bin` - Your HEN/GoldHEN binary for firmware 12.00/12.02
   - `inject_hen.lua` - The payload injection script (included in this repository)

### Step 2: Run the Exploit

1. **Insert USB**: Insert the prepared USB stick into your PS4
2. **Run BD-J**: Execute the BD-JB exploit as usual (via disc)
3. **Wait for Messages**: The system will display status messages during the process:
   - "BD-J init"
   - "Triggering sandbox escape exploit..."
   - "Exploit success - sandbox escape achieved"
   - "=== USB Payload Loader Starting ==="
   - "Looking for payload.bin on USB stick..."
   - And more detailed progress information

### Step 3: Monitor Results

The loader will automatically:
- Search for `/mnt/usb0/payload.bin`
- Read the payload file
- Inject it using the PayloadBridge
- Display success/failure status

**Success indicators:**
- "=== PAYLOAD INJECTION SUCCESSFUL ==="
- "HEN/GoldHEN should now be active"

**Error indicators:**
- "ERROR: Failed to read payload file"
- "=== PAYLOAD INJECTION FAILED ==="

## Payload Sources

Download appropriate payload.bin files for your firmware:
- **HEN**: From homebrew communities
- **GoldHEN**: From official GoldHEN releases
- Ensure the payload matches your PS4 firmware version (12.00/12.02)

## Troubleshooting

**"Cannot open file" error:**
- Verify USB stick is FAT32 formatted
- Ensure payload.bin is in the root directory (not in folders)
- Check USB stick is properly inserted

**"Payload injection failed" error:**
- Verify payload.bin is a valid HEN/GoldHEN file
- Check payload is for correct firmware version
- Ensure exploit completed successfully first

# Lua Payload Integration

This version includes LuaJ integration for running Lua scripts after the BD-JB exploit is successful. The LuaRunner class provides a bridge between Java and Lua environments, allowing for dynamic script execution post-exploit.

## Features

- **LuaJ Integration**: Uses LuaJ 3.0.1 for Lua script execution
- **Status Class Exposure**: The Java Status class is exposed to Lua scripts for logging and output
- **PayloadBridge Exposure**: The PayloadBridge class enables payload injection from Lua
- **Post-Exploit Execution**: Lua scripts run automatically after successful sandbox escape
- **Script File Support**: Can execute both inline Lua scripts and external Lua files

## Usage

After a successful exploit, the system automatically executes the USB-based script:
```
LuaRunner.runScriptFile("/mnt/usb0/inject_hen.lua")
```

You can modify `src/org/bdj/InitXlet.java` to run custom Lua scripts:

```java
// Run inline Lua script
LuaRunner.runScript("Status.println('Custom Lua message')");

// Run Lua script from file  
LuaRunner.runScriptFile("path/to/script.lua");
```

## Lua Script Examples

Since the Status and PayloadBridge classes are exposed to Lua, you can use them:

```lua
-- Simple logging
Status.println("Message from Lua script")

-- Payload injection
local payload = read_binary_file("/mnt/usb0/payload.bin")
local success = PayloadBridge(payload)

-- Error reporting
if success then
    Status.println("Payload injection successful")
else
    Status.println("Payload injection failed")
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

