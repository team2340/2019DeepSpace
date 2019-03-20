package frc.robot.Commands;

import frc.robot.Robot;
import frc.robot.RobotMap;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class LiftAndRampCommand extends Command {
	private Joystick controller;

	public LiftAndRampCommand() {
		requires(Robot.liftandramp);
	}

	@Override
	protected void initialize() {
		controller = Robot.oi.driveController;
	}

	@Override
	protected void execute() {
		SmartDashboard.putBoolean("Lift Limit Switch", Robot.oi.lift.getSensorCollection().isFwdLimitSwitchClosed());

        if(controller.getRawButton(RobotMap.BUTTON_6) && ! controller.getRawButton(RobotMap.BUTTON_1))
        {
            Robot.liftandramp.liftup();
		}
		else if (controller.getRawButton(RobotMap.BUTTON_6) && controller.getRawButton(RobotMap.BUTTON_1)){
            Robot.liftandramp.liftdown();
		}
		else{
			Robot.liftandramp.liftstop();
		}
		
        // if(controller.getRawButton(RobotMap.BUTTON_2) && ! controller.getRawButton(RobotMap.BUTTON_1)){
        //     Robot.liftandramp.rampdown();
        // }
        //  if(controller.getRawButton(RobotMap.BUTTON_2) && controller.getRawButton(RobotMap.BUTTON_1)){
        //     Robot.liftandramp.rampDown();
		// }
		// else{
		// 	Robot.liftandramp.rampstop();
		// }
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
