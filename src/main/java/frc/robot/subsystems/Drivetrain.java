// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.sensors.PigeonIMU;

import edu.wpi.first.wpilibj.drive.Vector2d;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpiutil.math.MathUtil;
import frc.robot.Constants;

public class Drivetrain extends SubsystemBase {

  private static WPI_TalonSRX m_frontLeft;
  private static WPI_TalonSRX m_frontRight;
  private static WPI_TalonSRX m_backLeft;
  private static WPI_TalonSRX m_backRight; 

  private static PigeonIMU m_gyro;

  public Drivetrain() {

    m_frontLeft = new WPI_TalonSRX(1);
    m_frontRight = new WPI_TalonSRX(2);
    m_backLeft = new WPI_TalonSRX(3);
    m_backRight = new WPI_TalonSRX(4);

    m_gyro = new PigeonIMU(5);

  }

  public void drive(double xSpeed, double ySpeed, double zRotation, boolean fieldRelative){

    xSpeed = MathUtil.clamp(xSpeed, -1, 1);
    ySpeed = MathUtil.clamp(xSpeed, -1, 1);
    zRotation = MathUtil.clamp(zRotation, -1, 1);

    xSpeed = applyDeadband(xSpeed, 0.05);
    ySpeed = applyDeadband(ySpeed, 0.05);
    zRotation = applyDeadband(zRotation, 0.05);

    Vector2d input = new Vector2d(ySpeed,xSpeed);

    if(fieldRelative){

      input.rotate(-m_gyro.getFusedHeading());

    }

    double[] wheelSpeeds = new double[4];

    wheelSpeeds[0] = input.x + input.y + zRotation;
    wheelSpeeds[1] = -input.x + input.y - zRotation;
    wheelSpeeds[2] = -input.x + input.y + zRotation;
    wheelSpeeds[3] = input.x + input.y - zRotation;

    normalize(wheelSpeeds);

    m_frontLeft.set(wheelSpeeds[0] * Constants.MAX_DRIVE_SPEED);
    m_frontRight.set((wheelSpeeds[1] * Constants.MAX_DRIVE_SPEED) * -1);
    m_backLeft.set(wheelSpeeds[2] * Constants.MAX_DRIVE_SPEED);
    m_backRight.set((wheelSpeeds[3] * Constants.MAX_DRIVE_SPEED) * -1);

  }

  private double applyDeadband(double input, double deadband) {
    if (Math.abs(input) > deadband) {
      if (input > 0.0) {
        return (input - deadband) / (1.0 - deadband);
      } else {
        return (input + deadband) / (1.0 - deadband);
      }
    } else {
      return 0.0;
    }
  }

  private void normalize(double[] wheelSpeeds) {
    double maxMagnitude = Math.abs(wheelSpeeds[0]);
    for (int i = 1; i < wheelSpeeds.length; i++) {
      double temp = Math.abs(wheelSpeeds[i]);
      if (maxMagnitude < temp) {
        maxMagnitude = temp;
      }
    }
    if (maxMagnitude > 1.0) {
      for (int i = 0; i < wheelSpeeds.length; i++) {
        wheelSpeeds[i] = wheelSpeeds[i] / maxMagnitude;
      }
    }
  }
  

  @Override
  public void periodic() {}

  @Override
  public void simulationPeriodic() {}
}
