// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.PowerDistribution;
import edu.wpi.first.wpilibj.motorcontrol.Talon;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class MotorTemplate extends SubsystemBase {
  /** Creates a new MotorTemplate. */
  int channel;
  Talon motorTalon;
  PowerDistribution pdp;
  SmartDashboard dashboard;
  
  public MotorTemplate(int m_port, int m_channel, PowerDistribution m_pdp, SmartDashboard m_SmartDashboard) {
    motorTalon = new Talon(m_port);
    pdp = m_pdp;
    channel = m_channel;
    dashboard = m_SmartDashboard;
  }

  public void testset(double speed){
    motorTalon.set(speed);
    SmartDashboard.putNumber("Motor Current", pdp.getCurrent(channel));
  }

  public void stop(){
    motorTalon.stopMotor();
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
