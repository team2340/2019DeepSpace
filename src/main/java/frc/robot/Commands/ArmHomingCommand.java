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
		Robot.arm.armMovement(false);
	}

	@Override
	protected boolean isFinished() {
		if (Robot.arm.limitSwitchForArmOpen.read() == true)
		{
			Robot.arm.armsStop();
			Robot.arm.encoderOffsetOfArm = Robot.arm.encoderArm.getPosition();
			System.out.println("Arm offset: " + Robot.arm.encoderOffsetOfArm);
			return true;
		}
		return false;
	}
}
