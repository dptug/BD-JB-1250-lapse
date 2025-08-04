# BD-JB-1250-lapse - Complete Setup Guide

This is a complete BD-J based jailbreak for PS4 firmware 12.00-12.50, adapted from game-based exploits to work with Blu-ray disc entry point.

## What's Different About This Version

- **No Game Requirements**: Unlike the original savedata exploit which required specific anime games, this version works with any BD-J capable Blu-ray disc
- **Blu-ray Entry Point**: Uses BD-J (Blu-ray Disc Java) sandbox escape instead of game-based exploitation
- **Complete Kernel Exploit**: Includes firmware-specific kernel patches for PS4 12.00-12.50
- **USB Payload Loading**: Automatically loads HEN/GoldHEN from USB after successful exploitation

## Setup Instructions

### Step 1: Prepare Your USB Stick

1. **Format USB**: Use a FAT32-formatted USB stick
2. **Copy Files**: Copy the following files to your USB stick:
   ```
   USB_ROOT/
   ├── payload.bin           # Your HEN/GoldHEN binary
   ├── inject_hen.lua       # Payload injection script (from this repo)
   └── savedata/            # Exploit scripts directory (from this repo)
       ├── bdj_main.lua     # BD-J specific main exploit
       ├── main.lua         # Original game-based exploit
       ├── lua.lua          # Lua primitives and game detection
       ├── kernel_patches_ps4.lua  # Firmware-specific kernel patches
       ├── elfldr.elf       # ELF loader binary
       └── [all other .lua files from savedata/]
   ```

### Step 2: Create the BD-J Disc

You have two options:

#### Option A: Use Pre-built ISO (Recommended)
1. Build the ISO using the included Makefile:
   ```bash
   make clean
   make
   ```
2. This creates `helloworld.iso` that you can burn to a BD-R disc

#### Option B: Use Existing BD-J Disc
1. Replace the JAR file on any existing BD-J disc with `bdj-exploit.jar`
2. The exploit will run when the disc auto-starts

### Step 3: Run the Exploit

1. **Insert USB**: Insert your prepared USB stick into PS4
2. **Insert Disc**: Insert the BD-J exploit disc
3. **Wait for Auto-execution**: The disc should auto-start and begin exploitation
4. **Monitor Progress**: Watch for these status messages:
   - "BD-J init"
   - "Triggering sandbox escape exploit..."
   - "Exploit success - sandbox escape achieved"
   - "=== USB Payload Loader Starting ==="
   - "[BD-J] BD-J kernel exploit starting..."
   - "[BD-J] BD-J exploit completed successfully"

## Technical Details

### Exploit Chain
1. **BD-J InitXlet**: Triggers sandbox escape via XletManager exploitation
2. **inject_hen.lua**: Reads payload from USB and calls PayloadBridge
3. **PayloadBridge**: Saves payload to `/data/payload.bin` and launches BD-J exploit
4. **bdj_main.lua**: BD-J specific kernel exploitation (bypasses game detection)
5. **Kernel Patches**: Firmware-specific kernel patches applied
6. **Payload Execution**: HEN/GoldHEN payload executed with kernel privileges

### Key Differences from Game-Based Exploit
- **No Game Detection**: The BD-J version bypasses the game identification logic in `lua.lua`
- **BD-J Context**: Runs in BD-J process instead of game process
- **Simplified Setup**: No need for specific game titles or save files
- **Universal**: Works on any PS4 with BD-J support (12.00-12.50)

### Files Modified/Added
- `savedata/lua.lua`: Fixed TODO comment for game "C" identification
- `savedata/bdj_main.lua`: New BD-J specific main exploit script
- `src/org/bdj/PayloadBridge.java`: Updated to use BD-J exploit chain
- Various stub classes added for compilation outside BD-J environment

## Supported Firmware Versions

The kernel patches support the following PS4 firmware versions:
- 9.00, 9.03, 9.04
- 9.50, 9.51, 9.60  
- 10.00, 10.50
- 11.00, 11.02
- 11.50
- 12.00

## Troubleshooting

**"Cannot open file" error:**
- Verify USB stick is FAT32 formatted
- Ensure all files are in correct locations
- Check USB stick is properly inserted

**"BD-J exploit failed" error:**
- Verify firmware version is supported (12.00-12.50)
- Ensure payload.bin is valid HEN/GoldHEN for your firmware
- Try different USB ports

**Disc doesn't auto-start:**
- Enable auto-play for Blu-ray discs in PS4 settings
- Manually start disc from PS4 home screen
- Verify disc was burned correctly

## Credits

- **Original BD-JB**: TheFlow for BD-J documentation and native code execution
- **Kernel Exploitation**: Based on PSFree kernel patches by TheFlow, ChendoChap, abc, AlAzif and LightningMods
- **Game-based Exploit**: Original savedata exploit authors
- **BD-J SDK**: john-tornblom for BDJ-SDK
- **Lua Integration**: LuaJ project for Lua-Java bridge