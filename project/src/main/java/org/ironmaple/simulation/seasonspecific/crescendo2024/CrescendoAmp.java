package org.ironmaple.simulation.seasonspecific.crescendo2024;

import static edu.wpi.first.units.Units.Centimeters;

import edu.wpi.first.math.geometry.*;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.networktables.StructPublisher;
import java.util.*;
import org.ironmaple.simulation.goal;

/**
 *
 *
 * <h2>Simulates the two <strong>REEF</strong>s on the field.</h2>
 *
 * <p>This class simulates the two <strong>REEF</strong>s on the field where <strong>CORAL</strong>s can be scored. It
 * includes all 12 {@link ReefscapeReefBranchesTower} instances on the field (both blue and red).
 */
public class CrescendoAmp extends goal {

    protected static final Translation3d blueAmpPose = new Translation3d(1.81, 8.3, 0.66);
    protected static final Translation3d redAmpPose = new Translation3d(14.7, 8.3, 0.66);

    protected final Arena2024Crescendo crescendoArena;

    StructPublisher<Pose3d> posePublisher;

    public CrescendoAmp(Arena2024Crescendo arena, boolean isBlue) {
        super(
                arena,
                Centimeters.of(61),
                Centimeters.of(30),
                Centimeters.of(46),
                "Note",
                isBlue ? blueAmpPose : redAmpPose,
                isBlue);

        crescendoArena = arena;

        setNeededAngle(new Rotation3d(0, Math.PI / 2, Math.PI / 2));

        StructPublisher<Pose3d> ampPublisher = NetworkTableInstance.getDefault()
                .getStructTopic(isBlue ? "BlueAmp" : "RedAmp", Pose3d.struct)
                .publish();
        ampPublisher.set(new Pose3d(position, this.peiceAngle));
    }

    @Override
    protected void addPoints() {

        crescendoArena.addAmpCharge(isBlue);
        arena.addToScore(isBlue, crescendoArena.isAmped(isBlue) ? 2 : 1);
    }

    @Override
    public void draw(List<Pose3d> drawList) {
        return;
    }
}
