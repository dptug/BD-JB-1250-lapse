-- inject_hen.lua
-- USB Payload Injection Script for PS4 HEN/GoldHEN
-- 
-- This script should be placed on the root of a FAT32-formatted USB stick
-- along with payload.bin (the HEN/GoldHEN binary for your firmware).
--
-- The script will be automatically executed after a successful BD-JB exploit
-- and will attempt to load and inject the payload from the USB stick.

Status.println("=== USB Payload Loader Starting ===")
Status.println("PS4 12.00 BD-JB Payload Injection System")

-- Define the payload file path
local payload_path = "/mnt/usb0/payload.bin"

-- Enhanced file reading function with better error handling
local function read_binary_file(path)
    local file, err = io.open(path, "rb")
    if not file then
        return nil, "Cannot open file: " .. (err or "unknown error")
    end
    
    local content, read_err = file:read("*all")
    file:close()
    
    if not content then
        return nil, "Cannot read file content: " .. (read_err or "unknown error")
    end
    
    if #content == 0 then
        return nil, "File is empty"
    end
    
    return content, nil
end

-- Function to validate payload data
local function validate_payload(payload_data)
    if not payload_data or #payload_data == 0 then
        return false, "Payload data is empty"
    end
    
    -- Basic payload validation - check for ELF header if it's an ELF file
    local elf_magic = "\x7FELF"
    if #payload_data >= 4 and payload_data:sub(1, 4) == elf_magic then
        Status.println("Detected ELF payload format")
        return true, "Valid ELF payload"
    end
    
    -- Check for other common payload patterns or just accept raw binary
    Status.println("Payload format: Raw binary (" .. #payload_data .. " bytes)")
    return true, "Binary payload accepted"
end

-- Main payload injection logic with comprehensive error handling
local function inject_payload()
    Status.println("Attempting to read payload from: " .. payload_path)
    
    -- Try to read the payload file
    local payload_data, error_msg = read_binary_file(payload_path)
    
    if not payload_data then
        Status.println("ERROR: Failed to read payload file - " .. error_msg)
        Status.println("Please ensure payload.bin exists on the USB root")
        Status.println("USB should be FAT32 formatted and properly mounted")
        return false
    end
    
    Status.println("Successfully read payload: " .. #payload_data .. " bytes")
    
    -- Validate payload data
    local is_valid, validation_msg = validate_payload(payload_data)
    if not is_valid then
        Status.println("ERROR: Payload validation failed - " .. validation_msg)
        return false
    end
    
    Status.println("Payload validation passed: " .. validation_msg)
    
    -- Prepare payload for injection
    Status.println("Preparing payload for injection...")
    Status.println("Security manager status: " .. (System.getSecurityManager() and "ACTIVE" or "DISABLED"))
    
    -- Call PayloadBridge to inject the payload
    Status.println("Calling PayloadBridge for injection...")
    
    local success = PayloadBridge(payload_data)
    
    if success then
        Status.println("=== PAYLOAD INJECTION SUCCESSFUL ===")
        Status.println("HEN/GoldHEN should now be active")
        Status.println("System jailbreak completed successfully")
        return true
    else
        Status.println("=== PAYLOAD INJECTION FAILED ===")
        Status.println("Check logs for error details")
        Status.println("Ensure payload is compatible with PS4 12.00")
        return false
    end
end

-- Enhanced error handling wrapper with detailed logging
local function safe_inject()
    local status, result = pcall(inject_payload)
    
    if not status then
        Status.println("CRITICAL ERROR: Exception during payload injection")
        Status.println("Error: " .. tostring(result))
        Status.println("Stack trace available in logs")
        return false
    end
    
    return result
end

-- Execute the payload injection with comprehensive status reporting
Status.println("Starting payload injection process...")
Status.println("BD-JB exploit completed successfully, proceeding with payload...")

local injection_result = safe_inject()

if injection_result then
    Status.println("=== USB Payload Loader completed successfully ===")
    Status.println("PS4 12.00 jailbreak process finished")
    Status.println("You may now run homebrew applications")
else
    Status.println("=== USB Payload Loader failed ===")
    Status.println("Check error messages above for troubleshooting")
    Status.println("Ensure:")
    Status.println("1. USB stick is FAT32 formatted")
    Status.println("2. payload.bin is in USB root directory")
    Status.println("3. Payload is compatible with PS4 12.00")
    Status.println("4. BD-JB exploit completed successfully")
end

Status.println("=== USB Payload Loader Finished ===")