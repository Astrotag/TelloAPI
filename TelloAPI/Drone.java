package TelloAPI;

public class Drone {
	
	UDPServer comms = new UDPServer();
	boolean commandMode = false;
	
	//?? What to do about the declaration of these enums
	public enum FlipDir{
		left,
		right,
		forwards, 
		backwards,  
		backLeft, 
		backRight,  
		frontLeft,  
		frontRight
	}
	
	final String[] flipDirs = {
			"l", "r", "f", "b", "bl", "rb", "fl", "fr"
	};
	
	public Drone() {
		
	}
	
	String SendQuery(String query) {
		comms.SendData(query);
		return comms.ReceiveData();
	}
	
	/*
	 * Use the command to enter command mode
	 */
	public void CommandMode() {
		if (SendQuery("command") == "ok") {
			System.out.println("Entering Command Mode");
			commandMode = true;
		}
		else
			System.out.println("Command Mode Error");
	}
	/*
	 * Use the land command to have the drone land
	 */
	public void Land() {
		if (SendQuery("land") == "ok" && !commandMode)
			System.out.println("Landing Successful");
		else
			System.out.println("Landing Error");
		}
	
	/*
	 * Activate the camera on the drone **WORK IN PROGRESS**
	 */
	public void StreamOn() {
		if (SendQuery("streamon") == "ok" && !commandMode) {
			System.out.println("Stream started");
			System.out.println();
		}
		else
			System.out.println("SteamOn error");
	
	}
	
	/*
	 * Deactivate the camera on the drone **WORK IN PROGRESS**
	 */
    public void StreamOff()
    {
        if (SendQuery("streamoff") == "ok" && !commandMode)
        {
            System.out.println("Stream ended");
        }
        else
        	System.out.println("StreamOff error");
    }
    
    /*
     * Emergency landing. Will cut the motors off to land immediately 
     */
    public void Emergency() {
        if (SendQuery("emergency") == "ok" && !commandMode)
            System.out.println("Emergency Shutdown Actioned");
        else
            System.out.println("Emergency error aka MISTAKES HAVE BEEN MADE!");
    }
    
	/*
	 * Increase the height of the drone
	 */
    public void Up(int height) {
    	if (height >= 20 && height <= 500) {
			if(SendQuery("up " + height) == "ok" && !commandMode)
				System.out.println("Raised "  + height);
			else
				System.out.println("Up command error");	
		}
    	else
    		System.out.println("Height needs to be between 20 and 500");
    }
    
    /*
     * Makes the drone fly left
     */
    public void Down(int height) {
    	if (height >= 20 && height <= 500) {
			if(SendQuery("down " + height) == "ok" && !commandMode)
				System.out.println("Lowered "  + height);
			else
				System.out.println("Down command error");	
		}
    	else
    		System.out.println("Height needs to be between 20 and 500");
    }
    
    /*
     * Makes the drone fly right
     */
    public void Right(int distance) {
    	if(distance >= 20 && distance <= 500) {
    		if(SendQuery("right " + distance) == "ok" && !commandMode)
    			System.out.println("Moved right " + distance + " cm");
    		else
    			System.out.println("Move right error");
    	}
    	else
    		System.out.println("Distance needs to be between 20 and 500");
    }
    

    /*
     *  Makes the drone fly forwards
     */
    public void Forwards(int distance)
    {
        if (distance >= 20 && distance <= 500)
        {
            if (SendQuery("forward " + distance) == "ok" && !commandMode)
                System.out.println("Moved forward " + distance + "cm");
            else
                System.out.println("Move forward error");
        }
        else
            System.out.println("Distance needs to be between 20 and 500");
    }
    
    /*
     * Makes the drone fly backwards
     */
    public void Backwards(int distance)
    {
        if (distance >= 20 && distance <= 500)
        {
            if (SendQuery("back " + distance) == "ok" && !commandMode)
                System.out.println("Moved back " + distance + "cm");
            else
                System.out.println("Move backwards error");
        }
        else
            System.out.println("Distance needs to be between 20 and 500");
    }

    /*
     * Makes the drone rotate clockwise
     */
    public void RotateClockwise(int degrees)
    {
        if (degrees >= 1 && degrees <= 3600)
        {
            if (SendQuery("cw " + degrees) == "ok" && !commandMode)
                System.out.println("Rotated Clockwise " + degrees + " degrees");
            else
                System.out.println("Rotate clockwise error");
        }
        else
            System.out.println("Degrees needs to be between 20 and 500");
        
    }
    
    /*
     * Makes the drone rotate counter clockwise
     */
    public void RotateCounterClockwise(int degrees)
    {
        if (degrees >= 1 && degrees <= 3600)
        {
            if (SendQuery("ccw " + degrees) == "ok" && !commandMode)
                System.out.println("Rotated Counter Clockwise " + degrees + " degrees");
            else
                System.out.println("Rotate counter clockwise error");
        }
        else
            System.out.println("Degrees needs to be between 20 and 500");
    }
    
    /*
     * Makes the drone do a flip
     */
    public void Flip(FlipDir flipDir) {
    	if(SendQuery("flip " + flipDirs[(flipDir.ordinal())]) == "ok" && !commandMode)
    		System.out.println("Flipped " + flipDir.toString());
    	else
    		System.out.println("Flip error");
    }
    
