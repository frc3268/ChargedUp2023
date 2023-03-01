# ChargedUp2023
Math IA Idea generator

*A note from Weiju, one of the programmers*
In our code, we're dealing with measures of various units, and we need to convert between them a lot. In order to avoid confusion, we're using a naming system in which we use suffixes in variable names to indicate units. They are as follows:
- R: Radians
- D: Degrees
- M: Meters
- I: Inches

Command groups (all begin retracted):
- Pickup:             OpenGripperCommand, PickupArmCommand, CloseGripperCommand, RetractArmCommand
- Dropoff @ Extended: ExtendArmCommand, OpenGripperCommand, RetractArmCommand
- Dropoff @ Lower:    LowerArmCommand, OpenGripperCommand, RetractArmCommand
- Dropoff @ Higher:   HigherArmCommand, OpenGripperCommand, RetractArmCommand