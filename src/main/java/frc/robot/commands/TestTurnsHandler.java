/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;

/**
 * An example command that uses an example subsystem.
 */
public class TestTurnsHandler extends CommandBase {
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})

  private double minLeft;
  private double minRight;
  private double maxLeft;
  private double maxRight;
  private double skips;
  private double currentLeft;
  private double currentRight;
  private boolean isReversed = false;

  private final TestTurns command;

  /**
   * Creates a new ExampleCommand.
   *
   * @param subsystem The subsystem used by this command.
   */
  public TestTurnsHandler(TestTurns command) {
    // Use addRequirements() here to declare subsystem dependencies.
    this.command = command;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    currentLeft = 0;
    currentRight = 0;
    minLeft = SmartDashboard.getNumber("Left Min Range", 0);
    minRight = SmartDashboard.getNumber("Right Min Range", 0);
    maxLeft = SmartDashboard.getNumber("Left Max Range", 1);
    maxRight = SmartDashboard.getNumber("Right Max Range", 1);
    skips = SmartDashboard.getNumber("Skips", 0.01);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if(!command.isScheduled()){
      if(currentLeft + skips > maxLeft)
      isReversed = !isReversed;
      SmartDashboard.putNumber("Left Power", currentLeft * (isReversed ? -1 : 1));
      SmartDashboard.putNumber("Right Power", currentRight * (isReversed ? -1 : 1));
      command.schedule();
      if(currentRight + skips > maxRight){
        currentRight = minRight;
        currentLeft += skips;
      }
      currentRight += skips;
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}