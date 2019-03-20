// package frc.robot.Commands;
// import frc.robot.Robot;
// import frc.robot.RobotUtils;

// import com.ctre.phoenix.motorcontrol.ControlMode;

// import edu.wpi.first.wpilibj.command.Command;
// import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

// public class AutoDriveForward extends Command {
// 	long startTime = 0;
// 	double desiredSpot = 0;
// 	double distance = 0;

// 	public AutoDriveForward(double howFar) {
// 		requires(Robot.drive);
// 		distance = howFar;
// 	}

// 	@Override
// 	protected void initialize() {
// 		Robot.oi.gyro.reset();
// 		Robot.drive.setForPosition();
// 		startTime = System.currentTimeMillis();
// //		double /*go = RobotUtils.distanceMinusRobot*/(distance);
// 		desiredSpot = RobotUtils.getEncPositionFromIN(distance);
// //		Robot.drive.move(desiredSpot);
// 		Robot.oi.left.set(ControlMode.Position, desiredSpot);
// 		Robot.oi.right.set(ControlMode.Position, -desiredSpot);
// 	}

// 	@Override
// 	protected void execute() {
// 		int leftPos = Math.abs(Robot.oi.left.getSelectedSensorPosition(0));
// 		int rightPos = Math.abs(Robot.oi.right.getSelectedSensorPosition(0));
// 		//		int leftErr = Math.abs(Robot.oi.left.getClosedLoopError(0));
// 		//		int rightErr = Math.abs(Robot.oi.right.getClosedLoopError(0));

// 		double range = 0;
// 		SmartDashboard.putNumber("Current angle: ", Robot.oi.gyro.getAngle());
// 		SmartDashboard.putNumber("left position", Robot.oi.left.getSelectedSensorPosition(0));
// 		SmartDashboard.putNumber("right position ",Robot.oi.right.getSelectedSensorPosition(0));

		
// 		//DO THAT HERE@!
// 		if(leftPos <= desiredSpot+range && leftPos >= desiredSpot-range){
// 			System.out.println ("LeftSide done");
// 		}
// 		if (rightPos <= desiredSpot+range && rightPos >= desiredSpot-range){
// 			System.out.println ("Rightside done");
		
// 		}
		
		
		
// //		if (Robot.oi.gyro.getAngle()>1){
// ////			System.out.println("Rotate to the left!!!!!!!!! gyro: " + Robot.oi.gyro.getAngle());
// //			double correction = Robot.drive.positionP-(Robot.drive.positionP * (.5 * (Math.abs(Robot.oi.gyro.getAngle())/ 3.0) ) );
// //			if (correction < 0 ) {
// //				correction = 0;
// //			}
// //			Robot.myLogger.log("Correct", "left", correction);
// //			Robot.oi.left.config_kP(0,correction,0);
// //			Robot.oi.left.set(ControlMode.Position, desiredSpot);
// //			
// //			Robot.oi.right.config_kP(0,Robot.drive.positionP,0);
// //			Robot.oi.right.set(ControlMode.Position, -desiredSpot);
// //		}
// //		else if (Robot.oi.gyro.getAngle()<-1) {
// //			
// //			double correction =Robot.drive.positionP-(Robot.drive.positionP * (.5 * (Math.abs(Robot.oi.gyro.getAngle())/ 3.0) ) );
// ////			System.out.println("Rotate to the right!!!!!!!!!!!!! gyro: " + Robot.oi.gyro.getAngle() );
// //			if (correction < 0 ) {
// //				correction = 0;
// //			}
// //			Robot.myLogger.log("Correct", "right", correction);
// //			Robot.oi.right.config_kP(0, correction , 0);
// //			
// //			Robot.oi.right.set(ControlMode.Position, -desiredSpot);
// //			Robot.oi.left.config_kP(0,Robot.drive.positionP,0);
// //			Robot.oi.left.set(ControlMode.Position, desiredSpot);
// //			
// //		}
// //		else {
// //			System.out.println("Don't rotate!!!!!");
// //			Robot.oi.right.config_kP(0,Robot.drive.positionP,0);
// //			Robot.oi.left.config_kP(0,Robot.drive.positionP,0);
// //			Robot.oi.left.set(ControlMode.Position, desiredSpot);
// //			Robot.oi.right.set(ControlMode.Position, -desiredSpot);
// //		}
// 	}
// 	//TODO: Check if current spike or encoder stopped 
// 	//		System.out.println("PID ERROR right " + Robot.oi.right.getClosedLoopError(0));
// 	//		System.out.println("PID ERROR left " + Robot.oi.left.getClosedLoopError(0));


// 	protected boolean done() {
// 		int leftPos = Math.abs(Robot.oi.left.getSelectedSensorPosition(0));
// 		int rightPos = Math.abs(Robot.oi.right.getSelectedSensorPosition(0));
// 		//		int leftErr = Math.abs(Robot.oi.left.getClosedLoopError(0));
// 		//		int rightErr = Math.abs(Robot.oi.right.getClosedLoopError(0));

// 		double range = 0;
// 		if((leftPos <= desiredSpot+range && leftPos >= desiredSpot-range)
// 				&& (rightPos <= desiredSpot+range && rightPos >= desiredSpot-range))
// 		{
// 			return true;
// 		}
// 		return false;
// 	}

// 	@Override
// 	protected boolean isFinished() {
// 		return done();
// 	}
// }
