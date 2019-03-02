package frc.robot.Commands;

import frc.robot.Robot;
import frc.robot.RobotMap;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;

//import org.usfirst.frc.team2340.robot.RobotMap;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class ArcadeDriveCommand extends Command {
	double x1=0;
    double x2=0;
    private NetworkTable table;
	boolean buttonPressed = false;
	boolean buttonMode = false;


	private Joystick controller;

	public ArcadeDriveCommand() {
		requires(Robot.drive);
		controller = Robot.oi.driveController;
	}

	@Override
	protected void initialize() {
		table = NetworkTableInstance.getDefault().getTable("datatable");
	}

	@Override
	protected void execute() {
		double angle = 0;//Robot.oi.gyro.getAngle();
		SmartDashboard.putNumber("Gyro angle", angle);
		SmartDashboard.putNumber("left position", Robot.oi.left.getSensorCollection().getQuadraturePosition());
		SmartDashboard.putNumber("right position ", Robot.oi.right.getSensorCollection().getQuadraturePosition());

		double x, y, z;

		z = (3 - controller.getZ()) / 2;
		y = -controller.getY() / z;
		x = controller.getX() / z;

		// System.out.println("ArcadeSpeed x:"+x+" y:"+y); this was already comented out

		x1 = table.getEntry("X1").getDouble(x1);
		x2 = table.getEntry("X2").getDouble(x2);
		double outerRange = 3;
		double innerRange = 1;

		if(!((x1<=39+outerRange)&&(x1>=39-outerRange)&&(x2 >= 39+outerRange)&&(x2 <= 39-outerRange))){
            //it's not straight at center!
            if (x2<x1) {
                SmartDashboard.putString("How To Move", "Turn Left");
                //print out turn left
            }
            else if (x2>x1) {
                SmartDashboard.putString( "How To Move", "Turn Left");
                //print out turn right
            }
		}
   	 	else {	
			//it is at center
			SmartDashboard.putString("How To Move", "All Good Press 3");
			if (controller.getRawButton(RobotMap.BUTTON_3)){
				if(!buttonPressed)
				{
					buttonPressed = true;
					if(buttonMode==false){
						buttonMode = true;
					}
					else if (buttonMode ==true){
						buttonMode =false;
					}
				}
			}
			else{
				buttonPressed = false;
			}
			// add to smart dash baord
		}
		if(buttonMode == true){
			double betty = .3;//this means the amount that we slow down one of the robot`s moters so we turn, it was named by Grace who when asked what we shoud name this varible responed Betty
			if((x1<(x2+innerRange))||(x1>(x2-innerRange))){
				Robot.oi.right.set(y);
				Robot.oi.left.set(y);
			}
			else if (x2<x1){//goignto the right need to go left
				Robot.oi.right.set(y);
				if(y<0){
					Robot.oi.left.set(y);
				}
				else if((y-betty)>0){
				Robot.oi.left.set(y-betty);
				}
				else{
					Robot.oi.left.set(0);
				}
			}
			else if(x1<x2){//going to the left need to go right
				if(y<0){
					Robot.oi.right.set(y);
				}
				else if ((y-betty)>0){
					Robot.oi.right.set(y-betty);
				}
				else{
					Robot.oi.right.set(0);
				}
				Robot.oi.left.set(y);
			}
		}
		else{
			Robot.drive.setArcadeSpeed(x, y);
		}
	}

	

	@Override
	protected boolean isFinished() {
		return false;
	}

	@Override
	protected void end() {
	}

	@Override
	protected void interrupted() {
	}
}
