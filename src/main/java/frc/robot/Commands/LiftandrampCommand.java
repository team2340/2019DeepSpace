package frc.robot.Commands;

import frc.robot.Robot;
import frc.robot.RobotMap;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Command;

public class LiftandrampCommand extends Command {
	private Joystick controller;

	public LiftandrampCommand() {
		requires(Robot.liftandramp);
	}

	@Override
	protected void initialize() {
		controller = Robot.oi.driveController;
	}

	@Override
	protected void execute() {
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
		
        if(controller.getRawButton(RobotMap.BUTTON_2) && ! controller.getRawButton(RobotMap.BUTTON_1)){
            Robot.liftandramp.rampdown();
        }
        else if(controller.getRawButton(RobotMap.BUTTON_2) && controller.getRawButton(RobotMap.BUTTON_1)){
            Robot.liftandramp.rampup();
		}
		else{
			Robot.liftandramp.rampstop();
		}

		if(controller.getRawButton(RobotMap.BUTTON_4)){
			if(Robot.liftandramp.getRampAngle()> 90){
				Robot.liftandramp.rampup();
			}
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
