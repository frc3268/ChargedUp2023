package frc.robot.subsystems

import edu.wpi.first.apriltag.AprilTagFieldLayout
import edu.wpi.first.math.geometry.Pose2d
import edu.wpi.first.math.geometry.Transform3d
import edu.wpi.first.math.util.Units
import edu.wpi.first.wpilibj.DriverStation
import edu.wpi.first.wpilibj.Filesystem
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab
import edu.wpi.first.wpilibj2.command.SubsystemBase
import frc.robot.Constants
import java.io.IOException
import org.photonvision.EstimatedRobotPose
import org.photonvision.PhotonCamera
import org.photonvision.PhotonPoseEstimator
import org.photonvision.PhotonPoseEstimator.PoseStrategy
import org.photonvision.targeting.PhotonPipelineResult

class CameraSubsystem : SubsystemBase() {
    val cam: PhotonCamera = PhotonCamera("CCP BALOON CAMERA")
    var frame: PhotonPipelineResult = PhotonPipelineResult()
    // star out on reflective tape pipeline
    var aprilOn: Boolean = false
    val visiontab: ShuffleboardTab = Shuffleboard.getTab("Vision")
    var poseEstimator: PhotonPoseEstimator? = null

    init {
        try {
            poseEstimator =
                    PhotonPoseEstimator(
                            AprilTagFieldLayout(
                                    Filesystem.getDeployDirectory().toString() +
                                            "2023-chargedup.json"
                            ),
                            PoseStrategy.MULTI_TAG_PNP,
                            cam,
                            Transform3d()
                    )
        } catch (e: IOException) {
            DriverStation.reportError("AprilTag: Failed to Load", e.getStackTrace())
            // !add some way to lock down apriltage features after this
        }
    }

    override fun periodic() {
        // This method will be called once per scheduler run
        frame = cam.getLatestResult()
        visiontab.add(
                "Distance to best target",
                movementToTarget(Constants.setHeights.poleTapeLow).distance
        )
        visiontab.add("Pipeline", (if (aprilOn) ("AprilTag") else ("Reflective Tape")))
    }

    fun takePicture(proscessed: Boolean) {
        (if (proscessed) (cam.takeOutputSnapshot()) else (cam.takeInputSnapshot()))
    }

    fun movementToTarget(targetHeight: Double): Constants.movementTarget {
        if (!frame.hasTargets())
                (return Constants.movementTarget(
                        Constants.errorCodes.targetsNotFoundError,
                        Constants.errorCodes.targetsNotFoundError
                ))
        val pitch: Double = Units.degreesToRadians(frame.getBestTarget().getPitch())
        val dist: Double = targetHeight - Constants.setHeights.camera / Math.tan(pitch)
        return Constants.movementTarget(dist, frame.getBestTarget().getYaw())
    }

    fun getEstimatedPose(prevPose: Pose2d): EstimatedRobotPose? {
        poseEstimator ?: return null
        cam.pipelineIndex = 1
        aprilOn = true
        poseEstimator?.setReferencePose(prevPose)
        return poseEstimator?.update()?.orElse(null)
    }

    fun resetPose(pose:Pose2d) {
        poseEstimator ?: return
        poseEstimator?.setReferencePose(pose)
    }

    override fun simulationPeriodic() {
        // This method will be called once per scheduler run during simulation
    }
}
