// package frc.robot.Commands;

// import java.util.ArrayList;

// import frc.robot.Robot;
// import frc.robot.RobotUtils;
// import edu.wpi.first.wpilibj.command.Command;
// import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

// public class Elevator extends Command {
// 	long startTime = 0;
// 	double desiredHeight = 0;
// 	double distance = 0;
// 	long startTimeNoMovement =0;
// 	Integer prevEncoderValue = null;


// 	public  Elevator(double wantedHeight) {
// 		requires(Robot.elevator);
// 		distance = wantedHeight;
// 	}
	
// 	@Override
// 	protected void initialize() { //853333 -> 55in: 15515.145enc/in
// 		Robot.elevator.setPosition();
// 		startTime = System.currentTimeMillis();
// 		desiredHeight = RobotUtils.getEncPositionFromINElevator(distance);
// 		Robot.elevator.movePosition(desiredHeight);
	
// 	}

// 	@Override
// 	protected void execute() {
// 		SmartDashboard.putNumber("height position", Robot.elevator.getEncoder());
// 	}
// 	@Override
// 	protected boolean isFinished() {

// 		if (Robot.elevator.fwdTopHatClosed() 
// 			|| (Math.abs(Robot.elevator.getEncoder()) <= (desiredHeight + 50)
// 			    && Math.abs(Robot.elevator.getEncoder()) >= (desiredHeight - 50))
// 			|| hasNotMoved()) {
// 			Robot.myLogger.log("Elevator","Done",Robot.elevator.getEncoder());
// 			return true;
// 		}
// 		else {
// 			return false;
// 		}
// 	}
	
// 	protected boolean hasNotMoved() {
// 		if (prevEncoderValue == null) {
// 			prevEncoderValue = Robot.elevator.getEncoder();
// 		}
// 		else {
// 			if (prevEncoderValue == Robot.elevator.getEncoder()){
// 				if (startTimeNoMovement == 0) {
// 					startTimeNoMovement = System.currentTimeMillis();
// 					Robot.myLogger.log("Elevator", "HasNotMoved - startTime no move 0", startTimeNoMovement);
// 				} else {
// 					long timeSinceLastMove = (System.currentTimeMillis()-startTimeNoMovement);
// 					if (timeSinceLastMove > 500) {
// 						return true;
// 					}
// 				}
// 			}else {
// 				startTimeNoMovement = 0;
// 				prevEncoderValue = Robot.elevator.getEncoder();
// 			}
// 		}
// 		return false;
// 	}
// }

