package frc.robot

import edu.wpi.first.wpilibj2.command.Command
import edu.wpi.first.wpilibj2.command.button.CommandXboxController
import edu.wpi.first.wpilibj2.command.button.Trigger
import edu.wpi.first.wpilibj2.command.StartEndCommand
import edu.wpi.first.wpilibj2.command.Commands
import frc.robot.subsystems.CameraSubsystem
import frc.robot.subsystems.DriveSubsystem
import frc.robot.subsystems.ControlledArmSubsystem
import frc.robot.subsystems.GripperSubsystem
import frc.robot.commands.DriveToTargetCommand
import frc.robot.commands.ExtendArmCommand
import frc.robot.commands.LowerArmCommand
import frc.robot.commands.RetractArmCommand
import frc.robot.Constants

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the [Robot]
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and trigger mappings) should be declared here.
 */
class RobotContainer {
    // The robot's subsystems and commands are defined here...
    private val driveSubsystem = DriveSubsystem()
    public val cameraSubsystem = CameraSubsystem()
    private val armSubsystem = ControlledArmSubsystem(Constants.Arm(5, 0.3, 0.0, 0.0, 0.0, 0.0, 1.0, -1.0, 1.0))
    private val gripperSubsystem = GripperSubsystem()
    private val io = IO()

    /** The container for the robot. Contains subsystems, OI devices, and commands. */
    init {
        // Configure the trigger bindings
        configureBindings()
    }

    /**
     * Use this method to define your trigger->command mappings. Triggers can be created via the
     * [Trigger#Trigger(java.util.function.BooleanSupplier)] constructor with an arbitrary
     * predicate, or via the named factories in
     * [edu.wpi.first.wpilibj2.command.button.CommandGenericHID]'s subclasses for
     * [CommandXboxController]/[edu.wpi.first.wpilibj2.command.button.CommandPS4Controller]
     * controllers or [edu.wpi.first.wpilibj2.command.button.CommandJoystick].
     */
    private fun configureBindings() {
        // Schedule ExampleCommand when exampleCondition changes to true
        Trigger { io.firstButton.asBoolean }
            //.onTrue(triggerCommand)
    }

    /**
     * Use this to pass the autonomous command to the main [Robot] class.
     *
     * @return the command to run in autonomous
     */
    val autonomousCommand: Command = DriveToTargetCommand(cameraSubsystem, driveSubsystem, Constants.setHeights.poleTapeLowI, 1.0)

    /**
     * The pitch axis moves the robot forward and backward; the roll axis turns it left and right.
     * The throttle can be used to limit the speed of movement or rotation.
     * When the throttle is set to zero (fully down towards the minus sign printed on the joystick), all inputs to the joystick are zeroed and the robot will not move.
     * When the throttle is set to max (all the way up towards the plus sign printed on the joystick), no limits are placed on the magnitude of the input whatsoever.
     * @author Weiju Wang
     */
    val teleopCommand: Command =
        driveSubsystem.arcadeDriveCommand(
            { io.joystick.getY() * (-1 * io.joystick.getThrottle() + 1) / 2 },
            { io.joystick.getX() * (-1 * io.joystick.getThrottle() + 1) / 2 }
        )
}
