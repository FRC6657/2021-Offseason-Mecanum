// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Auto;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drivetrain;

public class DrivePolar extends CommandBase {

  private final Drivetrain m_drivetrain;
  private final double magnitude;
  private final double angle;

  public DrivePolar(Drivetrain m_drivetrain, double magnitude, double angle) {
    
    this.m_drivetrain = m_drivetrain;
    this.magnitude = magnitude;
    this.angle = angle;

    addRequirements(m_drivetrain);
  }

  @Override
  public void initialize() {
    System.out.println("Polar Drive Initialized");
  }

  @Override
  public void execute() {
    m_drivetrain.drive(
      magnitude * Math.sin(angle * (Math.PI/180)),
      magnitude * Math.cos(angle * (Math.PI/180)),
      0,
      false);
  }

  @Override
  public void end(boolean interrupted) {

    m_drivetrain.drive(0,0,0,true);

  }

  @Override
  public boolean isFinished() {
    return false;
  }
}
