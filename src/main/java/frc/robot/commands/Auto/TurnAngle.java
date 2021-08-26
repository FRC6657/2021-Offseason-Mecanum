// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Auto;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drivetrain;

public class TurnAngle extends CommandBase {
  
  private final Drivetrain m_drivetrain;
  private final double angle;

  public TurnAngle(Drivetrain m_drivetrain, double angle) {
    
    this.m_drivetrain = m_drivetrain;
    this.angle = angle;

    addRequirements(m_drivetrain);
  }

  @Override
  public void initialize() {
    System.out.println("Polar Drive Initialized");
  }

  @Override
  public void execute() {
    m_drivetrain.turnAngle(angle);
  }

  @Override
  public void end(boolean interrupted) {

    m_drivetrain.drive(0,0,0,true);

  }

  @Override
  public boolean isFinished() {
    return (Math.abs(m_drivetrain.getAngle() - angle) < 1);
  }
}
