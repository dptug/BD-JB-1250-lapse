-- inject_hen.lua
-- USB Payload Injection Script for PS4 HEN/GoldHEN
-- 
-- This script should be placed on the root of a FAT32-formatted USB stick
-- along with payload.bin (the HEN/GoldHEN binary for your firmware).
--
-- The script will be automatically executed after a successful BD-JB exploit
-- and will attempt to load and inject the payload from the USB stick.

Status.println("=== USB Payload Loader Starting ===")
Status.println("Looking for payload.bin on USB stick...")

-- Define the payload file path
local payload_path = "/mnt/disc0/payload.bin"

-- Function to read a file in binary mode and return content as string
local function read_binary_file(path)
    local file = io.open(path, "rb")
    if not file then
        return nil, "Cannot open file"
    end
    
    local content = file:read("*all")
    file:close()
    
    if not content then
        return nil, "Cannot read file content"
    end
    
    return content, nil
end

-- Function to convert string to byte array for payload injection
local function string_to_bytes(str)
    local bytes = {}
    for i = 1, #str do
        bytes[i] = string.byte(str, i)
    end
    return bytes
end

-- Main payload injection logic
local function inject_payload()
    Status.println("Attempting to read payload from: " .. payload_path)
    
    -- Try to read the payload file
    local payload_data, error_msg = read_binary_file(payload_path)
    
    if not payload_data then
        Status.println("ERROR: Failed to read payload file - " .. error_msg)
        Status.println("Please ensure payload.bin exists on the USB root")
        return false
    end
    
    Status.println("Successfully read payload: " .. #payload_data .. " bytes")
    
    -- Convert payload data to format expected by PayloadBridge
    Status.println("Preparing payload for injection...")
    
    -- Call PayloadBridge to inject the payload
    Status.println("Calling PayloadBridge for injection...")
    
    local success = PayloadBridge(payload_data)
    
    if success then
        Status.println("=== PAYLOAD INJECTION SUCCESSFUL ===")
        Status.println("HEN/GoldHEN should now be active")
        return true
    else
        Status.println("=== PAYLOAD INJECTION FAILED ===")
        Status.println("Check logs for error details")
        return false
    end
end

-- Error handling wrapper
local function safe_inject()
    local status, result = pcall(inject_payload)
    
    if not status then
        Status.println("CRITICAL ERROR: Exception during payload injection")
        Status.println("Error: " .. tostring(result))
        return false
    end
    
    return result
end

-- Execute the payload injection
Status.println("Starting payload injection process...")

local injection_result = safe_inject()

if injection_result then
    Status.println("USB Payload Loader completed successfully")
else
    Status.println("USB Payload Loader failed - check error messages above")
end

Status.println("=== USB Payload Loader Finished ===")
