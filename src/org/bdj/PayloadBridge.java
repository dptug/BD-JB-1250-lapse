package org.bdj;

import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.OneArgFunction;
import java.io.FileOutputStream;
import java.io.File;

/**
 * PayloadBridge class provides the definitive bridge between the BD-J sandbox escape
 * and the Lua-based kernel exploit chain. It saves the user's payload (HEN/GoldHEN)
 * to a standard location and then executes the main kernel exploit script.
 */
public class PayloadBridge extends OneArgFunction {

    /**
     * Main entry point for payload injection. This method performs two critical functions:
     * 1. It takes the payload (e.g., hen.bin) read from the USB drive and writes it to the internal HDD at `/data/payload.bin`. This makes it available for the post-jailbreak loader.
     * 2. It triggers the main Lua script (`main.lua`), which in turn executes the `lapse.lua` kernel exploit to jailbreak the system.
     *
     * @param payload The payload bytes (e.g., HEN/GoldHEN) to be written to the internal HDD.
     * @return true if the process completes without throwing an exception, false otherwise.
     */
    public static boolean injectPayload(byte[] payload) {
        try {
            if (System.getSecurityManager() != null) {
                Status.println("PayloadBridge ERROR: Sandbox is still active. Cannot proceed.");
                return false;
            }

            Status.println("PayloadBridge: Security Manager disabled. Preparing for kernel exploitation.");

            // Step 1: Write the provided payload to the internal HDD.
            // The bin_loader.lua script expects to find the payload here after the jailbreak.
            if (payload != null && payload.length > 0) {
                Status.println("PayloadBridge: Writing " + payload.length + " bytes to /data/payload.bin...");
                try {
                    // Ensure the /data/ directory exists.
                    File dataDir = new File("/data/");
                    if (!dataDir.exists()) {
                        dataDir.mkdirs();
                    }
                    FileOutputStream fos = new FileOutputStream("/data/payload.bin");
                    fos.write(payload);
                    fos.close();
                    Status.println("PayloadBridge: Successfully wrote payload to internal storage.");
                } catch (Exception e) {
                    Status.printStackTrace("PayloadBridge WARNING: Could not write payload to /data/payload.bin. The bin_loader may fail.", e);
                    // We continue anyway, as the kernel exploit itself might be the primary goal.
                }
            } else {
                Status.println("PayloadBridge: No payload provided to write. Assuming it already exists at /data/payload.bin.");
            }

            // Step 2: Execute the BD-J specific kernel exploit.
            // This is adapted for BD-J entrypoint instead of game-based exploitation
            Status.println("PayloadBridge: Handing off to BD-J Lua kernel exploit chain...");
            LuaRunner.runScriptFile("/mnt/usb0/savedata/bdj_main.lua"); // BD-J specific version

            Status.println("PayloadBridge: Lua kernel exploit execution finished. System should now be jailbroken.");

            return true;

        } catch (Exception e) {
            Status.printStackTrace("PayloadBridge FATAL: A critical error occurred during the exploit process.", e);
            return false;
        }
    }

    /**
     * LuaJ OneArgFunction implementation to allow this class to be called directly from Lua scripts.
     * It extracts the payload data from the Lua environment and passes it to the main injectPayload method.
     *
     * @param arg The Lua argument, expected to be a string or userdata containing the payload bytes.
     * @return LuaValue.TRUE on success, LuaValue.FALSE on failure.
     */
    @Override
    public LuaValue call(LuaValue arg) {
        try {
            byte[] payload = null;
            if (arg.isstring()) {
                payload = arg.tojstring().getBytes("UTF-8");
            } else if (arg.isuserdata(byte[].class)) {
                payload = (byte[]) arg.touserdata(byte[].class);
            } else {
                Status.println("PayloadBridge Lua ERROR: Argument must be a string or byte array.");
                return LuaValue.FALSE;
            }

            boolean success = injectPayload(payload);
            return success ? LuaValue.TRUE : LuaValue.FALSE;

        } catch (Exception e) {
            Status.printStackTrace("PayloadBridge: Error in Lua call.", e);
            return LuaValue.FALSE;
        }
    }
}