    /*
     * Makes the drone fly to a specific coordinates at a specified speed
     */
    public void GoTo(int x, int y, int z, int speed)
    {
        if (x >= 20 && x <= 500 && y >= 20 && y <= 500  && z >= 20 && z <= 500 && speed >= 10 && speed <= 100)
        {
            if (SendQuery("go " + x + " " + y +  " " + z + " " + speed) == "ok" && !commandMode)
                System.out.println("Moved to location" + "X:" + x + " Y:" +  y + " Z:" + z + " Speed:" + speed);
            else
                System.out.println("Goto error");
        }
        else
            System.out.println("Error XYZ need to be between 20-500 Speed between 10-100");
    }
    
    /*
     * Tello fly a curve defined by the
     * current and two given coordinates
     *with speed(cm/s)
     *If the arc radius is not within
     *the range of 0.5-10 meters, it
     *responses false
     *x/y/z can’t be between -20 – 20 at
     * the same time.
     */
     public void Curve(int x1, int y1, int z1, int x2, int y2, int z2, int speed)
     {
         if (x1 >= 20 && x1 <= 500 && y1 >= 20 && y1 <= 500 && z1 >= 20 && z1 <= 500 && speed >= 10 && speed <= 60)
         {

             if (x2 >= 20 && x2 <= 500 && y2 >= 20 && y2 <= 500 && z2 >= 20 && z2 <= 500)
             {
                 if (SendQuery("curve "+ x1+ " "+ y1+ " "+ z1+ " "+ x2+ " "+ y2+ " "+ z2+ " "+ speed) == "ok" && !commandMode)
                     System.out.println("Curved to location" + "X1:"+ x1+ " Y1:"+ y1+ " Z1:"+ z1+ " X2:"+ x2+ " Y2"+ y2+ " Z2"+ z2+ " Speed:"+ speed);
                 else
                     System.out.println("Curve error");
             }
             else
                 System.out.println("Error XYZ2 need to be between 20-500");
         }
         else
             System.out.println("Error XYZ1 need to be between 20-500 Speed between 10-60");
     }

     /*
     * Sets the drone speed
     */
     public void SetSpeed(int speed)
     {
         if (speed >= 10 && speed <= 100)
         {
             if (SendQuery("speed " + speed) == "ok" && !commandMode)
                 System.out.println("Set Speed To " + speed);
             else
                 System.out.println("Set speed error");
         }
         else
             System.out.println("Speed needs to be between 10 and 100");
     }

     /*
      * Send RC control via four channels.
      *lr: left/right(-100~100)
      *fb: forward/backward(-100~100)
      *ud: up/down(-100~100)
      *yaw(-100~100)
      */
     public void RCCommand(int lr, int fb, int ud, int yaw)
     {
         if (lr >= -100 && lr <= 100 && fb >= -100 && fb <= 100 && ud >= -100 && ud <= 100 && yaw >= -100 && yaw <= 100)
         {
             if (SendQuery("rc"+" "+lr+" "+fb+" "+ud+" "+yaw) == "ok" && !commandMode)
                 System.out.println("RC Command to " + "Left/Right:"+ lr+ " Forwards/Backwards:"+ fb+ " Up/Down:"+ ud+ " Yaw:"+ yaw);
             else
                 System.out.println("RC command error");
         }
         else
             System.out.println("all inputs are betwwen -100 to 100");
     }

     /*
     * Secures Drones wifi
     */
     public void SecureDrone(String ssid, String pass)
     {
         Land();
         if (SendQuery("wifi "+ssid+" "+pass) == "ok" && !commandMode)
             System.out.println("Secured Drone with SSID: " + ssid +  "Password: " + pass + "Please Reconnect");
         else
             System.out.println("Secure drone error");
     }

     /*
     * get the speed parameter of the drone
     */
     public int GetSpeed()
     {           
         return Integer.parseInt(SendQuery("speed?"));
     }

     /*
     * get the percentage of the battery
     */
     public int GetBattery()
     {            
         return Integer.parseInt(SendQuery("battery?"));
     }

     /*
     * gets flight time
     */
     public String GetTime()
     {
         return SendQuery("time?");
     }

     /*
     * get height from starting point
     */
     public int GetHeight()
     {
         return Integer.parseInt(SendQuery("height?"));
     }

     /*
     * get temperature
     */
     public int GetTemp()
     {
         return Integer.parseInt(SendQuery("temp?"));
     }

     /*
     * Reports the pitch roll and yaw of the drone
     */
     public void GetAttitude()
     {
         System.out.println(SendQuery("attitude?"));
     }

     /*
     * get the barometer reading from the drone
     */        
     public int GetBaro()
     {
         return Integer.parseInt(SendQuery("baro?"));
     }

     /*
     * reports the acceleration of the drone
     */
     public void GetAcceleration()
     {
         System.out.println(SendQuery("acceleration?"));
     }

     /*
     * gets the distance to the floor
     */        
     public int GetTof()
     {
         return Integer.parseInt(SendQuery("tof?"));
     }

     /*
     * reports Wifi info
     */
     public void GetWifi()
     {
         System.out.println(SendQuery("wifi?"));
     }
	
}
