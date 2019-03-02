package frc.robot.Commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class ArmHomingCommand extends Command {
	public ArmHomingCommand() {
		requires(Robot.arm);
	}
	@Override
	protected void initialize() {
	}

	@Override
	protected void execute() {
		Robot.arm.armMovement(true);
	}

	@Override
	protected boolean isFinished() {
		// if (Robot.arm.limitSwitchesForArms.read() ==true )
		{
            // Robot.myLogger.log("Arm","HOMED",Robot.arm.encoderArm.getPosition());
            Robot.arm.encoderOffsetOfArm = Robot.arm.encoderArm.getPosition();
			return true;
		}
		// return false;
	}
}
