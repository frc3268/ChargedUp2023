// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.math.controller.ArmFeedforward;
import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.trajectory.TrapezoidProfile;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.motorcontrol.PWMSparkMax;
import edu.wpi.first.wpilibj2.command.ProfiledPIDSubsystem;

public class PIDMotorTemplate extends ProfiledPIDSubsystem {
  /** Creates a new PIDMotorTemplate. */

  private final PWMSparkMax m_motor = new PWMSparkMax(0);
  private final Encoder m_encoder = new Encoder(0, 0);
  private final ArmFeedforward m_feedforward =

      new ArmFeedforward(

          0, 0,

          0, 0);
  public PIDMotorTemplate() {
    super(
        // The ProfiledPIDController used by the subsystem
        new ProfiledPIDController(
            0,
            0,
            0,
            // The motion profile constraints
            new TrapezoidProfile.Constraints(0, 0)));
    //encoder distance per pulse
    m_encoder.setDistancePerPulse(0);
    //offset in radians
    setGoal(0);
  }

  @Override
  public void useOutput(double output, TrapezoidProfile.State setpoint) {
    // Calculate the feedforward from the sepoint
    double feedforward = m_feedforward.calculate(setpoint.position, setpoint.velocity);
    // Add the feedforward to the PID output to get the motor output
    m_motor.setVoltage(output + feedforward);
  }

  @Override
  public double getMeasurement() {
    // Return the process variable measurement here
      return m_encoder.getDistance(); // + offset
  }
}
