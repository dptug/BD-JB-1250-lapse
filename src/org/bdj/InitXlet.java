package org.bdj;

import javax.tv.xlet.Xlet;
import javax.tv.xlet.XletContext;

import org.bdj.Status;
import org.bdj.api.NativeInvoke;

import org.bdj.sandbox.XletManagerExploit;

public class InitXlet implements Xlet {
	
    public void initXlet(XletContext context) {
		Status.println("BD-J init");
		Status.println("PS4 12.00 BD-JB Exploit Starting...");
		
		try {
            Status.println("Triggering sandbox escape exploit...");
            
            // Attempt the sandbox escape exploit
            boolean exploitSuccess = XletManagerExploit.trigger();
            
            if (exploitSuccess) {
                Status.println("Exploit success - sandbox escape achieved");
                Status.println("Security Manager disabled, proceeding with native operations");
				
				// Test native functionality
				try {
					NativeInvoke.sendNotificationRequest("BD-JB PS4 12.00 - Exploit Successful!");
					Status.println("Native function call successful");
				} catch (Exception e) {
					Status.printStackTrace("Warning: Native function test failed: ", e);
				}
				
                // Run USB-based Lua script for payload injection after successful exploit
                Status.println("Proceeding to payload injection phase...");
                try {
                    LuaRunner.runScriptFile("/mnt/usb0/inject_hen.lua");
                    Status.println("Payload injection script completed");
                } catch (Exception e) {
                    Status.printStackTrace("Error running payload injection script: ", e);
                    // Fallback: try to run kernel exploit directly
                    Status.println("Attempting fallback kernel exploit execution...");
                    try {
                        LuaRunner.runScriptFile("/av_contents/content_tmp/savedata/main.lua");
                        Status.println("Fallback kernel exploit completed");
                    } catch (Exception fallbackError) {
                        Status.printStackTrace("Fallback also failed: ", fallbackError);
                    }
                }
            } else {
                Status.println("Exploit failed - sandbox still active");
                Status.println("BD-JB exploit unsuccessful, cannot proceed");
            }
            
        } catch (Exception e) {
            Status.printStackTrace("Critical error in initXlet: ", e);
            Status.println("BD-JB exploit chain failed");
        }
		
		Status.println("BD-J initialization completed");
    }
    
	public void startXlet() {
		// Xlet lifecycle method - intentionally empty
	}

	public void pauseXlet() {
		// Xlet lifecycle method - intentionally empty
	}

	public void destroyXlet(boolean unconditional) {
		// Cleanup resources if needed
		Status.close();
	}
}



