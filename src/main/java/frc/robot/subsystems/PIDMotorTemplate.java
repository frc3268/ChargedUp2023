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

  private final PWMSparkMax m_motor;
  private final Encoder m_encoder;
  private final ArmFeedforward m_feedforward;
  private final int offset;

  public PIDMotorTemplate(int[] constants) {
    super(
        // The ProfiledPIDController used by the subsystem
        new ProfiledPIDController(
            constants[0],
            constants[1],
            constants[2],
            // The motion profile constraints
            new TrapezoidProfile.Constraints(constants[3], constants[4])));
    // encoder distance per pulse
    m_feedforward = new ArmFeedforward(

        constants[5], constants[6],

        constants[7], constants[8]);
    m_encoder = new Encoder(constants[9], constants[10]);
    m_motor = new PWMSparkMax(constants[11]);
    m_encoder.setDistancePerPulse(constants[12]);
    // offset in radians
    setGoal(constants[13]);
    offset = constants[13];
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
    return m_encoder.getDistance() + offset;
  }
}