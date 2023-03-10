package frc.robot.commands.commandgroups

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup
import frc.robot.commands.OpenGripperCommand
import frc.robot.commands.FloorArmCommand
import frc.robot.commands.CloseGripperCommand
import frc.robot.commands.RetractArmCommand
import frc.robot.commands.PickupArmCommand
import frc.robot.subsystems.GripperSubsystem
import frc.robot.subsystems.ControlledArmSubsystem
import frc.robot.Constants

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
class PickUpCargoCommand(gripper: GripperSubsystem, arm: ControlledArmSubsystem) : SequentialCommandGroup() {
    /**
     * Creates a new PickUpCargoCommand.
     */
    init {
        // Add your commands in the addCommands() call, e.g.
        // addCommands(FooCommand(), BarCommand())
        addCommands(       
            OpenGripperCommand(gripper),
            PickupArmCommand(arm),
            CloseGripperCommand(gripper),
            RetractArmCommand(arm)
        )
    }
}
